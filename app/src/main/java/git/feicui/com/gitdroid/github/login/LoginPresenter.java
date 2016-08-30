package git.feicui.com.gitdroid.github.login;

import android.util.Log;


import git.feicui.com.gitdroid.github.login.model.AccessTokenResult;
import git.feicui.com.gitdroid.github.login.model.User;
import git.feicui.com.gitdroid.github.login.model.UserRepo;
import git.feicui.com.gitdroid.github.network.GithubApi;
import git.feicui.com.gitdroid.github.network.GithubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：yuanchao on 2016/7/29 0029 12:01
 * 邮箱：yuanchao@feicuiedu.com
 */
public class LoginPresenter {

    private Call<AccessTokenResult> tokenCall;
    private Call<User> userCall;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    // 登陆(先用code换token,再用token换用户信息)
    public void login(String code) {
        Log.i("gqq","--------------login方法");
        loginView.showProgress();
        if (tokenCall != null) tokenCall.cancel();
        // 获取token
        tokenCall = GithubClient.getInstance().getOAuthToken(
                GithubApi.CLIENT_ID,
                GithubApi.CLIENT_SECRET,
                code);
        tokenCall.enqueue(tokenCallback);
    }

    private Callback<AccessTokenResult> tokenCallback = new Callback<AccessTokenResult>() {
        // token接口响应
        @Override public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {

            // 响应体内数据(结果)
            AccessTokenResult result = response.body();
            String token = result.getAccessToken();
            // 缓存token
            UserRepo.setToken(token);
            // 再将用此token执行获取用户信息接口,拿到用户信息
            if (userCall != null) userCall.cancel();

            userCall = GithubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallback);
        }

        @Override public void onFailure(Call<AccessTokenResult> call, Throwable t) {

            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };

    private Callback<User> userCallback = new Callback<User>() {
        @Override public void onResponse(Call<User> call, Response<User> response) {
            User user = response.body();
            // 缓存user
            UserRepo.setUser(user);

            loginView.showMessage("登陆成功");
            loginView.gotoMain();
        }

        @Override public void onFailure(Call<User> call, Throwable t) {

            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };
}

package git.feicui.com.gitdroid.github.repoinfo;

import android.support.annotation.NonNull;
import android.util.Base64;



import java.io.IOException;

import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;
import git.feicui.com.gitdroid.github.network.GithubClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 业务（视图业务和逻辑业务）
 * <p/>
 * 仓库详情显示完成
 */
public class RepoInfoPresenter {
    // 主要完成视图
    public interface RepoInfoView {
        /**
         * 1.显示进度条
         * 2.隐藏进度条
         * 3.显示信息
         * 4.加载数据，显示数据
         */
        void showProgrss();

        void hideProgress();

        void showMessage(String msg);

        void setData(String htmlContent);
    }

    private RepoInfoView repoInfoView;
    private Call<RepoContentResult> repoContentCall;
    private Call<ResponseBody> mdhtmlCall;

    public RepoInfoPresenter(@NonNull RepoInfoView repoInfoView) {
        this.repoInfoView = repoInfoView;
    }

    public void getReadme(Repo repo) {
        //显示加载进度条
        repoInfoView.showProgrss();

        String login = repo.getOwner().getLogin();
        String name = repo.getName();
        if (repoContentCall != null) {
            repoContentCall.cancel();
        }
        repoContentCall = GithubClient.getInstance().getReadme(login, name);
        repoContentCall.enqueue(repoContentCallback);
    }

    private Callback<RepoContentResult> repoContentCallback = new Callback<RepoContentResult>() {
        @Override public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
            String content = response.body().getContent();
            // BASE64解码操作
            byte[] data = Base64.decode(content, Base64.DEFAULT);
            // 根据data获取到markdown（也就是readme文件）的HTML格式文件显示出来
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, data);
            if (mdhtmlCall != null) mdhtmlCall.cancel();
            mdhtmlCall = GithubClient.getInstance().markDown(body);
            mdhtmlCall.enqueue(mdhtmlCallback);
        }

        @Override public void onFailure(Call<RepoContentResult> call, Throwable t) {
            repoInfoView.hideProgress();
            //显示错误信息
            repoInfoView.showMessage(t.getMessage());
        }
    };

    private Callback<ResponseBody> mdhtmlCallback = new Callback<ResponseBody>() {
        @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            repoInfoView.hideProgress();
            //数据已经获取出来了
            try {
                String htmlContent = response.body().string();
                repoInfoView.setData(htmlContent);
            } catch (IOException e) {
                onFailure(call, e);
            }
        }

        @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };


    // 执行API
    // 得到响应结果
    // 数据交还给View去显示

    // 1. 先要去做Retrofit api 接口
    // 2. 完善一下
}

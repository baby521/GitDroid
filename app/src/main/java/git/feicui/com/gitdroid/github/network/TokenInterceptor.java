package git.feicui.com.gitdroid.github.network;


import java.io.IOException;

import git.feicui.com.gitdroid.github.login.model.UserRepo;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Token拦截器
 * <p/>
 *          修复登录加载
 */
public class TokenInterceptor implements Interceptor {

    @Override public Response intercept(Chain chain) throws IOException {
        // 每次请求，都将加一个token值
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        // 是否有token(注意空格)
        if (UserRepo.hasUserToken()) {
            builder.header("Authorization", "token " + UserRepo.getToken());
        }
        Response response = chain.proceed(builder.build());
        if (response.isSuccessful()) {
            return response;
        }
        if (response.code() == 401 || response.code() == 403) {
            throw new IOException("未经授权的！限制是每分钟10次！");
        } else {
            throw new IOException("响应码:" + response.code());
        }
    }
}

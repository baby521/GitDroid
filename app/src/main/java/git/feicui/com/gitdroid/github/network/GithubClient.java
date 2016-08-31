package git.feicui.com.gitdroid.github.network;


import git.feicui.com.gitdroid.commons.LoggingInterceptor;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.RepoResult;
import git.feicui.com.gitdroid.github.hotuser.HotUserResult;
import git.feicui.com.gitdroid.github.login.model.AccessTokenResult;
import git.feicui.com.gitdroid.github.login.model.User;
import git.feicui.com.gitdroid.github.repoinfo.RepoContentResult;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 开发者功能实现
 */
public class GithubClient implements GithubApi{

    private final GithubApi mGithubApi;
    private static GithubClient mGithubClient;

    public static GithubClient getInstance(){
        mGithubClient = new GithubClient();
        return mGithubClient;
    }

    private GithubClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mGithubApi = retrofit.create(GithubApi.class);

    }

    @Override
    public Call<AccessTokenResult> getOAuthToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return mGithubApi.getOAuthToken(clientId, clientSecret, code);
    }

    @Override
    public Call<User> getUserInfo() {
        return mGithubApi.getUserInfo();
    }

    @Override
    public Call<RepoResult> searchRepo(@Query("q") String q, @Query("page") int pageId) {
        return mGithubApi.searchRepo(q, pageId);
    }

    @Override
    public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
        return mGithubApi.getReadme(owner, repo);
    }

    @Override
    public Call<ResponseBody> markDown(@Body RequestBody body) {
        return mGithubApi.markDown(body);
    }

    @Override
    public Call<HotUserResult> searchUsers(@Query("q") String query, @Query("page") int pageId) {
        return mGithubApi.searchUsers(query, pageId);
    }
}

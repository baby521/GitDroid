package git.feicui.com.gitdroid.github.network;


import git.feicui.com.gitdroid.github.hotrepo.repolist.model.RepoResult;
import git.feicui.com.gitdroid.github.hotuser.HotUserResult;
import git.feicui.com.gitdroid.github.login.model.AccessTokenResult;
import git.feicui.com.gitdroid.github.login.model.User;
import git.feicui.com.gitdroid.github.repoinfo.RepoContentResult;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public interface GithubApi {

    String CALL_BACK = "feicui";

    // GitHub开发者，申请就行
    String CLIENT_ID = "628c20265d16c1f42e3a";
    String CLIENT_SECRET = "0fec69ff9a9b86c2430efeff32cffdc7dc041c48";

    // 授权时申请的可访问域
    String AUTH_SCOPE = "user,public_repo,repo";
    // 授权登陆页面(用WebView来加载)
    String AUTH_RUL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;
//    String AUTH_RUL = "https://github.com/login/oauth/authorize?client_id=aa7a3fb1b42f8c07a232&scope=user,public_repo,repo;

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(@Field("client_id")
    String clientId, @Field("client_secret")
    String clientSecret, @Field("code") String code);

    @GET("user")
    Call<User> getUserInfo();

    /**
     * 获取仓库列表的请求api
     * @param q   查询的参数--体现为语言
     * @param pageId   查询页数默认从 1 开始
     * @return
     */

    @GET("search/repositories")
    Call<RepoResult> searchRepo(@Query("q")
    String q, @Query("page") int pageId);

    /***
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return 仓库的readme页面内容,将是markdown格式且做了base64处理
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(
            @Path("owner") String owner,
            @Path("repo") String repo);

    /***
     * 获取一个markdonw内容对应的HTML页面
     *
     * @param body 请求体,内容来自getReadme后的RepoContentResult
     */
    @Headers({
            "Content-Type:text/plain"//纯文本的形式
    })
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);

    /**
     * 获取用户列表
     * @Param query 查询参数(followers:>1000)
     * @Param pageId 查询页数据(从1开始)
     */
    @GET("/search/users")
    Call<HotUserResult> searchUsers(
            @Query("q") String query,
            @Query("page") int pageId);

}

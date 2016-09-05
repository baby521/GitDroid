package git.feicui.com.gitdroid.gank.network;

import git.feicui.com.gitdroid.commons.LoggingInterceptor;
import git.feicui.com.gitdroid.gank.model.GankResult;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

/**
 * 对Retrofit简单的配置
 */
public class GankClient implements GankApi{

    /**
     * 使用：GankClient.getInstance().gankApi.getDailyData()
     * 实现了GankApi以后使用：GankClient.getInstance().getDailyData()
     */

    private GankApi gankApi;
    private static GankClient gankClient;

    public static GankClient getInstance(){
        if (gankClient==null){
            gankClient = new GankClient();
        }
        return gankClient;
    }

    private GankClient(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_URL)
                .client(okHttpClient)

                        //Retrofit 强大的功能：Gson转换器----将我们的数据请求的结果进行json转换，转换为我们需要的类型,例如类或者集合
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    @Override
    public Call<GankResult> getDailyData(@Path("yaer") int year, @Path("month") int month, @Path("day") int day) {
        return gankApi.getDailyData(year, month, day);
    }
}

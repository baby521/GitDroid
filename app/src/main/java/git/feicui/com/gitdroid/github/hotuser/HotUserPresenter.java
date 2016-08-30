package git.feicui.com.gitdroid.github.hotuser;

import android.support.annotation.NonNull;


import java.util.List;

import git.feicui.com.gitdroid.github.login.model.User;
import git.feicui.com.gitdroid.github.network.GithubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 热门开发者业务(视图业务及数据处理业务)
 * 作者：yuanchao on 2016/8/2 0002 10:33
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HotUserPresenter {

    /**
     * 热门开发者业务视图接口
     */
    public interface HotUsersView {
        /**
         * 在执行业务时将触发，用来显示一些提示信息
         */
        void showMessage(String msg);
        // --------------------------------------------------------

        /**
         * 在开始refresh业务时，将触发，用来显示下拉刷新时的内容视图
         */
        void showRefreshView();

        /**
         * 在执行refresh业务时，出现错误将触发,用来显示下拉刷新时的错误视图
         */
        void showErrorView(String errorMsg);

        /**
         * 在结束refresh业务时，将触发，用来隐藏下拉刷新时的内容视图
         */
        void hideRefreshView();

        /**
         * 在结束refresh业务，成功获取到数据后将触发，用来交付业务数据，让视图去显示数据
         */
        void refreshData(List<User> data);
        // --------------------------------------------------------

        /**
         * 在开始loadMore业务时，将触发，用来显示上拉加载时的加载中视图
         */
        void showLoadMoreLoading();

        /**
         * 在结束loadMore业务时，将触发，隐藏上拉加载时的加载中视图
         */
        void hideLoadMoreLoading();

        /**
         * 显示上拉加载时的错误视图
         */
        void showLoadMoreErro(String erroMsg);

        /**
         * 在结束loadmore业务，成功获取到数据后将触发，用来交付业务数据，让视图去显示数据
         */
        void addMoreData(List<User> datas);
    }

    // 用户列表视图接口对象
    private HotUsersView hotUsersView;

    private Call<HotUserResult> usersCall;
    private int nextPage = 0;

    public HotUserPresenter(@NonNull HotUsersView hotUsersView) {
        this.hotUsersView = hotUsersView;
    }

    // 下拉刷新处理
    public void refresh() {
        hotUsersView.hideLoadMoreLoading();
        hotUsersView.showRefreshView();
        nextPage = 1; // 永远刷新最新数据
        // HTTP API处理
        if (usersCall != null) usersCall.cancel();
        usersCall = GithubClient.getInstance().searchUsers("followers:" + ">1000", nextPage);
        usersCall.enqueue(ptrCallback);
    }

    // 加载更多处理
    public void loadMore() {
        hotUsersView.showLoadMoreLoading();
        // HTTP API处理
        if (usersCall != null) usersCall.cancel();
        usersCall = GithubClient.getInstance().searchUsers("followers:" + ">1000", nextPage);
        usersCall.enqueue(loadmoreCallback);
    }
    // 下拉，获取最新数据api callback
    private Callback<HotUserResult> ptrCallback = new Callback<HotUserResult>() {
        @Override public void onResponse(Call<HotUserResult> call, Response<HotUserResult> response) {
            hotUsersView.hideRefreshView();
            if(response.isSuccessful()){
                HotUserResult hotUserResult = response.body();
                if (hotUserResult == null) {
                    hotUsersView.showErrorView("结果为空!");
                    return;
                }
                // 取出搜索到的所有用户
                List<User> userList = hotUserResult.getRepoList();
                hotUsersView.refreshData(userList);
                // 下拉刷新成功(1), 下一面则更新为2
                nextPage = 2;
                return;
            }
            hotUsersView.showMessage("ptr onResponse " + response.code());
        }

        @Override public void onFailure(Call<HotUserResult> call, Throwable t) {
            hotUsersView.hideRefreshView();
            hotUsersView.showMessage("ptr onFailure" + t.getMessage());
        }
    };

    // 上拉，获取数据api callback
    private Callback<HotUserResult> loadmoreCallback = new Callback<HotUserResult>() {
        @Override public void onResponse(Call<HotUserResult> call, Response<HotUserResult> response) {
            hotUsersView.hideLoadMoreLoading();
            if(response.isSuccessful()){
                HotUserResult hotUserResult = response.body();
                if (hotUserResult == null) {
                    hotUsersView.showLoadMoreErro("结果为空!");
                    return;
                }
                // 取出搜索到的所有用户
                List<User> userList = hotUserResult.getRepoList();
                hotUsersView.addMoreData(userList);
                nextPage++;
                return;
            }
            hotUsersView.showMessage("loadmore onResponse " + response.code());
        }

        @Override public void onFailure(Call<HotUserResult> call, Throwable t) {
            // 视图停止刷新
            hotUsersView.hideLoadMoreLoading();
            hotUsersView.showMessage("loadmore onFailure" + t.getMessage());
        }
    };
}

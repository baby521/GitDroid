package git.feicui.com.gitdroid.github.hotrepo.repolist;


import android.util.Log;


import java.util.List;

import git.feicui.com.gitdroid.github.hotrepo.Language;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.RepoResult;
import git.feicui.com.gitdroid.github.hotrepo.repolist.view.RepoListView;
import git.feicui.com.gitdroid.github.network.GithubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：yuanchao on 2016/7/27 0027 17:30
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoListPresenter {
    // 视图的接口
    private RepoListView repoListView;
    private int nextPage = 0;
    private Language language;

    private Call<RepoResult> repoCall;

    public RepoListPresenter(RepoListView repoListView,Language language) {
        this.repoListView = repoListView;
        this.language = language;
    }

    // 下拉刷新处理
    public void refresh() {
        // 隐藏loadmore
        repoListView.hideLoadView();
        repoListView.showContent();
        nextPage = 1; // 永远刷新最新数据
        repoCall = GithubClient.getInstance().searchRepo(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(repoCallback);
    }

    // 加载更多处理
    public void loadMore() {
        repoListView.showLoadingView();
        repoCall = GithubClient.getInstance().searchRepo(
                "language:" + language.getPath()
                , nextPage);
        repoCall.enqueue(loadMoreCallback);
    }

    private final Callback<RepoResult> loadMoreCallback = new Callback<RepoResult>(){

        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadView();
            // 得到响应结果
            RepoResult reposResult = response.body();
            if (reposResult == null) {
                repoListView.showLoadError("结果为空!");
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = reposResult.getRepoList();
            repoListView.addLoadData(repoList);
            nextPage++;
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.hideLoadView();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };

    private final Callback<RepoResult> repoCallback = new Callback<RepoResult>() {
        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            // 视图停止刷新
            repoListView.stopRefre();
            // 得到响应结果
            RepoResult reposResult = response.body();
            if (reposResult == null) {
                repoListView.showError();
                return;
            }
            // 当前搜索的语言,没有仓库
            if (reposResult.getTotalCount() <= 0) {

                repoListView.refreshData(null);
                repoListView.showEmpty();
                return;
            }
            // 取出当前语言下的所有仓库
            List<Repo> repoList = reposResult.getRepoList();

            repoListView.refreshData(repoList);
            // 下拉刷新成功(1), 下一面则更新为2
            nextPage = 2;
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            // 视图停止刷新
            repoListView.stopRefre();
            repoListView.showMessage("repoCallback onFailure" + t.getMessage());
        }
    };
}

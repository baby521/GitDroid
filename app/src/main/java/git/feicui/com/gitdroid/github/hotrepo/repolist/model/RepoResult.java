package git.feicui.com.gitdroid.github.hotrepo.repolist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 仓库列表数据填充完成
 */
public class RepoResult {


    /**
     * total_count : 2074901
     * incomplete_results : false
     * items : [{}]
     */

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("items")
    private List<Repo> repoList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }

}

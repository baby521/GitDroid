package git.feicui.com.gitdroid.github.hotuser;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import git.feicui.com.gitdroid.github.login.model.User;

/**
 * 作者：yuanchao on 2016/8/2 0002 11:05
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HotUserResult {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<User> userList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<User> getRepoList() {
        return userList;
    }
    
//    "total_count": 603,
//            "incomplete_results": false,
//            "items": [
}

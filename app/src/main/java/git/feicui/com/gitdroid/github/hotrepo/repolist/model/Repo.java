package git.feicui.com.gitdroid.github.hotrepo.repolist.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import git.feicui.com.gitdroid.github.login.model.User;

/**
 * 仓库详情显示完成
 */
public class Repo implements Serializable{


    /**
     * id : 29028775
     * name : react-native
     * full_name : facebook/react-native
     * owner : {"login":"facebook","id":69631,"avatar_url":"https://avatars.githubusercontent.com/u/69631?v=3"}
     * description : A framework for building native apps with React.
     * stargazers_count : 33961
     * forks_count : 7122
     */

    private int id;
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private String description;
    @SerializedName("stargazers_count")
    private int startCount;
    @SerializedName("forks_count")
    private int forksCount;
    private User owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartCount() {
        return startCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public User getOwner() {
        return owner;
    }
}

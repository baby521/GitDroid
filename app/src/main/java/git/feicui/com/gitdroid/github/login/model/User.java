package git.feicui.com.gitdroid.github.login.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 修复登录加载
 */
public class User implements Serializable{


    /**
     * login : octocat
     * id : 1
     * avatar_url : https://github.com/images/error/octocat_happy.gif
     * name : monalisa octocat
     */

    // 登录所用的账号
    private String login;
    // 用户名
    private String name;
    // id
    private int id;

    // 用户头像路径
    @SerializedName("avatar_url")
    private String avatar;

    // 用户仓库路径
    @SerializedName("repos_url")
    private String repos;

    // 用户追随者路径
    @SerializedName("followers_url")
    private String followers;

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getRepos() {
        return repos;
    }

    public String getFollowers() {
        return followers;
    }

    @Override public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

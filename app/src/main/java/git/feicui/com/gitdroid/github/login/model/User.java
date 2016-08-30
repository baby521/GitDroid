package git.feicui.com.gitdroid.github.login.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class User implements Serializable{


    /**
     * login : octocat
     * id : 1
     * avatar_url : https://github.com/images/error/octocat_happy.gif
     * name : monalisa octocat
     */

    private String login;
    private int id;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String name;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package git.feicui.com.gitdroid.github.login.model;

/**
 * 修复登录加载
 */
public class UserRepo {

    private static String token;
    private static User user;

    public static boolean hasUserToken(){
        return token!=null;
    }

    public static boolean isEmpty(){
        return token==null || user==null;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserRepo.token = token;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserRepo.user = user;
    }
}

package git.feicui.com.gitdroid.github.login;

/**
 * 登录视图及主页面信息更改
 */
public interface LoginView {

    //显示进度
    void showProgress();

    //显示信息
    void showMessage(String msg);

    //重置WebView
    void resetWeb();

    //跳转到主页面
    void gotoMain();
}

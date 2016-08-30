package git.feicui.com.gitdroid.github.login;

/**
 * Created by Administrator on 2016/8/18 0018.
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

package git.feicui.com.gitdroid.github.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.MainActivity;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.commons.ActivityUtils;
import git.feicui.com.gitdroid.github.network.GithubApi;
import pl.droidsonroids.gif.GifImageView;


/**
 * 授权登录流程
 * <p/>
 * 1.WebView 加载一个授权Url
 * 2.同意授权后带出临时授权码code重定向到另一个url
 * 3.使用临时授权码code，获取用户token
 * 4.利用用户token获取用户信息
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    private ActivityUtils mActivityUtils;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new LoginPresenter(this);
        initWebView();
    }

    private void initWebView() {
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();

        webView.loadUrl(GithubApi.AUTH_RUL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
    }

    //进度监听，加载时显示动画效果，加载完成时显示加载内容
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress >= 100) {
                gifImageView.setVisibility(View.GONE);
            }
        }
    };

    //重定向的时候页面的刷新
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Uri uri = Uri.parse(url);
            if (GithubApi.CALL_BACK.equals(uri.getScheme())) {
                String code = uri.getQueryParameter("code");
                //进行登录
                mPresenter.login(code);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void gotoMain() {
        mActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}

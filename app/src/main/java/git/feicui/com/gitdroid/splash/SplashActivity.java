package git.feicui.com.gitdroid.splash;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import git.feicui.com.gitdroid.MainActivity;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.commons.ActivityUtils;
import git.feicui.com.gitdroid.github.login.LoginActivity;

public class SplashActivity extends FragmentActivity {

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.splash_login)
    public void login() {
        activityUtils.startActivity(LoginActivity.class);
        finish();
    }


    @OnClick(R.id.splash_use)
    public void use() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
}

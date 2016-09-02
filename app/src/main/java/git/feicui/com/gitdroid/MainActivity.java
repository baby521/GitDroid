package git.feicui.com.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.commons.ActivityUtils;
import git.feicui.com.gitdroid.favorite.FavoriteFragment;
import git.feicui.com.gitdroid.github.hotrepo.HotRepoFragment;
import git.feicui.com.gitdroid.github.hotuser.HotUserFragment;
import git.feicui.com.gitdroid.github.login.LoginActivity;
import git.feicui.com.gitdroid.github.login.model.UserRepo;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigationView)
    NavigationView navigation;
    @BindView(R.id.main_drawerlayout)
    DrawerLayout drawerlayout;

    private Button mBtnLogin;
    private ImageView mIvIcon;
    private ActivityUtils activityUtils;


    private HotRepoFragment hotRepoFragment;
    private HotUserFragment hotUserFragment;
    private FavoriteFragment favoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        activityUtils = new ActivityUtils(this);

        navigation.setNavigationItemSelectedListener(itemSelectedListener);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);

        mBtnLogin = ButterKnife.findById(navigation.getHeaderView(0), R.id.btnLogin);
        mIvIcon = ButterKnife.findById(navigation.getHeaderView(0), R.id.ivIcon);


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });

        hotRepoFragment = new HotRepoFragment();
        replaceFragment(hotRepoFragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (UserRepo.isEmpty()) {
            mBtnLogin.setText(R.string.login_github);
        }else {
            mBtnLogin.setText(R.string.switch_account);
            getSupportActionBar().setTitle(UserRepo.getUser().getName());

            ImageLoader.getInstance().displayImage(UserRepo.getUser().getAvatar(), mIvIcon);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }

    private NavigationView.OnNavigationItemSelectedListener itemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            if (item.isChecked()) {
                item.setCheckable(false);
            }
            switch (item.getItemId()) {

//                热门仓库
                case R.id.github_hot_repo: {
                    if (!hotRepoFragment.isAdded()) {
                        replaceFragment(hotRepoFragment);
                    }
                }
                break;

//                热门开发者
                case R.id.github_hot_user: {
                    if (hotUserFragment==null){
                        hotUserFragment = new HotUserFragment();
                    }
                    if (!hotUserFragment.isAdded()) {
                        replaceFragment(hotUserFragment);
                    }
                }
                break;

//                我的收藏
                case R.id.arsenal_my_repo:
                    if(favoriteFragment==null){
                        favoriteFragment = new FavoriteFragment();
                        }
                    if(!favoriteFragment.isAdded()){
                            replaceFragment(favoriteFragment);
                    }

                break;

//                每日干货
                case R.id.tips_daily: {

                }
                break;

            }


            drawerlayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };


}

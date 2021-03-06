package git.feicui.com.gitdroid.github.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.commons.ActivityUtils;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;

/**
 * 仓库详情页面
 */
public class RepoInfoActivity extends AppCompatActivity implements RepoInfoPresenter.RepoInfoView {

    private ActivityUtils activityUtils;

    private RepoInfoPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivIcon) ImageView ivIcon;
    @BindView(R.id.tvRepoInfo) TextView tvRepoInfo;
    @BindView(R.id.tvRepoStars) TextView tvRepoStars;
    @BindView(R.id.tvRepoName) TextView tvRepoName;
    @BindView(R.id.webView) WebView webView; // 用来展示readme的
    @BindView(R.id.progressBar) ProgressBar progressBar; // loading

    private Repo repo; // 当前仓库

    private static final String KEY_REPO = "key_repo";

    public static void open(Context context, @NonNull Repo repo) {
        Intent intent = new Intent(context, RepoInfoActivity.class);
        intent.putExtra(KEY_REPO, repo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        //设置View布局的时候就会触发我们的onContentChanged
        setContentView(R.layout.activity_repo_info);
        presenter = new RepoInfoPresenter(this);
        presenter.getReadme(repo);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        // 获取到Repo
        repo = (Repo) getIntent().getSerializableExtra(KEY_REPO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 使用仓库名称做为title
        getSupportActionBar().setTitle(repo.getName());
        // 设置仓库信息
        tvRepoName.setText(repo.getFullName());
        tvRepoInfo.setText(repo.getDescription());
        tvRepoStars.setText(String.format("start: %d  fork: %d", repo.getStarCount(), repo.getForksCount()));
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(), ivIcon);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void showProgrss() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override public void setData(String htmlContent) {
        //加载html字符串数据
        webView.loadData(htmlContent, "text/html", "UTF-8");
    }
}

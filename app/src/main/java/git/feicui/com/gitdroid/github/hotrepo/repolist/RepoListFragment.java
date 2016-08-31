package git.feicui.com.gitdroid.github.hotrepo.repolist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.commons.ActivityUtils;
import git.feicui.com.gitdroid.components.FooterView;
import git.feicui.com.gitdroid.github.hotrepo.Language;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;
import git.feicui.com.gitdroid.github.hotrepo.repolist.view.RepoListView;
import git.feicui.com.gitdroid.github.repoinfo.RepoInfoActivity;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 热门开发者刷新数据展示
 */
public class RepoListFragment extends Fragment implements RepoListView {

    @BindView(R.id.lvRepos)
    ListView lvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    private static final String KEY_LANGUAGE = "key_language";

    public static RepoListFragment getInstance(Language language){
        RepoListFragment fragment = new RepoListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LANGUAGE,language);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Language getLanuage(){
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    private RepoListPresenter presenter;

    private RepoListAdapter adapter;
    private FooterView footerView;
    private ActivityUtils activityUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this, view);
        adapter = new RepoListAdapter();
        lvRepos.setAdapter(adapter);
        presenter = new RepoListPresenter(this,getLanuage());

        lvRepos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(), repo);
            }
        });

        //下拉刷新
        initPullToRefresh();
//        上拉加载
        initLoadMore();

        if (adapter.getCount()==0){
            ptrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrFrameLayout.autoRefresh();

                }
            },200);
        }
    }

    private void initLoadMore() {
        footerView = new FooterView(getContext());
        Mugen.with(lvRepos, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                //上拉加载操作
                presenter.loadMore();
            }

            @Override
            public boolean isLoading() {
                return lvRepos.getFooterViewsCount()>0 && footerView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return lvRepos.getFooterViewsCount()>0&&footerView.isComplete();
            }
        }).start();
    }

    private void initPullToRefresh() {
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrFrameLayout.setDurationToCloseHeader(150);
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0,60,0,60);
        header.initWithString("I LIKE JAVA");
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //做刷新操作
                presenter.refresh();
            }
        });


    }


    /**
     * 下拉刷新视图展示：
     * 1.显示刷新视图
     * 2.显示加载错误信息
     * 3.显示空白页面
     * 4.显示刷新的新的数据
     */

    //    显示刷新的视图
    @Override
    public void showContent() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    //    显示加载错误信息
    @Override
    public void showError(String msg) {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

//    显示空白页面
    @Override
    public void showEmpty() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

//    显示刷新的新的数据
    @Override
    public void refreshData(List<Repo> list) {
//        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

//    停止刷新
    @Override
    public void stopRefre() {
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }


    /**
     * 上拉加载视图
     * 1.显示加载中的视图
     * 2.隐藏加载视图
     * 3.错误视图
     * 4.添加数据
     */

    @Override
    public void showLoadingView() {
        if (lvRepos.getFooterViewsCount()==0){
            lvRepos.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadView() {
        lvRepos.removeFooterView(footerView);
    }

    @Override
    public void showLoadError(String msg) {
        if (lvRepos.getFooterViewsCount()>0){
            lvRepos.addFooterView(footerView);
        }
        footerView.showError(msg);
    }

    @Override
    public void addLoadData(List<Repo> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

}

package git.feicui.com.gitdroid.github.hotuser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.commons.ActivityUtils;
import git.feicui.com.gitdroid.components.FooterView;
import git.feicui.com.gitdroid.github.login.model.User;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotUserFragment extends Fragment implements HotUserPresenter.HotUsersView {

    @BindView(R.id.lvRepos)
    ListView lvUser;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    private HotUserAdapter adapter;
    private FooterView footerView;
    private ActivityUtils activityUtils;

    private HotUserPresenter presenter;

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
        presenter = new HotUserPresenter(this);
        adapter = new HotUserAdapter();
        lvUser.setAdapter(adapter);

        //下拉刷新
        initPullToRefresh();
//        上拉加载
        initLoadMore();

        if (adapter.getCount() == 0) {
            ptrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrFrameLayout.autoRefresh();

                }
            }, 200);
        }
    }
    //加载的基本配置
    private void initLoadMore() {
        footerView = new FooterView(getContext());
        Mugen.with(lvUser, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                //上拉加载操作
                presenter.loadMore();
            }

            @Override
            public boolean isLoading() {
                return lvUser.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return lvUser.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }
    //刷新的基本配置
    private void initPullToRefresh() {
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrFrameLayout.setDurationToCloseHeader(150);
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, 60, 0, 60);
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

    //    显示刷新的新的数据
    @Override
    public void refreshData(List<User> list) {
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadMoreLoading() {
        if (lvUser.getFooterViewsCount() == 0) {
            lvUser.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMoreLoading() {
        lvUser.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreErro(String erroMsg) {
        if (lvUser.getFooterViewsCount() > 0) {
            lvUser.addFooterView(footerView);
        }
        footerView.showError(erroMsg);
    }

    @Override
    public void addMoreData(List<User> datas) {
        adapter.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(String errorMsg) {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshView() {
        ptrFrameLayout.refreshComplete();
    }


    /**
     * 上拉加载视图
     * 1.显示加载中的视图
     * 2.隐藏加载视图
     * 3.错误视图
     * 4.添加数据
     */
}

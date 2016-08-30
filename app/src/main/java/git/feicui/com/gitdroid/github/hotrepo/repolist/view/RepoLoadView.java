package git.feicui.com.gitdroid.github.hotrepo.repolist.view;


import java.util.List;

import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public interface RepoLoadView {
/**
 * 上拉加载视图
 * 1.显示加载中的视图
 * 2.隐藏加载视图
 * 3.错误信息
 * 4.添加数据
 *
 */
//    显示加载中的视图
    void showLoadingView();

//    隐藏加载视图
    void hideLoadView();

//    错误信息
    void showLoadError(String msg);

//    添加数据
    void addLoadData(List<Repo> list);
}

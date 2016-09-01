package git.feicui.com.gitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.favorite.dao.DBHelp;
import git.feicui.com.gitdroid.favorite.dao.LocalRepoDao;
import git.feicui.com.gitdroid.favorite.dao.RepoGroupDao;
import git.feicui.com.gitdroid.favorite.model.RepoGroup;

/**
 * Created by Administrator on 16-9-1.
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.tvGroupType)
    TextView tvGroupType;
    @BindView(R.id.btnFilter)
    ImageButton btnFilter;
    @BindView(R.id.listView)
    ListView listView;

    private RepoGroupDao repoGroupDao;
    private LocalRepoDao localRepoDao;
    private FavoriteAdapter favoriteAdapter;
    private int currentRepoGroupId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repoGroupDao = new RepoGroupDao(DBHelp.getInstance(getContext()));
        localRepoDao = new LocalRepoDao(DBHelp.getInstance(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        favoriteAdapter = new FavoriteAdapter();
        listView.setAdapter(favoriteAdapter);

        //默认显示的是全部的数据
        setData(R.id.repo_group_all);

//        Toast.makeText(getContext(),"本地仓库数据数量： "+localRepoDao.queryAll().size(),Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnFilter)
    public void showPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);

        //给PopupMenu填充本地Menu
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        popupMenu.setOnMenuItemClickListener(this);
        //我们自己在类别表里面其他的分类，怎么进行填充
        /**
         * 1.拿到Menu
         * 2.读取数据库数据
         * 3.数据填充到Menu上
         */
        Menu menu = popupMenu.getMenu();
        List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
        for (RepoGroup repo : repoGroups) {
            menu.add(Menu.NONE,repo.getId(),Menu.NONE,repo.getName());
        }
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        /**
         * 1.改变标题
         * 2.数据改变
         */

        tvGroupType.setText(item.getTitle().toString());
        //根据我们点击的仓库类别的id去获取不同的类别仓库信息
        currentRepoGroupId = item.getItemId();
        setData(currentRepoGroupId);
        return true;
    }

    private void setData(int groupId) {
        switch (groupId){
            case R.id.repo_group_all:
                favoriteAdapter.setData(localRepoDao.queryAll());
                break;
            case R.id.repo_group_no:
                favoriteAdapter.setData(localRepoDao.queryNoGroup());
                break;
            default:
                favoriteAdapter.setData(localRepoDao.queryForGroupId(currentRepoGroupId));
                break;
        }

    }
}

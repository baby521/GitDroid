package git.feicui.com.gitdroid.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.favorite.model.LocalRepo;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;

/**
 * Created by Administrator on 16-9-1.
 */
public class FavoriteAdapter extends BaseAdapter{

    private List<LocalRepo> datas;

    public FavoriteAdapter(){
        datas = new ArrayList<>();
    }

    public void setData(List<LocalRepo> localRepos){
        datas.clear();
        datas.addAll(localRepos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public LocalRepo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_repo, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        LocalRepo localRepo =  getItem(position);// 当前item选的"数据"
        viewHolder.tvRepoName.setText(localRepo.getFullName());
        viewHolder.tvRepoInfo.setText(localRepo.getDescription());
        viewHolder.tvRepoStars.setText(localRepo.getStargazersCount() + "");
        ImageLoader.getInstance().displayImage(localRepo.getAvatarUrl(), viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvRepoName)
        TextView tvRepoName;
        @BindView(R.id.tvRepoInfo) TextView tvRepoInfo;
        @BindView(R.id.tvRepoStars) TextView tvRepoStars;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}

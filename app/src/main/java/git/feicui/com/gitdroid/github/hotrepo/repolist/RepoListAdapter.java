package git.feicui.com.gitdroid.github.hotrepo.repolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.github.hotrepo.repolist.model.Repo;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class RepoListAdapter extends BaseAdapter{

    private ArrayList<Repo> mData;

    public RepoListAdapter() {
        mData = new ArrayList<>();
    }

    public void addAll(Collection<Repo> repos){
        mData.addAll(repos);
        notifyDataSetChanged();
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Repo getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_repo, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Repo repo = mData.get(position);// 当前item选的"数据"
        viewHolder.tvRepoName.setText(repo.getFullName());
        viewHolder.tvRepoInfo.setText(repo.getDescription());
        viewHolder.tvRepoStars.setText(repo.getStartCount() + "");
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatarUrl(), viewHolder.ivIcon);
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

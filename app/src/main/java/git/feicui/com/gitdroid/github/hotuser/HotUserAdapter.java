package git.feicui.com.gitdroid.github.hotuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.github.login.model.User;

/**
 * 作者：yuanchao on 2016/8/1 0001 11:28
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HotUserAdapter extends BaseAdapter {

    private final ArrayList<User> datas;

    public HotUserAdapter() {
        datas = new ArrayList<User>();
    }

    public void addAll(Collection<User> users){
        datas.addAll(users);
        notifyDataSetChanged();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return datas.size();
    }

    @Override public User getItem(int position) {
        return datas.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_user, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        User user = getItem(position); // 当前item选的"数据"
        viewHolder.tvLoginName.setText(user.getLogin());
        ImageLoader.getInstance().displayImage(user.getAvatarUrl(), viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivIcon) ImageView ivIcon;
        @BindView(R.id.tvLoginName) TextView tvLoginName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}

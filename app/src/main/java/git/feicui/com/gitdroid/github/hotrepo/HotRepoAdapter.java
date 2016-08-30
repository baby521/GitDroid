package git.feicui.com.gitdroid.github.hotrepo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

import git.feicui.com.gitdroid.github.hotrepo.repolist.RepoListFragment;

/**
 * Created by gqq on 16/8/15.
 */
public class HotRepoAdapter extends FragmentPagerAdapter{

    private List<Language> languages;

    public HotRepoAdapter(FragmentManager fm, Context context) {
        super(fm);
        languages = Language.getDefaultLanguage(context);
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages == null?0:languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}

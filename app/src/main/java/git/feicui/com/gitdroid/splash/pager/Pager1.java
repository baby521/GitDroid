package git.feicui.com.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import git.feicui.com.gitdroid.R;

/**
 * ViewPager页面动画效果展示
 * 提交Splash页面ViewPager的基本实现
 */

public class Pager1 extends FrameLayout {


    public Pager1(Context context) {
        this(context, null);
    }

    public Pager1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Pager1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_1, this, true);
    }


}

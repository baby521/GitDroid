package git.feicui.com.gitdroid.splash;


import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import git.feicui.com.gitdroid.R;
import git.feicui.com.gitdroid.splash.pager.Pager2;
import me.relex.circleindicator.CircleIndicator;

/**
 * Splash页面的动画
 */
public class SplashPagerFragment extends Fragment {


    @BindView(R.id.ivPhoneFont)
    ImageView ivPhoneFont;
    @BindView(R.id.layoutPhone)
    FrameLayout layoutPhone;
    @BindView(R.id.content)
    FrameLayout frameLayout;
    @BindView(R.id.splash_viewpager)
    ViewPager viewpager;
    @BindView(R.id.splash_indicator)
    CircleIndicator indicator;

    @BindColor(R.color.colorGreen) int colorGreen;
    @BindColor(R.color.colorRed) int colorRed;
    @BindColor(R.color.colorYellow) int colorYellow;

    private SplashPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new SplashPagerAdapter(getContext());
        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);

        viewpager.addOnPageChangeListener(pagerColorListener);
        viewpager.addOnPageChangeListener(phoneViewListener);

    }

    private ViewPager.OnPageChangeListener pagerColorListener = new ViewPager.OnPageChangeListener() {

        ArgbEvaluator evaluator = new ArgbEvaluator();
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==0){
                int color = (int) evaluator.evaluate(positionOffset,colorGreen,colorRed);
                frameLayout.setBackgroundColor(color);
                return ;
            }
            if (position==1){
                int color = (int) evaluator.evaluate(positionOffset,colorRed,colorYellow);
                frameLayout.setBackgroundColor(color);
                return ;
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ViewPager.OnPageChangeListener phoneViewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==0){
                float scale = 0.3f+positionOffset*0.7f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);

                int translate = (int) ((positionOffset-1)*360);
                layoutPhone.setTranslationX(translate);

                ivPhoneFont.setAlpha(positionOffset);
            }

            if (position==1){
                layoutPhone.setTranslationX(-positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (position==2){
                Pager2 pager2 = (Pager2) adapter.getView(position);
                pager2.showAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

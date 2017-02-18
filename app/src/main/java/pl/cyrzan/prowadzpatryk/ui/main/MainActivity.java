package pl.cyrzan.prowadzpatryk.ui.main;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.map_form_image)
    ImageView mapFormImage;
    @BindView(R.id.trips_image)
    ImageView tripsImage;

    private ArrayList<ImageView> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProwadzPatrykApplication.get(this).getComponent()
                .plus(new ActivityModule(this))
                .inject(this);

        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        correctTitleMargin();

        initViewPager(viewPager);
    }

    private void correctTitleMargin() {
        toolbar.setContentInsetsAbsolute(16, 0);
    }

    private void initViewPager(ViewPager viewPager) {
        tabs.add(mapFormImage);
        tabs.add(tripsImage);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mapFormImage.setSelected(true);
    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mainPresenter != null) {
            mainPresenter.detachView();
        }
    }

    @OnClick(R.id.trips_image)
    public void onClickTrips(){
        viewPager.setCurrentItem(1);
    }

    @OnClick(R.id.map_form_image)
    public void onClickMapForm(){
        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}

package pl.cyrzan.prowadzpatryk.ui.main;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.DachshundIndicator;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.ui.mapwithform.MapWithFormFragment;
import pl.cyrzan.prowadzpatryk.util.ViewUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

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
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tab_layout)
    DachshundTabLayout tabLayout;

    private MainAdapter adapter;

    private ArrayList<ImageView> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        initToolbar();
        initViewPager(viewPager);
    }

    @Override
    protected void setupActivityComponent() {
        ProwadzPatrykApplication.get(this).getComponent()
                .plus(new ActivityModule(this))
                .inject(this);
    }

    @Override
    public void configViews() {
        mainPresenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void correctTitleMargin() {
        toolbar.setContentInsetsAbsolute(16, 0);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        correctTitleMargin();
        if(getSupportActionBar() != null) {
            Log.i(TAG, "actiobar");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void initViewPager(ViewPager viewPager) {
        tabs.add(mapFormImage);
        tabs.add(tripsImage);
        adapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
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
        tabLayout.setupWithViewPager(viewPager);
    }

    public ViewPager getViewPager() {
        if (null == viewPager) {
            return null;
        }
        return viewPager;
    }

    public MainAdapter getAdapter(){
        if(adapter == null){
            return null;
        }
        return adapter;
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

    private void setUpNavDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.action_about: {
                        Log.i(TAG, "showAbout");
                        //showAbout();
                        break;
                    }
                    default:

                }
                drawerLayout.closeDrawers();
                return true;
            }


        });
    }

    @Override
    public void showError(int errorReport) {
        ViewUtil.makeToast(getApplicationContext(), getText(errorReport).toString());
    }

    @Override
    public void complete() {

    }
}

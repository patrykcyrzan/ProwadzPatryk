package pl.cyrzan.prowadzpatryk.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import pl.cyrzan.prowadzpatryk.ui.mapwithform.MapWithFormFragment;

/**
 * Created by Patryk on 10.02.2017.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 2;
    private FragmentManager fm;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapWithFormFragment();
            case 1:
                return new TripsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    public Fragment getFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  fm.findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

}

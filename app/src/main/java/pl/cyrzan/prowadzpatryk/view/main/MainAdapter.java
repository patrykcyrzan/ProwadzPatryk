package pl.cyrzan.prowadzpatryk.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Patryk on 10.02.2017.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 2;

    public MainAdapter(FragmentManager fm) {
        super(fm);
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

}

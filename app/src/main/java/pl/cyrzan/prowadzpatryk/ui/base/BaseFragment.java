package pl.cyrzan.prowadzpatryk.ui.base;

import android.os.Bundle;
//import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.view.View;

//import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by Patryk on 09.02.2017.
 * Abstract Fragment that every other Fragment in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent are kept
 * across configuration changes.
 */

//public abstract class BaseFragment extends RxFragment {
public abstract class BaseFragment extends Fragment{

    @Override
    //@CallSuper
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupActivityComponent();
        configViews();
        ButterKnife.bind(this, view);
    }

    protected abstract void setupActivityComponent();

    public abstract void configViews();
}

package pl.cyrzan.prowadzpatryk.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.util.ViewUtil;

import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public abstract class BaseActivity extends RxAppCompatActivity {
    private ViewGroup container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        container = (ViewGroup) findViewById(R.id.container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && addExtraTopPadding()) {
            container.setPadding(0, ViewUtil.getStatusBarHeight(this), 0, 0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color;
            int colorResourceId = android.R.color.transparent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = getResources().getColor(colorResourceId, getTheme());
            } else { //noinspection deprecation
                color = getResources().getColor(colorResourceId);
            }
            if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
                getWindow().setNavigationBarColor(color);
            }
        }
    }

    protected ViewGroup getContainer() {
        return container;
    }

    /**
     * Override if needed, this solve issue with 4.4 status bar
     *
     * @return
     */
    protected boolean addExtraTopPadding() {
        return true;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, container, true);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        container.addView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        container.addView(view, params);
        ButterKnife.bind(this);
    }

}

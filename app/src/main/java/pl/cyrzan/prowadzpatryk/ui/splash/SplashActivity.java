package pl.cyrzan.prowadzpatryk.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jrummyapps.android.widget.AnimatedSvgView;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter splashPresenter;

    @BindView(R.id.imageView)
    AnimatedSvgView svgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        showSplashAfterDelay();
    }

    @Override
    protected void setupActivityComponent() {
        ProwadzPatrykApplication.get(this).getComponent()
                .plus(new ActivityModule(this))
                .inject(this);
    }

    @Override
    public void configViews() {
        splashPresenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    private void showSplashAfterDelay() {
        svgView.start();
        (new Handler()).postDelayed(() -> splashPresenter.loadSplash(), 1100);

    }

    @Override
    public void showError(int errorReport) {

    }

    @Override
    public void complete() {

    }

    @Override
    public void loadMainScreen() {
        Intent mainAct = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(mainAct);
        finish();
    }
}

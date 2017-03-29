package pl.cyrzan.prowadzpatryk.ui.about;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;


public class AboutActivity extends BaseActivity implements AboutContract.View {

    @Inject
    AboutPresenter aboutPresenter;

    @BindView(R.id.toolbarLayout)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        setupToolbar();
    }

    @Override
    protected void setupActivityComponent() {
        ProwadzPatrykApplication.get(this).getComponent()
                .plus(new ActivityModule(this))
                .inject(this);
    }

    @Override
    public void configViews() {
        aboutPresenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    public static void startActivity(@NonNull Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getText(R.string.about_app));
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError(int errorReport) {

    }

    @Override
    public void complete() {

    }
}

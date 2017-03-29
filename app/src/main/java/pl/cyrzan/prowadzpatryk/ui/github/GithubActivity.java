package pl.cyrzan.prowadzpatryk.ui.github;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.vlad1m1r.lemniscate.BernoullisProgressView;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class GithubActivity extends BaseActivity implements GithubContract.View {

    @Inject
    GithubPresenter githubPresenter;

    @BindView(R.id.toolbarLayout)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;
    @BindView(R.id.progressBar)
    BernoullisProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        setupToolbar();
        initWebView();
        webView.loadUrl("https://github.com/patrykcyrzan/ProwadzPatryk");
    }

    @Override
    protected void setupActivityComponent() {
        ProwadzPatrykApplication.get(this).getComponent()
                .plus(new ActivityModule(this))
                .inject(this);
    }

    @Override
    public void configViews() {
        githubPresenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_github;
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getText(R.string.github));
        }
    }

    public static void startActivity(@NonNull Context context) {
        Intent intent = new Intent(context, GithubActivity.class);
        context.startActivity(intent);
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressLayout.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressLayout.setVisibility(View.GONE);
                progressView.setVisibility(View.GONE);
            }
        });
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

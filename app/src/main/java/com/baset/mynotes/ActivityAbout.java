package com.baset.mynotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ActivityAbout extends AppCompatActivity implements View.OnClickListener {
private Toolbar toolbar;
private TextView tvAppVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupViews();
        setupToolabr();
        setupAppVersionTv();
    }

    private void setupAppVersionTv() {
        tvAppVersion.setText(getResources().getString(R.string.version)+" "+ BuildConfig.VERSION_NAME);
    }

    private void setupToolabr() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void setupViews() {
        toolbar=findViewById(R.id.toolbar);
        tvAppVersion=findViewById(R.id.about_app_version);
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
switch (id){
    case R.id.about_github_source:
        viewGithub();
        break;
    case R.id.about_developer_website:
        viewWeblog();
        break;
    case R.id.about_send_feedback:
        sendFeedback();
        break;
}
    }

    private void sendFeedback() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"baset.esmaili0@gmail.com"});
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    private void viewWeblog() {
        Intent weblog = new Intent(Intent.ACTION_VIEW, Uri.parse("https://basetesmaeili.blogspot.com/"));
        startActivity(weblog);

    }

    private void viewGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/BasetEsmaeili/MyNotes"));
        startActivity(intent);
    }
}

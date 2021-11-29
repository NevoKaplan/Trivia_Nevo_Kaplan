package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton contin = findViewById(R.id.continued);
        contin.setOnClickListener(this::OnClick);
    }


    public void goToSite (View view) {
        goToUrl ("https://nevokaplan4.wixsite.com/bibis-adventure");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void OnClick(View view) {
        Intent intent = new Intent(this, game.class);
    }

    @Override
    public void home() {}
}
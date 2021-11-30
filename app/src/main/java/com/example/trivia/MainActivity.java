package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String player;
    TextView p1Text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button contin = findViewById(R.id.continued);
        contin.setOnClickListener(this);
        p1Text = findViewById(R.id.editTextTextPersonName);
        super.play(0);
    }


    public void goToSite (View view) {
        goToUrl ("https://nevokaplan4.wixsite.com/bibis-adventure");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void continueClick() {
        player = "player";
        Intent intent = new Intent(this, game.class);
        if (mediaPlayer != null) {
            int currentPos = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            intent.putExtra("currentPos", currentPos);
        }
        if (!(p1Text.getText().toString().matches(""))) {
            player = p1Text.getText().toString();
        }

        intent.putExtra("player", player);
        startActivityForResult(intent, 4);
        System.out.println("here");

    }

    @Override
    public void home() {} // so home button won't exit application

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continued)
            continueClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4)
            if (soundOn)
                super.play(data.getIntExtra("currentPos", 0));
    }

    @Override
    protected void onStop () {
        super.onStop();
        }
}
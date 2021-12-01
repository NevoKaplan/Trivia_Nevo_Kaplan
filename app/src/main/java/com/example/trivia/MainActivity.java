package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.annotation.SuppressLint;
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
    int chosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button contin = findViewById(R.id.continued);
        ImageButton first = findViewById(R.id.Ten_Questions);
        ImageButton second = findViewById(R.id.Lives);
        ImageButton third = findViewById(R.id.Basic);

        contin.setOnClickListener(this);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        chosen = 1;

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
        intent.putExtra("chosen", chosen);
        intent.putExtra("player", player);
        startActivityForResult(intent, 4);

    }

    @Override
    public void home() {} // so home button won't exit application

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Ten_Questions:
                ((ImageButton)findViewById(R.id.Ten_Questions)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                ((ImageButton)findViewById(R.id.Lives)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.Basic)).setBackground(getDrawable(R.drawable.topicbutton));
                chosen = 1;
                break;
            case R.id.Lives:
                ((ImageButton)findViewById(R.id.Ten_Questions)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.Lives)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                ((ImageButton)findViewById(R.id.Basic)).setBackground(getDrawable(R.drawable.topicbutton));
                chosen = 2;
                break;
            case R.id.Basic:
                ((ImageButton)findViewById(R.id.Ten_Questions)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.Lives)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.Basic)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                chosen = 3;
                break;
            default: // four
                continueClick();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.play(data.getIntExtra("currentPos", 0));
    }



    @Override
    protected void onStop () {
        super.onStop();
        }
}
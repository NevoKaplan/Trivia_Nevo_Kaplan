package com.example.trivia;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String player;
    private String password;
    int chosen;
    Dialog d;


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


        sp = getSharedPreferences("details", 0);
        player = sp.getString("name", "player");
        password = sp.getString("pass", "");
        count = new int[3];
        for(int i=0;i<3;i++)
            count[i] = sp.getInt("count_" + i, 0);

        scores = new float[3];
        for(int i=0;i<3;i++)
            scores[i] = sp.getFloat("scores_" + i, 360000);
        hasUser = false;
        createLoginDialog();

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
        Intent intent = new Intent(this, game.class);
        intent.putExtra("chosen", chosen);
        if (hasUser) {
            count[chosen-1]++;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("count_" + (chosen-1), count[chosen-1]);
            editor.commit();
        }
        intent.putExtra("hasUser", hasUser);
        intent.putExtra("count", count);
        intent.putExtra("scores", scores);
        intent.putExtra("username", user_name);
        if (mediaPlayer != null) {
            int currentPos = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            intent.putExtra("currentPos", currentPos);
        }
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
            default:
                continueClick();
                break;
        }
    }


    public void clickDialog(View view) {
        player = ((TextView)d.findViewById(R.id.enterName)).getText().toString();
        password = ((TextView)d.findViewById(R.id.enterPass)).getText().toString();
        // clicked on enter and the texts aren't empty
        if ((hasUser || (view.getId() == R.id.enter) && (!player.matches("player") && !password.matches("")))) {
            // create user or edit user details
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", player);
            editor.putString("pass", password);
            user_name = player;
            editor.commit();
            hasUser = true;
        }

        else {
            hasUser = false;
            user_name = "player";
        }

        d.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.play(data.getIntExtra("currentPos", 0));
        scores = data.getFloatArrayExtra("scores");
    }



    @Override
    protected void onStop () {
        super.onStop();
        }

    public void createLoginDialog() {
        d = new Dialog(this);

        d.setContentView(R.layout.custom_dialog);
        d.setTitle("Login");
        d.setCancelable(false);
        ((Button)d.findViewById(R.id.enter)).setOnClickListener(this::clickDialog);
        ((Button)d.findViewById(R.id.skip)).setOnClickListener(this::clickDialog);
        if (!(player.equals("player")) && !(password.equals(""))) {
            hasUser = true;
        }
        ((TextView)d.findViewById(R.id.enterName)).setText(player);
        ((TextView)d.findViewById(R.id.enterPass)).setText(password);
        d.show();
    }
}
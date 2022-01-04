package com.example.trivia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    Menu menu;
    boolean soundOn;
    MediaPlayer mediaPlayer;
    boolean hasUser;
    String user_name;
    int[] count;
    float[] scores;
    SharedPreferences sp;
    // Activity code here

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        this.soundOn = true;
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_music) {
            MenuItem music = menu.findItem(R.id.item_music);
            if (music.getIcon().getConstantState().equals((getDrawable(R.drawable.sound_on).getConstantState()))) {
                music.setIcon(R.drawable.sound_off);
                pause();
                this.soundOn = false;
            }
            else {
                music.setIcon(R.drawable.sound_on);
                play();
                this.soundOn = true;
            }
        }

        else if (id == R.id.item_exit) {
            finish();
            finishAffinity();
            onStop();
        }

        else if (id == R.id.item_home) { home(); }

        else if (id == R.id.trophy) { stats(); }

        return true;
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    public void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.instrumental);
            mediaPlayer.setLooping(true);
        }
        mediaPlayer.start();
    }

    public void play(int time) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.instrumental);
        }
        mediaPlayer.seekTo(time);
        mediaPlayer.start();
    }

    public void pause() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    public void home() {
        finish();
    }

    public void stats() {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(user_name + "'s statistics");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert = builder.create();

        if (hasUser) {
            String str = "You entered Ten Questions Mode " + count[0] + " times, your highest score is ";
            if (scores[0] != 360000)
                str += scores[0];
            else
                str += "-no data-";
            str += "\nYou entered Lives Mode " + count[1] + " times, your highest score is ";
            if (scores[1] != 360000)
                str += scores[1];
            else
                str += "-no data-";
            str += "\nYou entered Basic Mode " + count[2] + " times, your highest score is ";
            if (scores[2] != 360000)
                str += scores[2];
            else
                str += "-no data-";

            alert.setMessage(str);
        }
        else
            alert.setMessage("Only registered users can access this feature.\nRestart the app to register.");
        alert.show();
    }
}
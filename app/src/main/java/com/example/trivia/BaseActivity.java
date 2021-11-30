package com.example.trivia;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    Menu menu;
    boolean soundOn;
    MediaPlayer mediaPlayer;
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
            if (music.getIcon().getConstantState().equals((getDrawable(R.drawable.sound_off).getConstantState()))) {
                music.setIcon(R.drawable.sound_on);
                pause();
                this.soundOn = false;
            }
            else {
                music.setIcon(R.drawable.sound_off);
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

}
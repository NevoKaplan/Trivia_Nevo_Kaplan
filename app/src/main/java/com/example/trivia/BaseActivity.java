package com.example.trivia;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    Menu menu;
    // Activity code here

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_music) {
            MenuItem music = menu.findItem(R.id.item_music);
            if (music.getIcon().getConstantState().equals((getDrawable(R.drawable.sound_off).getConstantState())))
                music.setIcon(R.drawable.sound_on);
            else
                music.setIcon(R.drawable.sound_off);
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
    }

    public void home() { finish(); }
}
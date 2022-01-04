package com.example.trivia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class game extends BaseActivity implements View.OnClickListener{

    Controller controller;
    private String answer;
    private String realAnswer;
    private TextView question;
    private String playerName;
    static Random rnd = new Random();
    Toast toast;
    AlertDialog alert;
    long start;
    SoundPool soundPool;
    private int correct_sound, incorrect_sound, length, chosen, lives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        question = findViewById(R.id.questioned);
        controller = new Controller();
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        answer = "hello Dvir";
        Intent intent = getIntent();
        chosen = intent.getIntExtra("chosen", 1);
        hasUser = intent.getBooleanExtra("hasUser", false);
        count = intent.getIntArrayExtra("count");
        scores = intent.getFloatArrayExtra("scores");
        user_name = intent.getStringExtra("username");
        sp = getSharedPreferences("details", 0);
        length = controller.getQuestionLen();
        switch (chosen) {
            case 1:
                length = 10;
                break;
            case 2:
                ((ImageView)findViewById(R.id.heartOne)).setVisibility(View.VISIBLE);
                ((ImageView)findViewById(R.id.heartTwo)).setVisibility(View.VISIBLE);
                ((ImageView)findViewById(R.id.heartThree)).setVisibility(View.VISIBLE);
                lives = 3;
                break;
            default:
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else {
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }
        correct_sound = soundPool.load(this, R.raw.correct_sound, 1);
        incorrect_sound = soundPool.load(this, R.raw.incorrect_sound, 1);

        ImageButton one = findViewById(R.id.one);
        ImageButton two = findViewById(R.id.two);
        ImageButton three = findViewById(R.id.three);
        ImageButton four = findViewById(R.id.four);
        ImageView locked = findViewById(R.id.imageView);

        one.setOnClickListener(this::onClick);
        two.setOnClickListener(this::onClick);
        three.setOnClickListener(this::onClick);
        four.setOnClickListener(this::onClick);
        locked.setOnClickListener(this::locked);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        playerName = user_name;
        ((TextView)findViewById(R.id.name)).setText(playerName);
        builder.setCancelable(false);
        builder.setTitle("Well Played, " + playerName + "!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                home();
            }
        });
        alert = builder.create();

        shuffle(true);
        start = System.currentTimeMillis();

        super.play(intent.getIntExtra("currentPos", 0));
    }

    public void shuffle(boolean flag) {
        Question current = controller.createQuestion();
        int questionNumber = controller.getQuestionNum();
        if (flag && current != null && questionNumber <= length) {
            question.setText(current.getQuestion());
            realAnswer = current.getAnswer();
            String[] replies = current.getRe();
            shuffleArray(replies);
            ((TextView) findViewById(R.id.oneAnswer)).setText(replies[0]);
            ((TextView) findViewById(R.id.twoAnswer)).setText(replies[1]);
            ((TextView) findViewById(R.id.threeAnswer)).setText(replies[2]);
            ((TextView) findViewById(R.id.fourAnswer)).setText(replies[3]);
            ((TextView) findViewById(R.id.textView3)).setText("Question #" + questionNumber);
        }
        else {
            long end = System.currentTimeMillis();
            float len = (float)(Math.round((((end - start) / 1000.0) * 100.0))/100.0);
            int questionsAnswered = (controller.getScore()/10);
            if (hasUser && scores[chosen-1] > len && questionsAnswered == length) {
                System.out.println("here");
                scores[chosen-1] = len;
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("scores_" + (chosen-1), scores[chosen-1]);
                editor.commit();
            }
            alert.setMessage("You answered " + questionsAnswered + "/" + length + " questions correctly.\nYour time is: " + len + " seconds.");
            alert.show();
        }
    }

    public void shuffleArray(String[] ar)
    {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one:
                ((ImageButton)findViewById(R.id.one)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                ((ImageButton)findViewById(R.id.two)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.three)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.four)).setBackground(getDrawable(R.drawable.topicbutton));
                answer = ((TextView)findViewById(R.id.oneAnswer)).getText().toString();
                break;
            case R.id.two:
                ((ImageButton)findViewById(R.id.one)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.two)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                ((ImageButton)findViewById(R.id.three)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.four)).setBackground(getDrawable(R.drawable.topicbutton));
                answer = ((TextView)findViewById(R.id.twoAnswer)).getText().toString();
                break;
            case R.id.three:
                ((ImageButton)findViewById(R.id.one)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.two)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.three)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                ((ImageButton)findViewById(R.id.four)).setBackground(getDrawable(R.drawable.topicbutton));
                answer = ((TextView)findViewById(R.id.threeAnswer)).getText().toString();
                break;
            default: // four
                ((ImageButton)findViewById(R.id.one)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.two)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.three)).setBackground(getDrawable(R.drawable.topicbutton));
                ((ImageButton)findViewById(R.id.four)).setBackground(getDrawable(R.drawable.topicbuttonclicked));
                answer = ((TextView)findViewById(R.id.fourAnswer)).getText().toString();
                break;
        }
    }

    public void locked(View view) {
        boolean flag = true;
        if (!answer.equals("hello Dvir")) {
            if (answer.equals(realAnswer)) {
                controller.addScore(10);
                toast.setText("Correct! ( ͡ʘ ͜ʖ ͡ʘ)");
                soundPool.play(correct_sound, 1, 1, 0, 0, 1);
            }
            else {
                toast.setText("Incorrect ( ͡° ʖ̯ ͡°)");
                soundPool.play(incorrect_sound, 1, 1, 0, 0, 1);
                if (chosen == 2) {
                    lives--;
                    switch (lives) {
                        case 2:
                            ((ImageView)findViewById(R.id.heartOne)).setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            ((ImageView)findViewById(R.id.heartTwo)).setVisibility(View.INVISIBLE);
                            break;
                        default:
                            ((ImageView)findViewById(R.id.heartThree)).setVisibility(View.INVISIBLE);
                            alert.setTitle("Nice Try");
                            flag = false;
                            break;
                    }
                }
            }
            ((TextView)findViewById(R.id.scoreText)).setText("Score: " + controller.getScore());
            ((ImageButton)findViewById(R.id.one)).setBackground(getDrawable(R.drawable.topicbutton));
            ((ImageButton)findViewById(R.id.two)).setBackground(getDrawable(R.drawable.topicbutton));
            ((ImageButton)findViewById(R.id.three)).setBackground(getDrawable(R.drawable.topicbutton));
            ((ImageButton)findViewById(R.id.four)).setBackground(getDrawable(R.drawable.topicbutton));
            answer = "hello Dvir";
            shuffle(flag);
        }
        else
            toast.setText("Select an answer");
        toast.cancel();
        toast.show();
    }

    @Override
    protected void onStop () {
        super.onStop();
        toast.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    @Override
    public void home() {
        Intent intentR = new Intent();
        intentR.putExtra("currentPos", mediaPlayer.getCurrentPosition());
        intentR.putExtra("scores", scores);
        mediaPlayer.pause();
        setResult(Activity.RESULT_OK, intentR);
        finish();
    }

    @Override
    public void onBackPressed() {
        home();
    }

}
package com.example.trivia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
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
    MediaPlayer playSoundCorrect;
    MediaPlayer playSoundIncorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        question = findViewById(R.id.questioned);
        controller = new Controller();
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        answer = "hello Dvir";

        playSoundCorrect = MediaPlayer.create(this, R.raw.correct_sound);
        playSoundIncorrect = MediaPlayer.create(this, R.raw.incorrect_sound);

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
        Intent intent = getIntent();
        playerName = intent.getStringExtra("player");
        ((TextView)findViewById(R.id.name)).setText(playerName);
        builder.setCancelable(false);
        builder.setTitle("Well Played");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert = builder.create();

        shuffle();

        start = System.currentTimeMillis();
    }

    public void shuffle() {
        Question current = controller.createQuestion();
        if (current != null) {
            question.setText(current.getQuestion());
            realAnswer = current.getAnswer();
            String[] replies = current.getRe();
            shuffleArray(replies);
            ((TextView) findViewById(R.id.oneAnswer)).setText(replies[0]);
            ((TextView) findViewById(R.id.twoAnswer)).setText(replies[1]);
            ((TextView) findViewById(R.id.threeAnswer)).setText(replies[2]);
            ((TextView) findViewById(R.id.fourAnswer)).setText(replies[3]);
            ((TextView) findViewById(R.id.textView3)).setText("Question #" + controller.getQuestionNum());
        }
        else {
            long end = System.currentTimeMillis();
            alert.setMessage(playerName + ", you answered all the questions.\nYour time is: " + (Math.round((((end - start) / 1000.0) * 100.0))/100.0) + " seconds.");
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
        if (!answer.equals("hello Dvir")) {
            if (answer.equals(realAnswer)) {
                controller.addScore(10);
                toast.setText("Correct! ( ͡ʘ ͜ʖ ͡ʘ)");
                if (soundOn)
                    playSoundCorrect.start();
            }
            else {
                toast.setText("Incorrect ( ͡° ʖ̯ ͡°)");
                if (soundOn)
                    playSoundIncorrect.start();
            }
            ((TextView)findViewById(R.id.scoreText)).setText("Score: " + controller.getScore());
            ((ImageButton)findViewById(R.id.one)).setBackground(getDrawable(R.drawable.topicbutton));
            ((ImageButton)findViewById(R.id.two)).setBackground(getDrawable(R.drawable.topicbutton));
            ((ImageButton)findViewById(R.id.three)).setBackground(getDrawable(R.drawable.topicbutton));
            ((ImageButton)findViewById(R.id.four)).setBackground(getDrawable(R.drawable.topicbutton));
            answer = "hello Dvir";
            shuffle();
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
}
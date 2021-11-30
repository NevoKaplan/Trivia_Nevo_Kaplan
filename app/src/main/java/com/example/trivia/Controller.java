package com.example.trivia;

import android.view.Display;

public class Controller {
    private Model model;

    public Controller() {
        model = new Model();
    }

    public Question createQuestion() { return model.createQuestion(); }

    public void addScore(int score) { model.addScore(score); }

    public int getScore() { return model.getScore(); }

    public int getQuestionNum() {
        return model.getQuestionNum();
    }
}

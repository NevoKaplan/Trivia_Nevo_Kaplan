package com.example.trivia;

import android.view.Display;

public class Controller {
    private Model model;

    public Controller() {
        this.model = new Model();
    }

    public Question createQuestion() { return this.model.createQuestion(); }

    public void addScore(int score) { this.model.addScore(score); }

    public int getScore() { return this.model.getScore(); }

    public int getQuestionNum() {
        return this.model.getQuestionNum();
    }

    public int getQuestionLen() {
        return this.model.getQuestionLen();
    }
}

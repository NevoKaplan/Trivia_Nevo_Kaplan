package com.example.trivia;

public class Question {
    private String question;
    private String answer;
    private String w1;
    private String w2;
    private String w3;

    public Question(String[] question) {
        this.question = question[0];
        this.answer = question[1];
        this.w1 = question[2];
        this.w2 = question[3];
        this.w3 = question[4];
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getW1() {
        return this.w1;
    }

    public String getW2() {
        return this.w2;
    }

    public String getW3() {
        return this.w3;
    }

    public String[] getRe() {
        return new String[]{this.answer, this.w1, this.w2, this.w3};
    }
}

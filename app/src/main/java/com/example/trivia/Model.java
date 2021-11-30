package com.example.trivia;

import java.util.*;

public class Model {
    static Random rnd = new Random();
    private String[][] general_knowledge;
    private int len;
    private int score;

    public Model() {
        this.general_knowledge = new String[][]{{"According to the BBPA, what is the most common pub name in the UK?", "Red Lion", "Royal Oak", "White hart", "king's head"}, {"What is the world's most expensive spice by weight?", "Saffron", "Cinnamon", "Cardamom", "Vanilla"}, {"Which company did Valve cooperate with in the creation of the Vive?", "HTC", "Oculus", "Google", "Razer"}, {"What does a funambulist walk on?", "A Tight Rope", "Broken Glass", "Balls", "The Moon"}, {"How would one say goodbye in Spanish?", "Adiós", "Hola", "Au Revoir", "Salir"}, {"What type of animal was Harambe?", "Gorilla", "Tiger", "Panda", "Crocodile"}, {"What is the name of the very first video uploaded to YouTube?", "Me at the zoo", "tribute", "carrie rides a truck", "Her new puppy from great grandpa vern."}, {"Which of the following chemicals are found in eggplant seeds?", "Nicotine", "Mescaline", "Cyanide", "Psilocybin"}, {"What is on display in the Madame Tussauds museum in London?", "Wax sculptures", "Designer clothing", "Unreleased film reels", "Vintage cars"}, {"Where does water from Poland Spring water bottles come from?", "Maine, United States", "Hesse, Germany", "Masovia, Poland", "Bavaria, Poland"}, {"According to the 2014-2015 Australian Bureau of Statistics, what percentage of Australians were born overseas?", "28%", "13%", "20%", "7%"}, {"What was Bank of America originally established as?", "Bank of Italy", "Bank of Long Island", "Bank of Pennsylvania", "Bank of Charlotte"}, {"Which product did Nokia originally sell?", "Paper", "Phones", "Computers", "Processors"}, {"When did the website Facebook launch?", "2004", "2005", "2003", "2006"}};
        this.len = this.general_knowledge.length;
        this.score = 0;
    }

    public Question createQuestion() {
        if (len > 0) {
            int location = rnd.nextInt(len);
            Question current = new Question(this.general_knowledge[location]);
            pushToEnd(location);
            this.len--;
            return current;
        }
        else
            return null;
    }

    public void pushToEnd(int location) {
        String[] temp = this.general_knowledge[this.len - 1];
        this.general_knowledge[this.len - 1] = null;
        this.general_knowledge[location] = temp;
    }

    public void addScore(int score) { this.score += score; }

    public int getScore() { return this.score; }

    public int getQuestionNum() {
       return this.general_knowledge.length - this.len;
    }
}

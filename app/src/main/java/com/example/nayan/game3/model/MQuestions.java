package com.example.nayan.game3.model;

import java.util.ArrayList;

/**
 * Created by NAYAN on 10/4/2016.
 */

public class MQuestions {
    private String mQuestion;
    private ArrayList<MItem> options;

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public ArrayList<MItem> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<MItem> options) {
        this.options = options;
    }
}

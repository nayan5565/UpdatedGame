package com.example.nayan.game3.logic;

import android.content.Context;

import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.model.MLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/12/2016.
 */
public class Logic {


    static Logic logic;
    ArrayList<MLevel> levels;
    Context context;
    LevelAdapter levelAdapter;
    private Logic() {

    }

    public static Logic getInstance(Context context1) {

        if (logic == null) {
            logic = new Logic();
        }
        logic.context = context1;

        return logic;

    }

    public void callData(ArrayList<MLevel> levels, LevelAdapter adapter) {
        this.levels = levels;
        this.levelAdapter = adapter;



    }

}

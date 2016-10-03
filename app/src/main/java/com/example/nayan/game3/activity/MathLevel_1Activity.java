package com.example.nayan.game3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.MathLevelAdapter;
import com.example.nayan.game3.model.MItem;
import com.example.nayan.game3.model.MQuestions;

import java.util.ArrayList;

public class MathLevel_1Activity extends AppCompatActivity {
    public ArrayList<MQuestions> mQuestionses;
    RecyclerView recyclerView;
    MathLevelAdapter matchLevelAdapter;
    TextView txtItem;
    static MathLevel_1Activity instance;
    int index;

    public static MathLevel_1Activity getInstance() {

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_level_1);
        instance = this;
        init();
        generate();
        preParedisplay();
    }

    public void init() {

        mQuestionses = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        txtItem = (TextView) findViewById(R.id.tct);

        matchLevelAdapter = new MathLevelAdapter(this);
        recyclerView.setAdapter(matchLevelAdapter);

    }

    public void preParedisplay() {
        if (index >= mQuestionses.size()) {
            Toast.makeText(this,"level completed",Toast.LENGTH_SHORT).show();
            index = 0;
            return;
        } else {
            txtItem.setText(mQuestionses.get(index).getmQuestion());
            matchLevelAdapter.setData(mQuestionses.get(index).getOptions());
            index++;
        }


    }

    public void generate() {
        ArrayList<MItem> mItems = new ArrayList<>();
        MQuestions questions = new MQuestions();
        questions.setmQuestion("2+2");
        MItem mItem = new MItem();
        mItem.setNumber(2);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(4);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(6);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(4);
        mItem.setTag(1);

        mItems.add(mItem);

        questions.setOptions(mItems);

        mQuestionses.add(questions);

        mItems = new ArrayList<>();
        questions = new MQuestions();
        questions.setmQuestion("5+5=?");

        mItem = new MItem();
        mItem.setNumber(10);
        mItem.setTag(1);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(4);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(6);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(8);
        mItem.setTag(0);

        mItems.add(mItem);

        questions.setOptions(mItems);

        mQuestionses.add(questions);

        mItems = new ArrayList<>();
        questions = new MQuestions();
        questions.setmQuestion("2+5");
        mItem = new MItem();
        mItem.setNumber(2);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(7);
        mItem.setTag(1);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(6);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(8);
        mItem.setTag(0);

        mItems.add(mItem);

        questions.setOptions(mItems);


        mQuestionses.add(questions);

        mItems = new ArrayList<>();
        questions = new MQuestions();
        questions.setmQuestion("7+2");
        mItem = new MItem();
        mItem.setNumber(2);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(4);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(9);
        mItem.setTag(1);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(8);
        mItem.setTag(0);

        mItems.add(mItem);

        questions.setOptions(mItems);


        mQuestionses.add(questions);

        mItems = new ArrayList<>();
        questions = new MQuestions();
        questions.setmQuestion("8+2");
        mItem = new MItem();
        mItem.setNumber(2);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(4);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(6);
        mItem.setTag(0);

        mItems.add(mItem);

        mItem = new MItem();
        mItem.setNumber(10);
        mItem.setTag(1);

        mItems.add(mItem);

        questions.setOptions(mItems);

        mQuestionses.add(questions);


    }
}

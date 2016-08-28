package com.example.nayan.game3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String IMAGE_URL = "http://www.radhooni.com/content/match_game/v1/images/";
    static int value;
    static TextView textView;
    static LevelAdapter levelAdapter;
    static ArrayList<MLevel> levels;
    static MLevel level = new MLevel();
    MyDatabase database;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_activity);


        value = getIntent().getIntExtra("type", 0);
        Log.e("log", "is" + value);

        init();

        if (value == 2) {
            textView.setText("Normal");
//            level.getImage();
            //levels=database.getAllData();
            levels = Utils.easy;
        } else if (value == 3) {

            textView.setText("Medium");
//            level.getImage();
            levels = Utils.medium;
        } else if (value == 4) {

            textView.setText("Hard");
//            level.getImage();
            levels = Utils.hard;

        }
        levelAdapter.setData(levels);


    }

    public void init() {
        database=new MyDatabase(this);
        textView = (TextView) findViewById(R.id.tct);



        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (value == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 3) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 4) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        levelAdapter = new LevelAdapter(this);
        recyclerView.setAdapter(levelAdapter);


    }

    @Override
    public void onClick(View v) {
       /* if (v.getPresentId() == R.id.imgseting) {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("GameActivity Information");
            dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
            dialog.setContentView(R.layout.dialog_setting);
            Button btnWin = (Button) dialog.findViewById(R.id.btnStatics);
            btnWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Logic.getInstance(LevelActivity.this).showHistory();
                }
            });

            dialog.show();

        } */

    }


}

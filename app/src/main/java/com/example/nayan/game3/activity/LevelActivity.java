package com.example.nayan.game3.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.logic.Logic;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.MyGame;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";
    public static final String IMAGE_URL = "http://www.radhooni.com/content/match_game/v1/images/";
    static int value;
    static TextView textView;
    static LevelAdapter levelAdapter;
    static ArrayList<MLevel> levels;
    static MLevel level = new MLevel();
    RecyclerView recyclerView;
    SharedPreferences preferences;
    ImageView img, imgSetting;
    String image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = getIntent().getIntExtra("type", 0);
        Log.e("log", "is" + value);

        init();
        if (value == 2) {
            textView.setText("Normal");

            levels = MyGame.easy;
        } else if (value == 3) {

            textView.setText("Medium");
            levels = MyGame.medium;
        } else if (value == 4) {

            textView.setText("Hard");
            levels = MyGame.hard;

        }
        levelAdapter.setData(levels);


    }

    public void init() {
        textView = (TextView) findViewById(R.id.tct);
        image = getPREF(KEY_IMAGE);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);


        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
        if (image.equals(1 + "")) {
            Logic.getInstance(this).isSoundPlay = true;
            img.setImageResource(R.drawable.on);
        } else if (image.equals(0 + "")) {
            Logic.getInstance(this).isSoundPlay = false;
            img.setImageResource(R.drawable.off);
        }


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
            dialog.setTitle("Game Information");
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
        if (v.getId() == R.id.img) {
            if (Logic.getInstance(this).isSoundPlay == false) {
                Logic.getInstance(this).isSoundPlay = true;
                img.setImageResource(R.drawable.on);
                savePref(KEY_IMAGE, 1 + "");
            } else {
                Logic.getInstance(this).isSoundPlay = false;
                img.setImageResource(R.drawable.off);
                savePref(KEY_IMAGE, 0 + "");
            }

        }
    }

    private void savePref(String key, String value) {
        preferences = this.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPREF(String key) {
        preferences = this.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }
}

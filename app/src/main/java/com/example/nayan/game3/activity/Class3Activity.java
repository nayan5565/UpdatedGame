package com.example.nayan.game3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.Class3Adapter;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.logic.NLogic;
import com.example.nayan.game3.model.MContents;

import java.util.ArrayList;

/**
 * Created by NAYAN on 10/1/2016.
 */

public class Class3Activity extends AppCompatActivity {
    public static MContents mContents;
    ArrayList<MContents> imageArrayList;
    ImageView imgSetting;
    RecyclerView recyclerView;
    Class3Adapter class3Adapter;
    Toolbar toolbar;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class3_activity);
        init();
        getLocalData();
    }

    public void getLocalData() {
        ArrayList<MContents> realTxtSen = new ArrayList<>();
        realTxtSen = database.getContentsData();
        imageArrayList =generatesTxtSen(realTxtSen);
        class3Adapter.setData(imageArrayList);
    }

    public ArrayList<MContents> generatesTxtSen(ArrayList<MContents> realTxtSen) {
        int count = 20;
        ArrayList<MContents> tempTxtSen = new ArrayList<>();
        for (MContents mContents : realTxtSen) {
            tempTxtSen.add(mContents);
            count++;
            MContents contents = new MContents();
            contents.setSen(mContents.getSen());
            contents.setMid(mContents.getMid());
            contents.setPresentType(count);
            tempTxtSen.add(contents);
        }
        return tempTxtSen;
    }

    public void init() {
        database = new MyDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        imgSetting = (ImageView) findViewById(R.id.imgseting);
//        imgSetting.setOnClickListener(this);
        NLogic.getInstance(this).setLevel(mContents);
        imageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        if (SubLevelActivity.value == Utils.EASY) {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        }
//        if (SubLevelActivity.value == Utils.MEDIUM) {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        }
//        if (SubLevelActivity.value == Utils.HARD) {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        }

        class3Adapter = new Class3Adapter(this);
        recyclerView.setAdapter(class3Adapter);
    }
}

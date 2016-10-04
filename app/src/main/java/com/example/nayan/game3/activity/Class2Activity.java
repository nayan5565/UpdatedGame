package com.example.nayan.game3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.Class1AdapterOfBangla;
import com.example.nayan.game3.adapter.Class2AdapterOfBangla;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.logic.NLogic;
import com.example.nayan.game3.model.MContents;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NAYAN on 9/30/2016.
 */

public class Class2Activity extends AppCompatActivity {
    public static MContents mContents;
    private ArrayList<MContents> imageArrayList;
    private ImageView imgSetting;
    private RecyclerView recyclerView;
    private Class2AdapterOfBangla class1AdapterOfBangla;
    private Toolbar toolbar;
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_2);
        init();
        prepareDisplay();
        getLocalData();
    }

    private void getLocalData() {

        ArrayList<MContents> realAssets = new ArrayList<>();
        MyDatabase db = new MyDatabase(this);
        realAssets = db.getContentsData();
        imageArrayList = generateAssets(realAssets);
        Collections.shuffle(imageArrayList);
        class1AdapterOfBangla.setData(imageArrayList);
    }

    private ArrayList<MContents> generateAssets(ArrayList<MContents> realAssets) {
        int count = 20;
        ArrayList<MContents> tempAsset = new ArrayList<>();
        for (MContents mContents : realAssets) {

            tempAsset.add(mContents);
            count++;
            MContents asset1 = new MContents();
//            asset1.setPresentId(count);
            asset1.setPresentType(count);
//            asset1.setHints(asset.getHints());
//            asset1.setImageOpen(asset.getImageOpen());
            asset1.setTxt(mContents.getTxt());
            asset1.setMid(mContents.getMid());
//            asset1.setLevelId(asset.getLevelId());
//            asset1.setSounds(asset.getSounds());
            tempAsset.add(asset1);
        }
        return tempAsset;
    }

    private void init() {
        database = new MyDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
//        imgSetting.setOnClickListener(this);
        NLogic.getInstance(this).setLevel(mContents);
        imageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        class1AdapterOfBangla = new Class2AdapterOfBangla(this);
        recyclerView.setAdapter(class1AdapterOfBangla);
    }

    private void prepareDisplay() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(class1AdapterOfBangla);
    }
}

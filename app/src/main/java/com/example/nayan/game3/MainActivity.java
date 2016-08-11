package com.example.nayan.game3;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nayan.game3.adapter.MyRecyclerViewAdapter;
import com.example.nayan.game3.model.MData;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MData> arrayList;
    MData mData;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    ImageView img,imgSetting;

    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = getIntent().getIntExtra("type", 0);
        init();
        if (value == 2) {
            normalGame();
        } else if (value == 3) {
            mediumGame();
        } else if (value == 4) {
            hardGame();
        }


    }

    public void init() {
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);

        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);


        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (value == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 3) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (value == 4) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);


    }

    public void hardGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(7);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(8);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(9);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(10);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(11);
        mData.setType(6);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(12);
        mData.setType(6);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        Collections.shuffle(arrayList);

        adapter.setData(arrayList);
    }

    public void normalGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);


        Collections.shuffle(arrayList);

        adapter.setData(arrayList);
    }

    public void mediumGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/backend/uploads/menu/thumbnail/chingri_balachao.jpg");
        arrayList.add(mData);


        Collections.shuffle(arrayList);

        adapter.setData(arrayList);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgseting) {
            final Dialog dialog=new Dialog(this);
            dialog.setTitle("Game Information");
            dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
            dialog.setContentView(R.layout.dialog_setting);
            Button btnPoint=(Button)dialog.findViewById(R.id.btnBestPoint);
            btnPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Logic.getInstance(MainActivity.this).bestPoint();
                }
            });
            dialog.show();

        } else if (v.getId() == R.id.img) {
            if (Logic.getInstance(this).isSoundPlay==false){
                Logic.getInstance(this).isSoundPlay = true;
                img.setImageResource(R.drawable.on);
            }
            else {
                Logic.getInstance(this).isSoundPlay =false;
                img.setImageResource(R.drawable.off);
            }

        }
    }
}

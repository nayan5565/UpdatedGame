package com.example.nayan.game3.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.logic.NLogic;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NAYAN on 8/20/2016.
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";
    public static int levelId;
    public static MLevel mLevel;
    ArrayList<MAsset> imageArrayList;
    ImageView img, imgSetting;
    RecyclerView recyclerView;
    GameAdapter gameAdapter;
    SharedPreferences preferences;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);


        init();
        levelId = mLevel.getId();
        NLogic.getInstance(this).setLevel(mLevel);

        imageArrayList = generateAssets();

        Collections.shuffle(imageArrayList);
        gameAdapter.setData(imageArrayList);


    }

    public ArrayList<MAsset> generateAssets() {
        int count = 20;
        MyDatabase db = new MyDatabase(this);
        ArrayList<MAsset> tempAsset = new ArrayList<>();
        for (MAsset asset : db.getData(levelId)) {
            NLogic.count = 0;

            NLogic.previousId = tempAsset.size() + 1;

            tempAsset.add(asset);
            count++;
            MAsset asset1 = new MAsset();
            asset1.setPresentId(count);
            asset1.setPresentType(asset.getPresentType());
            asset1.setHints(asset.getHints());
            asset1.setImageOpen(asset.getImageOpen());
            asset1.setImages(asset.getImages());
            asset1.setLevelId(asset.getLevelId());
            asset1.setSounds(asset.getSounds());
            tempAsset.add(asset1);
        }
        return tempAsset;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    public void init() {

//        image = getPREF(KEY_IMAGE);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);

//        img = (ImageView) findViewById(R.id.img);
        // img.setOnClickListener(this);
       /* if (image.equals(1 + "")) {
            NLogic.getInstance(this).isSoundPlay = true;
            img.setImageResource(R.drawable.on);
        } else if (image.equals(0 + "")) {
            NLogic.getInstance(this).isSoundPlay = false;
            img.setImageResource(R.drawable.off);
        }*/

        imageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (LevelActivity.value == Utils.EASY) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        if (LevelActivity.value == Utils.MEDIUM) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        if (LevelActivity.value == Utils.HARD) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        gameAdapter = new GameAdapter(this);
        recyclerView.setAdapter(gameAdapter);
    }

   /* public void normalGame() {
        mData = new MData();
        mData.setPresentId(3);
        mData.setPresentType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/eight.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setPresentId(4);
        mData.setPresentType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/eight.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setPresentId(6);
        mData.setPresentType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/fifteen.png");
        arrayList.add(mData);


        Collections.shuffle(arrayList);

        levelAdapter.setData(arrayList);
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgseting) {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("GameActivity Information");
            dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
            dialog.setContentView(R.layout.dialog_setting);
            Button btnWin = (Button) dialog.findViewById(R.id.btnStatics);
            btnWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    NLogic.getInstance(GameActivity.this).showHistory();
                }
            });

            dialog.show();

        }/* else if (v.getId() == R.id.img) {
            if (NLogic.getInstance(this).isSoundPlay == false) {
                NLogic.getInstance(this).isSoundPlay = true;
                img.setImageResource(R.drawable.on);
                savePref(KEY_IMAGE, 1 + "");
            } else {
                NLogic.getInstance(this).isSoundPlay = false;
                img.setImageResource(R.drawable.off);
                savePref(KEY_IMAGE, 0 + "");
            }

        }*/
    }

    /*private void savePref(String key, String value) {
        preferences = this.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPREF(String key) {
        preferences = this.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }*/
}

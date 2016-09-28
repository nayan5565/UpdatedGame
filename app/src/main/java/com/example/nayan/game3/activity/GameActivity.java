package com.example.nayan.game3.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.VungleAdManager;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.logic.NLogic;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MContents;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NAYAN on 8/20/2016.
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static int levelId;
    public static MLevel mLevel;
    public static MContents mContents;
    ArrayList<MContents> imageArrayList;
    ImageView imgSetting;
    RecyclerView recyclerView;
    GameAdapter gameAdapter;
    Toolbar toolbar;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);
//        VungleAdManager.getInstance(this).play();

        init();
       getLocalData();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void getLocalData() {
        imageArrayList=database.getContentsData();
        gameAdapter.setData(imageArrayList);
//        ArrayList<MContents> realAssets = new ArrayList<>();
//        MyDatabase db = new MyDatabase(this);
//        realAssets = db.getContentsData(mContents.getLid());
//        imageArrayList = generateAssets(realAssets);
//        Collections.shuffle(imageArrayList);
//       gameAdapter.setData(imageArrayList);
    }

    public ArrayList<MContents> generateAssets(ArrayList<MContents> realAssets) {
        int count = 20;
        ArrayList<MContents> tempAsset = new ArrayList<>();
        for (MContents mContents : realAssets) {
            NLogic.count = 0;

            NLogic.previousId = tempAsset.size() + 1;

            tempAsset.add(mContents);
            count++;
            MAsset asset1 = new MAsset();
            asset1.setPresentId(count);
//            asset1.setPresentType(asset.getPresentType());
//            asset1.setHints(asset.getHints());
//            asset1.setImageOpen(asset.getImageOpen());
//            asset1.setImages(asset.getImages());
//            asset1.setLevelId(asset.getLevelId());
//            asset1.setSounds(asset.getSounds());
            tempAsset.add(mContents);
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
        database=new MyDatabase(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);
        NLogic.getInstance(this).setLevel(mContents);
        imageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        if (LevelActivity.value == Utils.EASY) {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        }
//        if (LevelActivity.value == Utils.MEDIUM) {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        }
//        if (LevelActivity.value == Utils.HARD) {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        }

        gameAdapter = new GameAdapter(this);
        recyclerView.setAdapter(gameAdapter);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgseting) {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("Game Information");
            dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
            dialog.setContentView(R.layout.dialog_setting);
            Button btnWin = (Button) dialog.findViewById(R.id.btnBack);
            btnWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    NLogic.getInstance(GameActivity.this).showHistory();
                }
            });

            dialog.show();
        }
    }
}

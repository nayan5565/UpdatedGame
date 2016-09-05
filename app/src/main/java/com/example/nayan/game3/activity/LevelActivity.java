package com.example.nayan.game3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.nayan.game3.DownLoadAsyncTask;
import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String IMAGE_URL = "http://www.radhooni.com/content/match_game/v1/images/";
    public static int value;
    public static TextView textView;
    static LevelAdapter levelAdapter;
    static ArrayList<MLevel> levels;
    static MLevel level = new MLevel();
    MyDatabase database;
    RecyclerView recyclerView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_activity);


        value = getIntent().getIntExtra("type", 0);
        Log.e("log", "is" + value);

        init();
        prepareDisplay();
        getLocalData();
        downloadAssets();

//        if (value == Utils.EASY) {
//            textView.setText("Normal");
//            textView.setTextColor(0xffff00ff);
//            levels = Utils.easy;
//        } else if (value == Utils.MEDIUM) {
//
//            textView.setText("Medium");
//            textView.setTextColor(0xffff00ff);
//            levels = Utils.medium;
//        } else if (value == Utils.HARD) {
//
//            textView.setText("Hard");
//            textView.setTextColor(0xffff00ff);
//            levels = Utils.hard;
//
//        }
//        levelAdapter.setData(levels);


    }

    private void downloadAssets(){
        ArrayList<String>uniqueImage=new ArrayList<>();
        for (int i=0;i<levels.size();i++){
            ArrayList<MAsset>assetArrayList=database.getData(levels.get(i).getId());
            for (int j =0;j<assetArrayList.size();j++){
                if (!uniqueImage.contains(assetArrayList.get(j).getImages())){
                    uniqueImage.add(assetArrayList.get(j).getImages());
                }
            }
        }
        for (int i=0;i<uniqueImage.size();i++){
            new DownLoadAsyncTask(this,OpenActivity.getPath(uniqueImage.get(i))).execute(IMAGE_URL + uniqueImage.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        id = R.id.action_settings;
        //id unused
        return super.onOptionsItemSelected(item);
    }

    public void getLocalData() {
        levels = database.getAllData(value);
        levelAdapter.setData(levels);
    }

    @Override
    protected void onRestart() {
        getLocalData();
        super.onRestart();
    }

    public void init() {
        database = new MyDatabase(this);
        textView = (TextView) findViewById(R.id.tct);


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (value == Utils.EASY) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == Utils.MEDIUM) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == Utils.HARD) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        toolbar=(Toolbar)findViewById(R.id.toolbar);

        levelAdapter = new LevelAdapter(this);
        recyclerView.setAdapter(levelAdapter);


    }
    public void prepareDisplay() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

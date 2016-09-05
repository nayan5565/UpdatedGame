package com.example.nayan.game3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NAYAN on 8/4/2016.
 */
public class OpenActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";
    Button btnNormal, btnHard, btnMedium;
    //    ImageView btnPlay;
    Toolbar toolbar;
    MLevel level;
    MyDatabase database;
    DrawerLayout drawerLayout;
    SharedPreferences preferences;
    ImageView img, imgSetting;
    String image;
    Animation animation;
//    TextView title;

    //   NavigationDrawerFragment drawerFragment;

    public static String getPath(String fileName) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Match Game";
        File file = new File(dir);
        if (!file.isDirectory()) {
            file.mkdirs();

        }
        return dir + File.separator + fileName;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_activity);


        init();
        getOnlineData();

//        getLocalData();

        prepareDisplay();

    }

    public void init() {

        database = new MyDatabase(this);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        title = (TextView) findViewById(R.id.txtTitles);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        btnPlay = (ImageView) findViewById(R.id.btnPlay);
//        Utils.zoom(btnPlay, false);

//        btnPlay.setOnClickListener(this);
        btnNormal = (Button) findViewById(R.id.btnNormal);
//        Utils.zoom(btnNormal, false);
        btnNormal.setOnClickListener(this);
        btnMedium = (Button) findViewById(R.id.btnMedium);
//        Utils.zoom(btnMedium, false);
        btnMedium.setOnClickListener(this);
        btnHard = (Button) findViewById(R.id.btnHard);
//        Utils.zoom(btnHard, false);
        btnHard.setOnClickListener(this);
        image = getPREF(KEY_IMAGE);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);


        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
        if (image.equals(1 + "")) {
            Utils.isSoundPlay = true;
            img.setImageResource(R.drawable.on);
        } else if (image.equals(0 + "")) {
            Utils.isSoundPlay = false;
            img.setImageResource(R.drawable.off);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
////        int id = item.getItemId();
////        if (id == android.R.id.home) {
////            btnHard.setVisibility(View.GONE);
////            btnNormal.setVisibility(View.GONE);
////            btnMedium.setVisibility(View.GONE);
////            btnPlay.setVisibility(View.VISIBLE);
////        }
////
////        if (id == R.id.action_settings) {
////            return true;
////        }
//        return super.onOptionsItemSelected(item);
//    }

    public void getOnlineData() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/games.json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Utils.easy = new ArrayList<MLevel>();
                        Utils.medium = new ArrayList<MLevel>();
                        Utils.hard = new ArrayList<MLevel>();
                        Utils.assetArrayList = new ArrayList<>();
                        try {
                            JSONObject puzzle = response.getJSONObject("puzzle");


                            JSONArray easy = puzzle.getJSONArray("easy");
                            for (int i = 0; i < easy.length(); i++) {
                                JSONObject jsonObject = easy.getJSONObject(i);

                                level = new MLevel();
                                level.setId(jsonObject.getInt("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));
                                level.setDifficulty(Utils.EASY);

                                JSONArray asset1 = jsonObject.getJSONArray("asset");

                                MAsset mAsset;
                                int count = 0;
                                for (int j = 0; j < asset1.length(); j++) {
                                    JSONObject image = asset1.getJSONObject(j);

                                    count++;
                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setLevelId(level.getId());
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    Log.e("Loge", "present id id ::" + mAsset.getPresentId());
                                    Utils.assetArrayList.add(mAsset);


                                }
//                                level.setAsset(Utils.assetArrayList);

                                Utils.easy.add(level);


                            }


                            JSONArray medium = puzzle.getJSONArray("medium");

                            for (int i = 0; i < medium.length(); i++) {
                                JSONObject jsonObject = medium.getJSONObject(i);

                                level = new MLevel();
                                level.setId(jsonObject.getInt("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));
                                level.setDifficulty(Utils.MEDIUM);

                                JSONArray asset1 = jsonObject.getJSONArray("asset");

                                MAsset mAsset;
                                int count = 0;
                                for (int j = 0; j < asset1.length(); j++) {
                                    JSONObject image = asset1.getJSONObject(j);

                                    count++;
                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setLevelId(level.getId());
                                    mAsset.setPresentId(count);

                                    Utils.assetArrayList.add(mAsset);
                                }
//                                level.setAsset(Utils.assetArrayList);
                                Utils.medium.add(level);


                            }


                            JSONArray hard = puzzle.getJSONArray("hard");

                            for (int i = 0; i < hard.length(); i++) {
                                JSONObject jsonObject = hard.getJSONObject(i);

                                level = new MLevel();
                                level.setId(jsonObject.getInt("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));
                                level.setDifficulty(Utils.HARD);

                                JSONArray asset1 = jsonObject.getJSONArray("asset");
                                ArrayList<MAsset> asset = new ArrayList<MAsset>();
                                MAsset mAsset;
                                int count = 0;
                                for (int j = 0; j < asset1.length(); j++) {
                                    JSONObject image = asset1.getJSONObject(j);

                                    count++;
                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setLevelId(level.getId());
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    count++;
                                    mAsset.setPresentId(count);

                                    Utils.assetArrayList.add(mAsset);
                                }
//                                level.setAsset(Utils.assetArrayList);
                                Utils.hard.add(level);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        saveLevelToDb();
                        saveAssetToDb();

                    }
                }
        );
    }

    private void saveLevelToDb() {
        for (MLevel data : Utils.easy) {
            database.addLevelFromJson(data);
        }
        for (MLevel data : Utils.medium) {
            database.addLevelFromJson(data);
        }
        for (MLevel data : Utils.hard) {
            database.addLevelFromJson(data);
        }

    }

//    private void getLocalData() {
//        Utils.easy = database.getAllData(Utils.EASY);
//        Log.e("log", "easy : " + Utils.easy.size());
//        Utils.medium = database.getAllData(Utils.MEDIUM);
//        Log.e("log", "medium : " + Utils.medium.size());
//        Utils.hard = database.getAllData(Utils.HARD);
//        Log.e("log", "hard : " + Utils.hard.size());
//    }

    private void saveAssetToDb() {
        for (MAsset data2 : Utils.assetArrayList) {
            database.addAssetFromJson(data2);
        }
    }

    public void prepareDisplay() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        //drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavDrewer);
//        drawerFragment.setUp(R.id.fragNavDrewer, drawerLayout, toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img) {
            if (Utils.isSoundPlay == false) {
                Utils.isSoundPlay = true;
                img.setImageResource(R.drawable.on);
                savePref(KEY_IMAGE, 1 + "");
            } else {
                Utils.isSoundPlay = false;
                img.setImageResource(R.drawable.off);
                savePref(KEY_IMAGE, 0 + "");
            }

//        } else if (v.getId() == R.id.btnPlay) {
//            Utils.getSound(OpenActivity.this, R.raw.click);
//            btnPlay.setVisibility(View.GONE);
//            btnHard.setVisibility(View.VISIBLE);
//            btnNormal.setVisibility(View.VISIBLE);
//            btnMedium.setVisibility(View.VISIBLE);


        } else if (v.getId() == R.id.btnNormal) {
//            LevelActivity.textView.setText("Easy");
            Utils.getSound(OpenActivity.this, R.raw.click);

            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", Utils.EASY);
            startActivity(intent);

        } else if (v.getId() == R.id.btnHard) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", Utils.HARD);
            startActivity(intent);
        } else if (v.getId() == R.id.btnMedium) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", Utils.MEDIUM);
            startActivity(intent);
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

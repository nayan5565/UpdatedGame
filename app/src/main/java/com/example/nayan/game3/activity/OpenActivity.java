package com.example.nayan.game3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NAYAN on 8/4/2016.
 */
public class OpenActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";
    Button btnNormal, btnHard, btnMedium;
    ImageView btnPlay;
    Toolbar toolbar;
    MLevel level;
    MyDatabase database;
    DrawerLayout drawerLayout;
    SharedPreferences preferences;
    ImageView img, imgSetting;
    String image;
    Animation animation;

    //   NavigationDrawerFragment drawerFragment;

    ArrayList<MLevel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_activity);


        init();
        prepareDisplay();
        getOnlineData();

    }

    public void init() {
        list = new ArrayList<>();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnPlay = (ImageView) findViewById(R.id.btnPlay);
        Utils.zoom(btnPlay, false);

        btnPlay.setOnClickListener(this);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        Utils.zoom(btnNormal, false);
        btnNormal.setOnClickListener(this);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        Utils.zoom(btnMedium, false);
        btnMedium.setOnClickListener(this);
        btnHard = (Button) findViewById(R.id.btnHard);
        Utils.zoom(btnHard, false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            btnHard.setVisibility(View.GONE);
            btnNormal.setVisibility(View.GONE);
            btnMedium.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getOnlineData() {
        Utils.easy = new ArrayList<MLevel>();
        Utils.medium = new ArrayList<MLevel>();
        Utils.hard = new ArrayList<MLevel>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/games.json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONObject puzzle = response.getJSONObject("puzzle");


                            JSONArray easy = puzzle.getJSONArray("easy");
                            for (int i = 0; i < easy.length(); i++) {
                                JSONObject jsonObject = easy.getJSONObject(i);

                                level = new MLevel();
                                level.seteId(jsonObject.getString("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

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
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);

                                    asset.add(mAsset);

                                    count++;
                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    asset.add(mAsset);
                                }
                                level.setAsset(asset);
                               /* database.addLevelFromJson(level);
                                Utils.easy = database.getAllData();*/
                                Utils.easy.add(level);


                            }


                            JSONArray medium = puzzle.getJSONArray("medium");

                            for (int i = 0; i < medium.length(); i++) {
                                JSONObject jsonObject = medium.getJSONObject(i);

                                level = new MLevel();
                                level.seteId(jsonObject.getString("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

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
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    asset.add(mAsset);

                                    count++;
                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    asset.add(mAsset);


                                }
                                level.setAsset(asset);
                                Utils.medium.add(level);


                            }


                            JSONArray hard = puzzle.getJSONArray("hard");

                            for (int i = 0; i < hard.length(); i++) {
                                JSONObject jsonObject = hard.getJSONObject(i);

                                level = new MLevel();
                                level.seteId(jsonObject.getString("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

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
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    asset.add(mAsset);

                                    count++;
                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setPresentType(j + 1);
                                    mAsset.setPresentId(count);
                                    asset.add(mAsset);

                                }
                                level.setAsset(asset);
                                Utils.hard.add(level);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }
        );
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

        } else if (v.getId() == R.id.btnPlay) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            btnPlay.setVisibility(View.GONE);
            btnHard.setVisibility(View.VISIBLE);
            btnNormal.setVisibility(View.VISIBLE);
            btnMedium.setVisibility(View.VISIBLE);


        } else if (v.getId() == R.id.btnNormal) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Toast.makeText(this, "normal", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", 2);
            startActivity(intent);

        } else if (v.getId() == R.id.btnHard) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Toast.makeText(this, "hard", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", 4);
            startActivity(intent);
        } else if (v.getId() == R.id.btnMedium) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Toast.makeText(this, "medium", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", 3);
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

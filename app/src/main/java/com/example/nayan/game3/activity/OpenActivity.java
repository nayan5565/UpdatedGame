package com.example.nayan.game3.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.nayan.game3.AnalyticsApplication;
import com.example.nayan.game3.DialogSoundOnOff;
import com.example.nayan.game3.InMobAdManager;
import com.example.nayan.game3.R;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MContents;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;
import com.example.nayan.game3.utils.Utils;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.name;

/**
 * Created by NAYAN on 8/4/2016.
 */
public class OpenActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNormal, btnHard, btnMedium;
    Toolbar toolbar;
    MLevel mLevel;
    MyDatabase database;
    DrawerLayout drawerLayout;
    Animation animation;
    private Tracker tracker;
    String image;

    //   NavigationDrawerFragment drawerFragment;

    public static String getPath(String fileName) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Match Game";
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();

        }
        return dir + File.separator + fileName;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_activity);
//        VungleAdManager.getInstance(this).playAdOptions();
        AdView adView = (AdView) findViewById(R.id.adView);
        InMobAdManager.getInstance(this).loadAd(adView);
        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        tracker = application.getDefaultTracker();
        // [END shared_tracker]


        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        init();
        getOnlineData();
        prepareDisplay();

    }

    public void init() {

        image = DialogSoundOnOff.getPREF(this, DialogSoundOnOff.KEY_IMAGE);
        if (image.equals(1 + "")) {
            Utils.isSoundPlay = true;

        } else if (image.equals(0 + "")) {
            Utils.isSoundPlay = false;

        }
        database = new MyDatabase(this);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(this);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(this);
        btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(this);

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

        }

        if (id == R.id.action_settings) {

            DialogSoundOnOff.dialogShow(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getOnlineData() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/levels.php", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Utils.levels = new ArrayList<MLevel>();
                        try {
                            JSONObject puzzle = response.getJSONObject("puzzle");


                            JSONArray level = puzzle.getJSONArray("levels");
                            for (int i = 0; i < level.length(); i++) {
                                JSONObject jsonObject = level.getJSONObject(i);

                                mLevel = new MLevel();
//                                mLevel.setId(jsonObject.getInt("id"));
                                mLevel.setLid(jsonObject.getString("lid"));
                                mLevel.setName(jsonObject.getString("name"));
                                mLevel.setUpdate_date(jsonObject.getString("update_date"));
                                mLevel.setTotal_slevel(jsonObject.getString("total_slevel"));
                                mLevel.setDifficulty(Utils.EASY);

                                JSONArray sub = jsonObject.getJSONArray("sub");

                                MSubLevel mSubLevel;
                                int count = 0;
                                for (int j = 0; j < sub.length(); j++) {
                                    JSONObject subLevel = sub.getJSONObject(j);

                                    count++;
                                    mSubLevel = new MSubLevel();
                                    mSubLevel.setLid(subLevel.getString("lid"));
                                    mSubLevel.setName(subLevel.getString("name"));
                                    mSubLevel.setCoins_price(subLevel.getString("coins_price"));
                                    mSubLevel.setNo_of_coins("no_of_coins");
//                                    mSubLevel.setLevelId(mLevel.getId());
//                                    mSubLevel.setPresentType(j + 1);
//                                    mSubLevel.setPresentId(count);
//                                    Log.e("Loge", "present id id ::" + mSubLevel.getPresentId());
//                                    Utils.assetArrayList.add(mSubLevel);
                                }
                                Utils.levels.add(mLevel);


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
    public void getOnlineContentsData(){
        AsyncHttpClient httpClient=new AsyncHttpClient();
        httpClient.post("http://www.radhooni.com/content/match_game/v1/contents.php",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray content=response.getJSONArray("contents");
                    MContents mContents;
                    for (int i=0;i<content.length();i++){
                        JSONObject jsonObject=content.getJSONObject(i);
                        mContents=new MContents();
                        mContents.setMid(jsonObject.getString("mid"));
                        mContents.setLid(jsonObject.getString("lid"));
                        mContents.setImg(jsonObject.getString("img"));
                        mContents.setAud(jsonObject.getString("aud"));
                        mContents.setTxt(jsonObject.getString("txt"));
                        mContents.setVid(jsonObject.getString("vid"));
                        mContents.setSen(jsonObject.getString("sen"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveLevelToDb() {
        for (MLevel data : Utils.levels) {
            database.addLevelFromJson(data);
        }
    }

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
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_game_exit);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        Button btnNO = (Button) dialog.findViewById(R.id.btnNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void exitYes() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNormal) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", Utils.EASY);
            startActivity(intent);
//            finish();

        } else if (v.getId() == R.id.btnHard) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", Utils.HARD);
            startActivity(intent);
//            finish();
        } else if (v.getId() == R.id.btnMedium) {
            Utils.getSound(OpenActivity.this, R.raw.click);
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", Utils.MEDIUM);
            startActivity(intent);
//            finish();
        }
    }

}

package com.example.nayan.game3.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.nayan.game3.AnalyticsApplication;
import com.example.nayan.game3.DialogSoundOnOff;
import com.example.nayan.game3.InMobAdManager;
import com.example.nayan.game3.R;
import com.example.nayan.game3.VungleAdManager;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.vungle.publisher.VunglePub;

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
    MLevel level;
    MyDatabase database;
    DrawerLayout drawerLayout;
    Animation animation;
    private Tracker tracker;
    String image;
    final VunglePub vunglePub = VunglePub.getInstance();
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
        VungleAdManager.getInstance(this).playAdOptions();
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

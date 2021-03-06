package com.example.nayan.game3.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.nayan.game3.DialogSoundOnOff;
import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MContents;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;
import com.example.nayan.game3.utils.Utils;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NAYAN on 8/4/2016.
 */
public class LevelActivity extends AppCompatActivity implements View.OnClickListener {
    private Button result, special;
    private Toolbar toolbar;
    private MLevel mLevel;
    private static LevelAdapter levelAdapter;
    private static ArrayList<MLevel> levels;
    private MyDatabase database;
    private DrawerLayout drawerLayout;
    private Animation animation;
    private Tracker tracker;
    private RecyclerView recyclerView;
    private String image;
    private Gson gson;

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
        setContentView(R.layout.level_activity);
        result = (Button) findViewById(R.id.result);
//        VungleAdManager.getInstance(this).playAdOptions();
//        AdView adView = (AdView) findViewById(R.id.adView);
//        InMobAdManager.getInstance(this).loadAd(adView);
        // [START shared_tracker]
        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        tracker = application.getDefaultTracker();
        // [END shared_tracker]


//        tracker.setScreenName("Image~" + name);
//        tracker.send(new HitBuilders.ScreenViewBuilder().build());

//        tracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Action")
//                .setAction("Share")
//                .build());


        init();
        getOnlineData();
        getOnlineContentsData();
        prepareDisplay();
        getLocalData();

    }

    private void init() {

        image = DialogSoundOnOff.getPREF(this, DialogSoundOnOff.KEY_IMAGE);
        if (image.equals(1 + "")) {
            Utils.isSoundPlay = true;

        } else if (image.equals(0 + "")) {
            Utils.isSoundPlay = false;

        }
        database = new MyDatabase(this);


//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        result = (Button) findViewById(R.id.result);
        special = (Button) findViewById(R.id.special);
        special.setOnClickListener(this);
        result.setTextColor(0xffff00ff);
        special.setTextColor(0xffff00ff);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        levelAdapter = new LevelAdapter(this);


    }

    private void getLocalData() {
        levels = database.getLevelData();
        Log.e("list", "size : " + levels.size());
        levelAdapter.setData(levels);
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

    private void getOnlineData() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/levels.php", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        Utils.levels = new ArrayList<MLevel>();
                        Utils.mSubLevelArrayList = new ArrayList<MSubLevel>();
                        try {
                            JSONObject puzzle = response.getJSONObject("puzzle");


                            JSONArray level = puzzle.getJSONArray("level");
                            for (int i = 0; i < level.length(); i++) {
                                JSONObject jsonObject = level.getJSONObject(i);

                                mLevel = new MLevel();
//                                mLevel.setId(jsonObject.getInt("id"));
                                mLevel.setLid(jsonObject.getInt("lid"));
                                mLevel.setName(jsonObject.getString("name"));
                                Log.e("level", "name :" + mLevel.getName());
                                mLevel.setUpdate_date(jsonObject.getString("update_date"));
                                mLevel.setTotal_slevel(jsonObject.getString("total_slevel"));
                                Utils.levels.add(mLevel);

                                JSONArray sub = jsonObject.getJSONArray("sub");

                                MSubLevel mSubLevel;
                                int count = 0;
                                for (int j = 0; j < sub.length(); j++) {
                                    JSONObject subLevel = sub.getJSONObject(j);

                                    count++;
                                    mSubLevel = new MSubLevel();
                                    mSubLevel.setParentId(mLevel.getLid());
                                    mSubLevel.setLid(subLevel.getInt("lid"));
                                    mSubLevel.setName(subLevel.getString("name"));
                                    Log.e("sublevel", "name :" + mSubLevel.getName());
                                    mSubLevel.setCoins_price(subLevel.getString("coins_price"));
                                    mSubLevel.setNo_of_coins(subLevel.getString("no_of_coins"));
//                                    mSubLevel.setLevelId(mLevel.getId());
//                                    mSubLevel.setPresentType(j + 1);
//                                    mSubLevel.setPresentId(count);
//                                    Log.e("Loge", "present id id ::" + mSubLevel.getPresentId());
                                    Utils.mSubLevelArrayList.add(mSubLevel);

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        saveLevelToDb();
                        saveSubLevelToDb();
                        getLocalData();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("json", "onfailer :" + responseString);
                    }
                }
        );
    }

    private void getOnlineDataByGson(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/levels.php",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject puzzle=response.getJSONObject("puzzle");
                    JSONArray level=puzzle.getJSONArray("level");
                    MLevel[] mLevels=gson.fromJson(level.toString(),MLevel[].class);
                    levels=new ArrayList<MLevel>(Arrays.asList(mLevels));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getOnlineContentsData() {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.post("http://www.radhooni.com/content/match_game/v1/contents.php", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Utils.contents = new ArrayList<MContents>();
                try {
                    JSONArray content = response.getJSONArray("contents");
                    MContents mContents;
                    int count = 0;
                    for (int i = 0; i < content.length(); i++) {
                        JSONObject jsonObject = content.getJSONObject(i);
                        count++;
                        mContents = new MContents();
                        mContents.setMid(jsonObject.getInt("mid"));
                        mContents.setLid(jsonObject.getInt("lid"));
                        mContents.setImg(jsonObject.getString("img"));
                        mContents.setAud(jsonObject.getString("aud"));
                        mContents.setTxt(jsonObject.getString("txt"));
                        mContents.setVid(jsonObject.getString("vid"));
                        mContents.setSen(jsonObject.getString("sen"));
                        mContents.setPresentType(count);


                        Utils.contents.add(mContents);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saveContentsToDb();
            }
        });
    }

    private void saveLevelToDb() {
        Log.e("SAVE", "Level size:" + Utils.levels.size());
        for (MLevel data : Utils.levels) {
            database.addLevelFromJson(data);
        }
    }

    private void saveSubLevelToDb() {
        Log.e("SAVE db", "mSubLevel size:" + Utils.mSubLevelArrayList.size());
        for (MSubLevel data2 : Utils.mSubLevelArrayList) {
            database.addSubFromJsom(data2);
        }
    }

    private void saveContentsToDb() {
        for (MContents data2 : Utils.contents) {
            database.addContentsFromJsom(data2);
        }
    }

    public void prepareDisplay() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);

        //drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavDrewer);
//        drawerFragment.setUp(R.id.fragNavDrewer, drawerLayout, toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(levelAdapter);

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

    private void exitYes() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.special) {
            LayoutInflater layoutInflater = LayoutInflater.from(LevelActivity.this);
            View view = layoutInflater.inflate(R.layout.scrollview_dailog, null);
            TextView textView = (TextView) view.findViewById(R.id.txtScroll);
//            textView.setText("Your really long message.");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            textView.setTextColor(0xffff00ff);
            alertDialog.setTitle("Title");
//alertDialog.setMessage("Here is a really long message.");
            alertDialog.setView(view);
//            alertDialog.setButton("OK", null);
            AlertDialog alert = alertDialog.create();
            alert.show();

        }
    }

}

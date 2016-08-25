package com.example.nayan.game3.activity;

import android.content.Intent;
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
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.MyAnimation;
import com.example.nayan.game3.utils.MyGame;
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

    Button btnNormal, btnHard, btnMedium;
    ImageView btnPlay;
    Toolbar toolbar;
    MLevel level;
    DrawerLayout drawerLayout;
    Animation animation;

    //   NavigationDrawerFragment drawerFragment;

    ArrayList<MLevel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_activity);
        list = new ArrayList<>();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnPlay = (ImageView) findViewById(R.id.btnPlay);
//        animation = AnimationUtils.loadAnimation(this, R.anim.zoom);
//        btnPlay.startAnimation(animation);
        MyAnimation.zoom(btnPlay, false);
        btnPlay.setOnClickListener(this);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(this);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(this);
        btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(this);


        prepareDisplay();
        getOnlineData();

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
        MyGame.easy = new ArrayList<MLevel>();
        MyGame.medium = new ArrayList<MLevel>();
        MyGame.hard = new ArrayList<MLevel>();
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
                                for (int j = 0; j < asset1.length(); j++) {
                                    JSONObject image = asset1.getJSONObject(j);

                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setPresentType(j + 1);
                                    asset.add(mAsset);

                                }
                                level.setAsset(asset);
                                MyGame.easy.add(level);


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
                                for (int j = 0; j < asset1.length(); j++) {
                                    JSONObject image = asset1.getJSONObject(j);

                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setPresentType(j + 1);
                                    asset.add(mAsset);

                                }
                                level.setAsset(asset);
                                MyGame.medium.add(level);


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
                                for (int j = 0; j < asset1.length(); j++) {
                                    JSONObject image = asset1.getJSONObject(j);

                                    mAsset = new MAsset();
                                    mAsset.setImages(image.getString("images"));
                                    mAsset.setSounds(image.getString("sounds"));
                                    mAsset.setHints(image.getString("hints"));
                                    mAsset.setPresentType(j + 1);
                                    asset.add(mAsset);

                                }
                                level.setAsset(asset);
                                MyGame.hard.add(level);


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
        if (v.getId() == R.id.btnPlay) {

            btnPlay.setVisibility(View.GONE);
            btnHard.setVisibility(View.VISIBLE);
            btnNormal.setVisibility(View.VISIBLE);
            btnMedium.setVisibility(View.VISIBLE);


        } else if (v.getId() == R.id.btnNormal) {
            Toast.makeText(this, "normal", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", 2);
            startActivity(intent);

        } else if (v.getId() == R.id.btnHard) {
            Toast.makeText(this, "hard", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", 4);
            startActivity(intent);
        } else if (v.getId() == R.id.btnMedium) {
            Toast.makeText(this, "medium", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        }
    }
}

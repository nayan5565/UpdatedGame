package com.example.nayan.game3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nayan.game3.adapter.MyRecyclerViewAdapter;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.MyGame;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";
    public static final String IMAGE_URL = "http://www.radhooni.com/content/match_game/v1/images/";
    static int value;
    static TextView textView;
    static MyRecyclerViewAdapter adapter;
    static ArrayList<MLevel> levels;
    RecyclerView recyclerView;
    SharedPreferences preferences;
    ImageView img, imgSetting;
    String image;

    public static void getOnlineData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/games.json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONObject puzzle = response.getJSONObject("puzzle");

                            if (value == 2) {
                                textView.setText("Normal");
                                JSONArray  easy = puzzle.getJSONArray("easy");
                                for (int i = 0; i < easy.length(); i++) {
                                    JSONObject jsonObject = easy.getJSONObject(i);

                                    MLevel level = new MLevel();
                                    level.seteId(jsonObject.getString("id"));
                                    level.setLevel(jsonObject.getString("level"));
                                    level.setCoinPrice(jsonObject.getString("coins_price"));
                                    level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

                                    JSONArray asset = jsonObject.getJSONArray("asset");
                                    for (int j = 0; j < asset.length(); j++) {
                                        JSONObject image = asset.getJSONObject(j);


                                        level.setImage(image.getString("images"));
                                        level.setSound("sounds");
                                        level.setHint("hints");
                                        level.setType(j + 1);

                                    }
                                    MyGame.easy = new ArrayList<MLevel>();
                                    MyGame.easy.add(level);


                                }


                            } else if (value == 3) {
                                textView.setText("Medium");
                                JSONArray  medium = puzzle.getJSONArray("medium");

                                for (int i = 0; i < medium.length(); i++) {
                                    JSONObject jsonObject = medium.getJSONObject(i);

                                    MLevel level = new MLevel();
                                    level.seteId(jsonObject.getString("id"));
                                    level.setLevel(jsonObject.getString("level"));
                                    level.setCoinPrice(jsonObject.getString("coins_price"));
                                    level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

                                    JSONArray asset = jsonObject.getJSONArray("asset");
                                    for (int j = 0; j < asset.length(); j++) {
                                        JSONObject image = asset.getJSONObject(j);


                                        level.setImage(image.getString("images"));
                                        level.setSound("sounds");
                                        level.setHint("hints");
                                        level.setType(j + 1);

                                    }
                                    MyGame.medium = new ArrayList<MLevel>();
                                    MyGame.medium.add(level);


                                }

                            } else if (value == 4) {
                                textView.setText("Hard");
                                JSONArray hard = puzzle.getJSONArray("hard");

                                for (int i = 0; i < hard.length(); i++) {
                                    JSONObject jsonObject = hard.getJSONObject(i);

                                    MLevel level = new MLevel();
                                    level.seteId(jsonObject.getString("id"));
                                    level.setLevel(jsonObject.getString("level"));
                                    level.setCoinPrice(jsonObject.getString("coins_price"));
                                    level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

                                    JSONArray asset = jsonObject.getJSONArray("asset");
                                    for (int j = 0; j < asset.length(); j++) {
                                        JSONObject image = asset.getJSONObject(j);


                                        level.setImage(image.getString("images"));
                                        level.setSound("sounds");
                                        level.setHint("hints");
                                        level.setType(j + 1);

                                    }
                                    MyGame.hard = new ArrayList<MLevel>();
                                    MyGame.hard.add(level);


                                }
                            }







                            if (MyGame.difficult == 1) {
                                adapter.setData(MyGame.easy);
                            } else if (MyGame.difficult2 == 2) {
                                adapter.setData(MyGame.medium);
                            } else if (MyGame.difficult3 == 3) {
                                adapter.setData(MyGame.hard);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = getIntent().getIntExtra("type", 0);
        Log.e("log", "is" + value);

        init();
        getOnlineData();

    }

    public void init() {
        textView = (TextView) findViewById(R.id.tct);
        image = getPREF(KEY_IMAGE);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);


        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
        if (image.equals(1 + "")) {
            Logic.getInstance(this).isSoundPlay = true;
            img.setImageResource(R.drawable.on);
        } else if (image.equals(0 + "")) {
            Logic.getInstance(this).isSoundPlay = false;
            img.setImageResource(R.drawable.off);
        }


        levels = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (value == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 3) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 4) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
       /* if (v.getId() == R.id.imgseting) {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("Game Information");
            dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
            dialog.setContentView(R.layout.dialog_setting);
            Button btnWin = (Button) dialog.findViewById(R.id.btnStatics);
            btnWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Logic.getInstance(MainActivity.this).showHistory();
                }
            });

            dialog.show();

        } */
        if (v.getId() == R.id.img) {
            if (Logic.getInstance(this).isSoundPlay == false) {
                Logic.getInstance(this).isSoundPlay = true;
                img.setImageResource(R.drawable.on);
                savePref(KEY_IMAGE, 1 + "");
            } else {
                Logic.getInstance(this).isSoundPlay = false;
                img.setImageResource(R.drawable.off);
                savePref(KEY_IMAGE, 0 + "");
            }

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

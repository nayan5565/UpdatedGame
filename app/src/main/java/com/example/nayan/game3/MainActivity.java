package com.example.nayan.game3;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nayan.game3.adapter.MyRecyclerViewAdapter;
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

    ArrayList<MLevel>levels;

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    SharedPreferences preferences;
    ImageView img, imgSetting;
    String image;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = getIntent().getIntExtra("type", 0);
        init();
        if (value == 2) {


        } else if (value == 3) {

        } else if (value == 4) {

        }


    }

    public void init() {
        image = getPREF(KEY_IMAGE);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);

        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
        if (image.equals(1 + "")) {
            img.setImageResource(R.drawable.on);
        }
       else if (image.equals(0 + "")) {
            img.setImageResource(R.drawable.off);
        }


        levels=new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (value == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 3) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (value == 4) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);



    }

    public void getOnlineData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.radhooni.com/content/match_game/v1/game.json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONObject puzzle = response.getJSONObject("puzzle");
                            JSONArray easy = puzzle.getJSONArray("easy");
                            for (int i = 0; i < easy.length(); i++) {
                                JSONObject jsonObject = easy.getJSONObject(i);

                                MLevel level = new MLevel();
                                level.seteId(jsonObject.getString("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

                                levels.add(level);

                                /*JSONArray image = easy.getJSONArray(i);
                                for (int j = 0; j < image.length(); j++) {
                                    JSONObject object1 = image.getJSONObject(j);


                                }*/
                            }
                            JSONArray medium=puzzle.getJSONArray("medium");
                            for (int i = 0; i < medium.length(); i++) {
                                JSONObject jsonObject = medium.getJSONObject(i);

                                MLevel level = new MLevel();
                                level.seteId(jsonObject.getString("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

                                levels.add(level);
                            }

                            JSONArray hard=puzzle.getJSONArray("hard");
                            for (int i = 0; i < hard.length(); i++) {
                                JSONObject jsonObject = hard.getJSONObject(i);

                                MLevel level = new MLevel();
                                level.seteId(jsonObject.getString("id"));
                                level.setLevel(jsonObject.getString("level"));
                                level.setCoinPrice(jsonObject.getString("coins_price"));
                                level.setNoOfCoinPrice(jsonObject.getString("no_of_coins"));

                                levels.add(level);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }
        );
    }

    /*public void hardGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/one.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/one.png");
        arrayList.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/two.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/two.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/three.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/three.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(7);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/four.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(8);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/four.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(9);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/five.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(10);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/five.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(11);
        mData.setType(6);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/six.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(12);
        mData.setType(6);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/six.png");
        arrayList.add(mData);

        Collections.shuffle(arrayList);

        adapter.setData(arrayList);
    }

    public void normalGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/seven.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/seven.png");
        arrayList.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/eight.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/eight.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/nineteen.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/nineteen.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/fifteen.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/fifteen.png");
        arrayList.add(mData);


        Collections.shuffle(arrayList);

        adapter.setData(arrayList);
    }

    public void mediumGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/nine.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/nine.png");
        arrayList.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/ten.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/ten.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/eleven.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/eleven.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(7);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/twelve.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(8);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/twelve.png");
        arrayList.add(mData);


        mData = new MData();
        mData.setId(9);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/fourteen.png");
        arrayList.add(mData);

        mData = new MData();
        mData.setId(10);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/fourteen.png");
        arrayList.add(mData);

        Collections.shuffle(arrayList);

        adapter.setData(arrayList);
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgseting) {
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

        } else if (v.getId() == R.id.img) {
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

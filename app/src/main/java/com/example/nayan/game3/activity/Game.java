package com.example.nayan.game3.activity;

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

import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.logic.NLogic;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.utils.MyGame;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by NAYAN on 8/20/2016.
 */
public class Game extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";

    ArrayList<MAsset> imageArrayList;
    ImageView img, imgSetting;
    RecyclerView recyclerView;

    MAsset mAsset=new MAsset();

    GameAdapter adapter;
    SharedPreferences preferences;
    int value;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);


        init();

        value = getIntent().getIntExtra("level", 0);

        if (MyGame.difficult == 1) {

            imageArrayList=MyGame.easy.get(value).getAsset();



        } else if (MyGame.difficult2 == 2) {

            imageArrayList=MyGame.medium.get(value).getAsset();


        } else if (MyGame.difficult3 == 3) {
            imageArrayList=MyGame.hard.get(value).getAsset();


        }
        adapter.setData(imageArrayList);


    }

    public void init() {

        image = getPREF(KEY_IMAGE);
        imgSetting = (ImageView) findViewById(R.id.imgseting);
        imgSetting.setOnClickListener(this);

        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
        if (image.equals(1 + "")) {
            NLogic.getInstance(this).isSoundPlay = true;
            img.setImageResource(R.drawable.on);
        } else if (image.equals(0 + "")) {
            NLogic.getInstance(this).isSoundPlay = false;
            img.setImageResource(R.drawable.off);
        }

        imageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        /*if (value == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 3) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (value == 4) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }*/
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new GameAdapter(this);
        recyclerView.setAdapter(adapter);


    }

    private void animation() {
        ScaleInAnimator sa = new ScaleInAnimator();
        sa.setRemoveDuration(2000);
        recyclerView.setItemAnimator(sa);
    }



    /*public void hardGame() {
        mData = new MData();
        mData.setId(1);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/one.png");
        list.add(mData);

        mData = new MData();
        mData.setId(2);
        mData.setType(1);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/one.png");
        list.add(mData)
        ;
        mData = new MData();
        mData.setId(3);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/two.png");
        list.add(mData);

        mData = new MData();
        mData.setId(4);
        mData.setType(2);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/two.png");
        list.add(mData);

        mData = new MData();
        mData.setId(5);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/three.png");
        list.add(mData);

        mData = new MData();
        mData.setId(6);
        mData.setType(3);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/three.png");
        list.add(mData);

        mData = new MData();
        mData.setId(7);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/four.png");
        list.add(mData);

        mData = new MData();
        mData.setId(8);
        mData.setType(4);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/four.png");
        list.add(mData);

        mData = new MData();
        mData.setId(9);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/five.png");
        list.add(mData);

        mData = new MData();
        mData.setId(10);
        mData.setType(5);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/five.png");
        list.add(mData);

        mData = new MData();
        mData.setId(11);
        mData.setType(6);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/six.png");
        list.add(mData);

        mData = new MData();
        mData.setId(12);
        mData.setType(6);
        mData.setImage("http://www.radhooni.com/content/match_game/v1/images/six.png");
        list.add(mData);

        Collections.shuffle(list);

        adapter.setData(list);
    }*/

    /*public void normalGame() {
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
                    NLogic.getInstance(Game.this).showHistory();
                }
            });

            dialog.show();

        } else if (v.getId() == R.id.img) {
            if (NLogic.getInstance(this).isSoundPlay == false) {
                NLogic.getInstance(this).isSoundPlay = true;
                img.setImageResource(R.drawable.on);
                savePref(KEY_IMAGE, 1 + "");
            } else {
                NLogic.getInstance(this).isSoundPlay = false;
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

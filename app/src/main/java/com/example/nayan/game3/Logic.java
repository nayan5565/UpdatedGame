package com.example.nayan.game3;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nayan.game3.adapter.MyRecyclerViewAdapter;
import com.example.nayan.game3.model.MData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by JEWEL on 8/12/2016.
 */
public class Logic {
    public static final String MyPREFERENCE = "mypref";
    public static final String HARD_GAME_MAX_POINT = "hardMax";
    public static final String NORMAL_GAME_MAX_POINT = "normalMax";
    public static final String MEDIUM_GAME_MAX_POINT = "mediumMax";
    static Logic logic;
    public int previousId, count, clickCount, matchCount, previousType;
    public boolean isSoundPlay = true;
    ArrayList<MData> arrayList;
    int preVoiusPoint;
    int presentPoint;
    SharedPreferences preferences;
    MediaPlayer mediaPlayer;
    Context context;
    Handler handler = new Handler();
    MyRecyclerViewAdapter adapter;



    public static Logic getInstance(Context context1) {
        if (logic==null){
            logic=new Logic();
        }
        logic.context=context1;
        return logic;

    }

    public void callData(ArrayList<MData>arrayList,MyRecyclerViewAdapter adapter){
        this.arrayList=arrayList;
        this.adapter=adapter;
    }

    public void bestPoint() {
        preferences = context.getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);
        if (arrayList.size() == 12) {
            preVoiusPoint = preferences.getInt(HARD_GAME_MAX_POINT, 0);
            Log.e("previous point ", "is : " + preVoiusPoint);

        } else if (arrayList.size() == 6) {
            preVoiusPoint = preferences.getInt(NORMAL_GAME_MAX_POINT, 0);
        } else if (arrayList.size() == 4) {
            preVoiusPoint = preferences.getInt(MEDIUM_GAME_MAX_POINT, 0);
        }
        if (presentPoint > preVoiusPoint) {
            //mData.setBestPoint(presentPoint);


        } else {
            //mData.setBestPoint(preVoiusPoint);
            presentPoint = preVoiusPoint;
        }


        Dialog dialog = new Dialog(context);
        dialog.setTitle("Best Point");
        dialog.setContentView(R.layout.row_d_best_point);
        TextView textView = (TextView) dialog.findViewById(R.id.txtBetPoint);
        textView.setText(presentPoint + "");

        SharedPreferences.Editor editor = preferences.edit();
        if (arrayList.size() == 12) {
            editor.putInt(HARD_GAME_MAX_POINT, presentPoint);
        } else if (arrayList.size() == 6) {
            editor.putInt(NORMAL_GAME_MAX_POINT, presentPoint);
        } else if (arrayList.size() == 4) {
            editor.putInt(MEDIUM_GAME_MAX_POINT, presentPoint);
        }


        editor.commit();


        dialog.show();


    }

    public void showInformation() {
        presentPoint = 100 / clickCount;
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Game Over");
        dialog.setContentView(R.layout.row_dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.txt);
        textView.setText("Point : " + presentPoint);
        Button button = (Button) dialog.findViewById(R.id.btnNewGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSound(R.raw.shuffle);
                resetList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getSound(int path) {
        if (isSoundPlay) {
            if (mediaPlayer != null) {
                //mediaPlayer.stop();
                //mediaPlayer.reset();
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, path);
            mediaPlayer.start();
        }
    }

    public void imageClick(final MData mData, int pos) {
        if (previousId == mData.getId() || mData.getStatus() == 1) {
            return;
        }
        clickCount++;

        arrayList.get(pos).setStatus(1);
        adapter.setData(arrayList);
        count++;
        if (count == 2) {

            if (previousType == mData.getType()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSound(R.raw.match2);
                    }
                }, 1000);

                matchCount++;

                if (matchCount == arrayList.size() / 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getSound(R.raw.gameover);
                        }
                    }, 2000);

                    showInformation();
                }
                previousId = 0;
                count = 0;
                return;
            } else {

                final int perevious = previousId;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSound(R.raw.fail);
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getId() == perevious || arrayList.get(i).getId() == mData.getId()) {
                                arrayList.get(i).setStatus(0);

                            }
                        }
                        adapter.setData(arrayList);
                    }
                }, 1000);
                count = 0;
                previousId = 0;
                return;
            }
        }
        previousId = mData.getId();
        previousType = mData.getType();
    }


    public void resetList() {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setStatus(0);
        }
        Collections.shuffle(arrayList);
        clickCount = 0;
        matchCount = 0;
        adapter.setData(arrayList);

    }
}

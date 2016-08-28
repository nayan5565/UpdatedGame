package com.example.nayan.game3.logic;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.activity.GameActivity;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NAYAN on 8/20/2016.
 */
public class NLogic {
    public static final String MyPREFERENCE = "mypref";
    public static final String HARD_GAME_MAX_POINT = "hardMax";
    public static final String EASY_GAME_MAX_POINT = "normalMax";
    public static final String MEDIUM_GAME_MAX_POINT = "mediumMax";
    public static final String Hard_GAME_WIN_NO = "hardWin";
    public static final String MEDIUM_GAME_WIN_NO = "mediumWin";
    public static final String NORMAL_GAME_WIN_NO = "normalWin";

    static NLogic nLogic;
    public int previousId, count, clickCount, matchWinCount, previousType, gameWinCount, previousPoint, presentPoint, bestPoint;


    ArrayList<MAsset> list;
    SharedPreferences preferences;
    Context context;
    Handler handler = new Handler();
    GameAdapter gameAdapter;
    GameActivity game=new GameActivity();


    private NLogic() {

    }

    public static NLogic getInstance(Context context1) {

        if (nLogic == null) {
            nLogic = new NLogic();
        }
        nLogic.context = context1;

        return nLogic;

    }

    public void callData(ArrayList<MAsset> list, GameAdapter adapter) {
        this.list = list;
        this.gameAdapter = adapter;
       /* bestPoint = getPref(HARD_GAME_MAX_POINT);
        gameWinCount = getPref(Hard_GAME_WIN_NO);*/
       /* if (list==Utils.hard.get(GameActivity.value).getAsset()) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            gameWinCount = getPref(Hard_GAME_WIN_NO);
        } else if (list==Utils.medium.get(GameActivity.value).getAsset()) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            gameWinCount = getPref(MEDIUM_GAME_WIN_NO);
        } else if (list==Utils.easy.get(GameActivity.value).getAsset()) {
            bestPoint = getPref(EASY_GAME_MAX_POINT);
            gameWinCount = getPref(NORMAL_GAME_WIN_NO);
        }*/


    }


    public void showInformation() {
        presentPoint = 100 / clickCount;
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("GameActivity Over");
        dialog.setContentView(R.layout.row_dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.txt);
        textView.setText("Point : " + presentPoint);
        Button newGame = (Button) dialog.findViewById(R.id.btnNewGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Utils.getSound(context,R.raw.shuffle);
                resetList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void imageClick(final MAsset mImage, int pos) {
        Log.e("Loge","present id ::"+mImage.getPresentId());
        if (previousId ==mImage .getPresentId() || mImage.getImageOpen() == 1) {
            return;
        }
        clickCount++;

        list.get(pos).setImageOpen(1);
        gameAdapter.setData(list);
        count++;
        if (count == 2) {

            if (previousType == mImage.getPresentType()) {
                matchWinCount++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Utils.getSound(context,R.raw.match2);


                    }
                }, 800);



                if (matchWinCount == list.size() / 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                           // Utils.getSound(context,R.raw.gameover);
                           /* game.animation();*/
                        }
                    }, 1000);
                    gameWinCount++;
                    presentPoint = 100 / clickCount;
                    /*if (list==Utils.easy.get(GameActivity.value).getAsset()) {
                        savePref(NORMAL_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            Log.e("log", "point");
                            savePref(EASY_GAME_MAX_POINT, presentPoint);
                        }
                    } else if (list==Utils.medium.get(GameActivity.value).getAsset()) {
                        savePref(MEDIUM_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            savePref(MEDIUM_GAME_MAX_POINT, presentPoint);
                        }
                    } else if (list==Utils.hard.get(GameActivity.value).getAsset()){
                        savePref(Hard_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            savePref(HARD_GAME_MAX_POINT, presentPoint);
                        }
                    }*/


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
                        Utils.getSound(context,R.raw.fail);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getPresentId() == perevious || list.get(i).getPresentId() == mImage.getPresentId()) {
                                list.get(i).setImageOpen(0);

                            }
                        }
                        gameAdapter.setData(list);
                    }
                }, 1000);
                count = 0;
                previousId = 0;
                return;
            }
        }
        previousId = mImage.getPresentId();
        previousType = mImage.getPresentType();
    }


    public void resetList() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageOpen(0);
        }
        Collections.shuffle(list);
        clickCount = 0;
        matchWinCount = 0;
        gameAdapter.setData(list);

    }

    private void savePref(String key, int value) {
        preferences = context.getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private int getPref(String key) {
        preferences = context.getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public String showHistory() {
       /* if (list==Utils.hard.get(GameActivity.value).getAsset()) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            gameWinCount = getPref(Hard_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);

        } else if (list==Utils.medium.get(GameActivity.value).getAsset()) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            gameWinCount = getPref(MEDIUM_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);

        } else if (list==Utils.easy.get(GameActivity.value).getAsset()) {
            bestPoint = getPref(EASY_GAME_MAX_POINT);
            gameWinCount = getPref(NORMAL_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);
        }*/
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Status");
        dialog.setContentView(R.layout.row_d_best_point);
        TextView textView = (TextView) dialog.findViewById(R.id.txtBetPoint);
        textView.setText("best point: " + bestPoint + "");
        TextView textView1 = (TextView) dialog.findViewById(R.id.txtTotalWin);
        textView1.setText("no of total win: " + gameWinCount + "");
        dialog.show();
        return "";
    }

}

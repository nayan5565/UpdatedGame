package com.example.nayan.game3.logic;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nayan.game3.R;
import com.example.nayan.game3.activity.Game;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.model.MAsset;

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
    public boolean isSoundPlay = true;

    ArrayList<MAsset> list;
    SharedPreferences preferences;
    MediaPlayer mediaPlayer;
    Context context;
    Handler handler = new Handler();
    GameAdapter adapter;
    Game game=new Game();


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
        this.adapter = adapter;
        bestPoint = getPref(HARD_GAME_MAX_POINT);
        gameWinCount = getPref(Hard_GAME_WIN_NO);
        /*if (list.size() == 12) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            gameWinCount = getPref(Hard_GAME_WIN_NO);
        } else if (list.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            gameWinCount = getPref(MEDIUM_GAME_WIN_NO);
        } else if (list.size() == 4) {
            bestPoint = getPref(EASY_GAME_MAX_POINT);
            gameWinCount = getPref(NORMAL_GAME_WIN_NO);
        }*/


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

    public void imageClick(final MAsset mImage, int pos) {
        if (previousId ==mImage .getPresentId() || mImage.getImageOpen() == 1) {
            return;
        }
        clickCount++;

        list.get(pos).setImageOpen(1);
        adapter.setData(list);
        count++;
        if (count == 2) {

            if (previousType == mImage.getPresentType()) {
                matchWinCount++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSound(R.raw.match2);


                    }
                }, 1000);



                if (matchWinCount == list.size() / 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getSound(R.raw.gameover);
                           /* game.animation();*/
                        }
                    }, 2000);
                    gameWinCount++;
                    presentPoint = 100 / clickCount;
                    if (list.size() == 4) {
                        savePref(NORMAL_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            Log.e("log", "point");
                            savePref(EASY_GAME_MAX_POINT, presentPoint);
                        }
                    } else if (list.size() == 6) {
                        savePref(MEDIUM_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            savePref(MEDIUM_GAME_MAX_POINT, presentPoint);
                        }
                    } else {
                        savePref(Hard_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            savePref(HARD_GAME_MAX_POINT, presentPoint);
                        }
                    }


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
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getPresentId() == perevious || list.get(i).getPresentId() == mImage.getPresentId()) {
                                list.get(i).setImageOpen(0);

                            }
                        }
                        adapter.setData(list);
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
        adapter.setData(list);

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

    public void showHistory() {
        if (list.size() == 12) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            gameWinCount = getPref(Hard_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);

        } else if (list.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            gameWinCount = getPref(MEDIUM_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);

        } else if (list.size() == 4) {
            bestPoint = getPref(EASY_GAME_MAX_POINT);
            gameWinCount = getPref(NORMAL_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);
        }
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Status");
        dialog.setContentView(R.layout.row_d_best_point);
        TextView textView = (TextView) dialog.findViewById(R.id.txtBetPoint);
        textView.setText("best point: " + bestPoint + "");
        TextView textView1 = (TextView) dialog.findViewById(R.id.txtTotalWin);
        textView1.setText("no of total win: " + gameWinCount + "");
        dialog.show();
    }

}

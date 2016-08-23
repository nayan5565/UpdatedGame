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

import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.model.MLevel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by JEWEL on 8/20/2016.
 */
public class NLogic {
    public static final String MyPREFERENCE = "mypref";
    public static final String HARD_GAME_MAX_POINT = "hardMax";
    public static final String NORMAL_GAME_MAX_POINT = "normalMax";
    public static final String MEDIUM_GAME_MAX_POINT = "mediumMax";
    public static final String Hard_GAME_WIN_NO = "hardWin";
    public static final String MEDIUM_GAME_WIN_NO = "mediumWin";
    public static final String NORMAL_GAME_WIN_NO = "normalWin";

    static NLogic nLogic;
    public int previousId, count, clickCount, matchCount, previousType, matchWin, preViousPoint, presentPoint, bestPoint;
    public boolean isSoundPlay = true;

    ArrayList<MLevel> list;
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

    public void callData(ArrayList<MLevel> list, GameAdapter adapter) {
        this.list = list;
        this.adapter = adapter;
        bestPoint = getPref(HARD_GAME_MAX_POINT);
        matchWin = getPref(Hard_GAME_WIN_NO);
        /*if (list.size() == 12) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            matchWin = getPref(Hard_GAME_WIN_NO);
        } else if (list.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            matchWin = getPref(MEDIUM_GAME_WIN_NO);
        } else if (list.size() == 4) {
            bestPoint = getPref(NORMAL_GAME_MAX_POINT);
            matchWin = getPref(NORMAL_GAME_WIN_NO);
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

    public void imageClick(final MLevel mImage, int pos) {
        if (previousId ==mImage .getId() || mImage.getStatus() == 1) {
            return;
        }
        clickCount++;

        list.get(pos).setStatus(1);
        adapter.setData(list);
        count++;
        if (count == 2) {

            if (previousType == mImage.getType()) {
                matchCount++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSound(R.raw.match2);


                    }
                }, 1000);



                if (matchCount == list.size() / 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getSound(R.raw.gameover);
                           /* game.animation();*/
                        }
                    }, 2000);
                    matchWin++;
                    presentPoint = 100 / clickCount;
                    if (list.size() == 4) {
                        savePref(NORMAL_GAME_WIN_NO, matchWin);
                        if (presentPoint > bestPoint) {
                            Log.e("log", "point");
                            savePref(NORMAL_GAME_MAX_POINT, presentPoint);
                        }
                    } else if (list.size() == 6) {
                        savePref(MEDIUM_GAME_WIN_NO, matchWin);
                        if (presentPoint > bestPoint) {
                            savePref(MEDIUM_GAME_MAX_POINT, presentPoint);
                        }
                    } else {
                        savePref(Hard_GAME_WIN_NO, matchWin);
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
                            if (list.get(i).getId() == perevious || list.get(i).getId() == mImage.getId()) {
                                list.get(i).setStatus(0);

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
        previousId = mImage.getId();
        previousType = mImage.getType();
    }


    public void resetList() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setStatus(0);
        }
        Collections.shuffle(list);
        clickCount = 0;
        matchCount = 0;
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
            matchWin = getPref(Hard_GAME_WIN_NO);
            Log.e("previous point ", "is : " + preViousPoint);

        } else if (list.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            matchWin = getPref(MEDIUM_GAME_WIN_NO);
            Log.e("previous point ", "is : " + preViousPoint);

        } else if (list.size() == 4) {
            bestPoint = getPref(NORMAL_GAME_MAX_POINT);
            matchWin = getPref(NORMAL_GAME_WIN_NO);
            Log.e("previous point ", "is : " + preViousPoint);
        }
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Status");
        dialog.setContentView(R.layout.row_d_best_point);
        TextView textView = (TextView) dialog.findViewById(R.id.txtBetPoint);
        textView.setText("best point: " + bestPoint + "");
        TextView textView1 = (TextView) dialog.findViewById(R.id.txtTotalWin);
        textView1.setText("no of total win: " + matchWin + "");
        dialog.show();
    }

}

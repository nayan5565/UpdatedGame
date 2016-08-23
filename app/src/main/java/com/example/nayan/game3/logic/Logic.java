package com.example.nayan.game3.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;

import com.example.nayan.game3.adapter.MyRecyclerViewAdapter;
import com.example.nayan.game3.model.MLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/12/2016.
 */
public class Logic {
    public static final String MyPREFERENCE = "mypref";
    public static final String HARD_GAME_MAX_POINT = "hardMax";
    public static final String NORMAL_GAME_MAX_POINT = "normalMax";
    public static final String MEDIUM_GAME_MAX_POINT = "mediumMax";
    public static final String Hard_GAME_WIN_NO = "hardWin";
    public static final String MEDIUM_GAME_WIN_NO = "mediumWin";
    public static final String NORMAL_GAME_WIN_NO = "normalWin";

    static Logic logic;
    public int previousId, count, clickCount, matchCount, previousType, matchWin, preViousPoint, presentPoint, bestPoint;
    public boolean isSoundPlay = true;
    ArrayList<MLevel> levels;
    SharedPreferences preferences;
    MediaPlayer mediaPlayer;
    Context context;
    Handler handler = new Handler();
    MyRecyclerViewAdapter adapter;


    private Logic() {

    }

    public static Logic getInstance(Context context1) {

        if (logic == null) {
            logic = new Logic();
        }
        logic.context = context1;

        return logic;

    }

    public void callData(ArrayList<MLevel> levels, MyRecyclerViewAdapter adapter) {
        this.levels = levels;
        this.adapter = adapter;
       /* if (levels.size() == 12) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            matchWin = getPref(Hard_GAME_WIN_NO);
        } else if (levels.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            matchWin = getPref(MEDIUM_GAME_WIN_NO);
        } else if (levels.size() == 4) {
            bestPoint = getPref(NORMAL_GAME_MAX_POINT);
            matchWin = getPref(NORMAL_GAME_WIN_NO);
        }*/


    }


    /*public void showInformation() {
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
    }*/

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

   /* public void imageClick(final MLevel mLevel, int pos) {
        if (previousId == mLevel.getId() || mLevel.getStatus() == 1) {
            return;
        }
        clickCount++;

        levels.get(pos).setStatus(1);
        adapter.setData(levels);
        count++;
        if (count == 2) {

            if (previousType == mLevel.getType()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSound(R.raw.match2);
                    }
                }, 1000);

                matchCount++;

                if (matchCount == levels.size() / 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getSound(R.raw.gameover);
                        }
                    }, 2000);
                    matchWin++;
                    presentPoint = 100 / clickCount;
                    if (levels.size() == 4) {
                        savePref(NORMAL_GAME_WIN_NO, matchWin);
                        if (presentPoint > bestPoint) {
                            Log.e("log", "point");
                            savePref(NORMAL_GAME_MAX_POINT, presentPoint);
                        }
                    } else if (levels.size() == 6) {
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
                        for (int i = 0; i < levels.size(); i++) {
                            if (levels.get(i).getId() == perevious || levels.get(i).getId() == mLevel.getId()) {
                                levels.get(i).setStatus(0);

                            }
                        }
                        adapter.setData(levels);
                    }
                }, 1000);
                count = 0;
                previousId = 0;
                return;
            }
        }
        previousId = mLevel.getId();
        previousType = mLevel.getType();
    }*/


    /*public void resetList() {
        for (int i = 0; i < levels.size(); i++) {
            levels.get(i).setStatus(0);
        }
        Collections.shuffle(levels);
        clickCount = 0;
        matchCount = 0;
        adapter.setData(levels);

    }*/

   /* private void savePref(String key, int value) {
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
        if (levels.size() == 12) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            matchWin = getPref(Hard_GAME_WIN_NO);
            Log.e("previous point ", "is : " + preViousPoint);

        } else if (levels.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            matchWin = getPref(MEDIUM_GAME_WIN_NO);
            Log.e("previous point ", "is : " + preViousPoint);

        } else if (levels.size() == 4) {
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
    }*/
}

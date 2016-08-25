package com.example.nayan.game3.logic;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.model.MLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/12/2016.
 */
public class Logic {
   /* public static final String MyPREFERENCE = "mypref";
    public static final String HARD_GAME_MAX_POINT = "hardMax";
    public static final String NORMAL_GAME_MAX_POINT = "normalMax";
    public static final String MEDIUM_GAME_MAX_POINT = "mediumMax";
    public static final String Hard_GAME_WIN_NO = "hardWin";
    public static final String MEDIUM_GAME_WIN_NO = "mediumWin";
    public static final String NORMAL_GAME_WIN_NO = "normalWin";*/

    static Logic logic;
    // public int previousId, count, clickCount, matchCount, previousType, matchWin, preViousPoint, presentPoint, bestPoint;
    public boolean isSoundPlay = true;
    ArrayList<MLevel> levels;
    //SharedPreferences preferences;
    MediaPlayer mediaPlayer;
    Context context;
    //Handler handler = new Handler();
    LevelAdapter levelAdapter;


    private Logic() {

    }

    public static Logic getInstance(Context context1) {

        if (logic == null) {
            logic = new Logic();
        }
        logic.context = context1;

        return logic;

    }

    public void callData(ArrayList<MLevel> levels, LevelAdapter adapter) {
        this.levels = levels;
        this.levelAdapter = adapter;
       /* if (levels.size() == 12) {
            bestPoint = getPref(HARD_GAME_MAX_POINT);
            gameWinCount = getPref(Hard_GAME_WIN_NO);
        } else if (levels.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            gameWinCount = getPref(MEDIUM_GAME_WIN_NO);
        } else if (levels.size() == 4) {
            bestPoint = getPref(EASY_GAME_MAX_POINT);
            gameWinCount = getPref(NORMAL_GAME_WIN_NO);
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
        if (previousId == mLevel.getPresentId() || mLevel.getImageOpen() == 1) {
            return;
        }
        clickCount++;

        levels.get(pos).setImageOpen(1);
        levelAdapter.setData(levels);
        count++;
        if (count == 2) {

            if (previousType == mLevel.getPresentType()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSound(R.raw.match2);
                    }
                }, 1000);

                matchWinCount++;

                if (matchWinCount == levels.size() / 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getSound(R.raw.gameover);
                        }
                    }, 2000);
                    gameWinCount++;
                    presentPoint = 100 / clickCount;
                    if (levels.size() == 4) {
                        savePref(NORMAL_GAME_WIN_NO, gameWinCount);
                        if (presentPoint > bestPoint) {
                            Log.e("log", "point");
                            savePref(EASY_GAME_MAX_POINT, presentPoint);
                        }
                    } else if (levels.size() == 6) {
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
                        for (int i = 0; i < levels.size(); i++) {
                            if (levels.get(i).getPresentId() == perevious || levels.get(i).getPresentId() == mLevel.getPresentId()) {
                                levels.get(i).setImageOpen(0);

                            }
                        }
                        levelAdapter.setData(levels);
                    }
                }, 1000);
                count = 0;
                previousId = 0;
                return;
            }
        }
        previousId = mLevel.getPresentId();
        previousType = mLevel.getPresentType();
    }*/


    /*public void resetList() {
        for (int i = 0; i < levels.size(); i++) {
            levels.get(i).setImageOpen(0);
        }
        Collections.shuffle(levels);
        clickCount = 0;
        matchWinCount = 0;
        levelAdapter.setData(levels);

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
            gameWinCount = getPref(Hard_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);

        } else if (levels.size() == 6) {
            bestPoint = getPref(MEDIUM_GAME_MAX_POINT);
            gameWinCount = getPref(MEDIUM_GAME_WIN_NO);
            Log.e("previous point ", "is : " + previousPoint);

        } else if (levels.size() == 4) {
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
    }*/
}

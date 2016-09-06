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
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NAYAN on 8/20/2016.
 */
public class NLogic {
    public static final String MyPREFERENCE = "mypref";
    public static int previousId, count, clickCount, matchWinCount, previousType, gameWinCount, previousPoint, presentPoint, bestPoint;
    static NLogic nLogic;

    private ArrayList<MAsset> list;
    private SharedPreferences preferences;
    private Context context;
    private Handler handler = new Handler();
    private GameAdapter gameAdapter;
    private MLevel mLevel;


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

        bestPoint = mLevel.getBestpoint();
//        gameWinCount = mLevel.getLevelWinCount();


    }

    public void setLevel(MLevel mLevel) {
        this.mLevel = mLevel;
    }

    public void saveDb() {
        MyDatabase db = new MyDatabase(context);
        db.addLevelFromJson(mLevel);
    }


    public void showInformation(int listSize) {
        presentPoint = pointCount(listSize);
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("GameActivity Over");
        dialog.setContentView(R.layout.row_dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.txt);
        textView.setText("Point : " + presentPoint);
        Button newGame = (Button) dialog.findViewById(R.id.btnNewGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getSound(context, R.raw.shuffle);
                resetList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void imageClick(final MAsset mImage, int pos, int listSize) {
        Log.e("Loge", "present id ::" + mImage.getPresentId());


        if (previousId == mImage.getPresentId() || mImage.getImageOpen() == Utils.IMAGE_ON||count>1) {
            return;
        }
        clickCount++;

        list.get(pos).setImageOpen(Utils.IMAGE_ON);
        gameAdapter.setData(list);
        count++;
        if (count == 2) {

            if (previousType == mImage.getPresentType()) {
                matchWinCount++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Utils.getSound(context, R.raw.match2);
                        count = 0;


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
                    //starting game over
                    gameWinCount = mLevel.getLevelWinCount();
                    gameWinCount++;
                    mLevel.setLevelWinCount(gameWinCount);
                    savePoint(listSize);
                    showInformation(listSize);
                }
                previousId = 0;

                return;
            } else {

                final int perevious = previousId;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utils.getSound(context, R.raw.fail);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getPresentId() == perevious || list.get(i).getPresentId() == mImage.getPresentId()) {
                                list.get(i).setImageOpen(Utils.IMAGE_OFF);

                            }
                        }
                        gameAdapter.setData(list);
                        count = 0;
                    }
                }, 1000);
                previousId = 0;
                return;
            }
        }
        previousId = mImage.getPresentId();
        previousType = mImage.getPresentType();
    }


    public void resetList() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageOpen(Utils.IMAGE_OFF);
        }
        Collections.shuffle(list);
        clickCount = 0;
        matchWinCount = 0;
        gameAdapter.setData(list);

    }

    public void savePoint(int listSize) {
        presentPoint = pointCount(listSize);
        if (presentPoint > bestPoint) {
            mLevel.setBestpoint(presentPoint);
            saveDb();
        }

    }

    public int pointCount(int listSize) {
        int point = 50;

        if (clickCount == listSize) {
            point = 100;
        } else if (clickCount > listSize && clickCount <= listSize + listSize / 2) {
            point = 75;
        }

        return point;
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

        Dialog dialog = new Dialog(context);
        dialog.setTitle("Status");
        dialog.setContentView(R.layout.row_d_best_point);
        TextView textView = (TextView) dialog.findViewById(R.id.txtBetPoint);
        textView.setText("best point: " + mLevel.getBestpoint() + "\nWin count : " + mLevel.getLevelWinCount());
        TextView textView1 = (TextView) dialog.findViewById(R.id.txtTotalWin);
//        textView1.setText("no of total win: " + gameWinCount + "");
        dialog.show();
        return "";
    }

}

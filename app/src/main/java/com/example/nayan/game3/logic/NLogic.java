package com.example.nayan.game3.logic;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nayan.game3.R;
import com.example.nayan.game3.VungleAdManager;
import com.example.nayan.game3.adapter.GameAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MContents;
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

    private ArrayList<MContents> list;
    private SharedPreferences preferences;
    private Context context;
    private Handler handler = new Handler();
    private GameAdapter gameAdapter;
    private MContents mContents;


    private NLogic() {

    }

    public static NLogic getInstance(Context context1) {

        if (nLogic == null) {
            nLogic = new NLogic();
        }
        nLogic.context = context1;

        return nLogic;

    }

    public void callData(ArrayList<MContents> list, GameAdapter adapter) {
        this.list = list;
        this.gameAdapter = adapter;

//        bestPoint = mContents.getBestpoint();
//        gameWinCount = mContents.getLevelWinCount();


    }

    public void setLevel(MContents mContents) {
        this.mContents = mContents;
    }

    public void saveDb() {
        MyDatabase db = new MyDatabase(context);
//        db.addLevelFromJson(mContents);
    }


    public void showInformation(int listSize) {
        presentPoint = pointCount(listSize);
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("GameActivity Over");
        dialog.setCancelable(false);
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

    public void textClick(MContents mContents) {
        if (mContents.getMid() == clickCount + 1) {
            clickCount = mContents.getMid();
            count++;
            Toast.makeText(context, mContents.getTxt(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "wrong click", Toast.LENGTH_SHORT).show();
        }
        if (count == list.size()) {
            count = 0;
            clickCount = 0;
            Toast.makeText(context, "game over", Toast.LENGTH_SHORT).show();
        }

    }

    public void imageClick(final MContents mImage, int pos, int listSize) {
        Log.e("Loge", "present id ::" + mImage.getPresentId());


        if (previousType == mImage.getPresentType() || count > 1||mImage.getClick()==Utils.IMAGE_ON) {
            Toast.makeText(context, "same click", Toast.LENGTH_SHORT).show();
            return;
        }
        clickCount++;

        list.get(pos).setClick(Utils.IMAGE_ON);
        gameAdapter.setData(list);
        count++;
        Utils.getSound(context, R.raw.click);
        if (count == 2) {

            if (previousId == mImage.getMid()) {
                Toast.makeText(context, "match", Toast.LENGTH_SHORT).show();
                Log.e("log", "matchwincount : " + matchWinCount);
                matchWinCount++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Utils.getSound(context, R.raw.match2);
                        count = 0;


                    }
                }, 800);


                if (matchWinCount == list.size() / 2) {
//                    VungleAdManager.getInstance(context).play();

                    Toast.makeText(context, "game over", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Utils.getSound(context,R.raw.gameover);
                           /* game.animation();*/
                        }
                    }, 1000);
                    //starting game over
//                    gameWinCount = mContents.getLevelWinCount();
                    gameWinCount++;
                    Log.e("log", "game over : " + gameWinCount);
//                    mContents.setLevelWinCount(gameWinCount);
//                    savePoint(listSize);
//                    showInformation(listSize);
                    matchWinCount=0;
                }
                previousType = 0;

                return;
            } else {

                final int perevious = previousType;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utils.getSound(context, R.raw.fail);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getPresentType() == perevious || list.get(i).getPresentType() == mImage.getPresentType()) {
                                list.get(i).setClick(Utils.IMAGE_OFF);
                                Toast.makeText(context, "did not match or same click", Toast.LENGTH_SHORT).show();
//
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
        previousId = mImage.getMid();
        previousType = mImage.getPresentType();
    }


    public void resetList() {
        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setImageOpen(Utils.IMAGE_OFF);
        }
        Collections.shuffle(list);
        clickCount = 0;
        matchWinCount = 0;
        gameAdapter.setData(list);

    }

    public void savePoint(int listSize) {
        presentPoint = pointCount(listSize);
        if (presentPoint > bestPoint) {
//            mContents.setBestpoint(presentPoint);
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

    public String showHistory() {

        Dialog dialog = new Dialog(context);
        dialog.setTitle("Status");
        dialog.setContentView(R.layout.row_d_best_point);
        TextView textView = (TextView) dialog.findViewById(R.id.txtBetPoint);
//        textView.setText("best point: " + mContents.getBestpoint() + "\nWin count : " + mContents.getLevelWinCount());
        TextView textView1 = (TextView) dialog.findViewById(R.id.txtTotalWin);
//        textView1.setText("no of total win: " + gameWinCount + "");
        dialog.show();
        return "";
    }

}

package com.example.nayan.game3.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nayan.game3.DialogSoundOnOff;
import com.example.nayan.game3.InMobAdManager;
import com.example.nayan.game3.R;
import com.example.nayan.game3.adapter.LevelAdapter;
import com.example.nayan.game3.adapter.SubLevelAdapter;
import com.example.nayan.game3.database.MyDatabase;
import com.example.nayan.game3.model.MContents;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;
import com.example.nayan.game3.utils.Utils;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class SubLevelActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String IMAGE_URL = "http://www.radhooni.com/content/match_game/v1/images/";
    private static int value;
    private static SubLevelAdapter subLevelAdapter;
    private static ArrayList<MSubLevel> mSubLevels;
    private static ArrayList<MContents> contentses;
    private static MSubLevel mSubLevel = new MSubLevel();
    private MyDatabase database;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView textView;
    private int STORAGE_PERMISSION_CODE = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_level_activity);


        value = getIntent().getIntExtra("id", 0);
        Log.e("log", "is" + value);

        init();
        prepareDisplay();
        getLocalData();
//        downloadAssets();

        if (value == 1) {
            textView.setText("Normal");
            textView.setTextColor(0xff888888);
        }
//        AdView adView = (AdView) findViewById(R.id.adView);
//        InMobAdManager.getInstance(this).loadAd(adView);


    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                downloadAssets();
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void downloadAssets() {
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            ArrayList<String> uniqueImage = new ArrayList<>();
//            for (int i = 0; i < contentses.size(); i++) {
//                ArrayList<MContents> assetArrayList = database.getContentsData(contentses.get(i).getMid());
//                for (int j = 0; j < assetArrayList.size(); j++) {
//                    if (!uniqueImage.contains(assetArrayList.get(j).getImg())) {
//                        uniqueImage.add(assetArrayList.get(j).getImg());
//                    }
//                }
//            }
//            for (int i = 0; i < uniqueImage.size(); i++) {
//                Log.e("DOWNLOAD_PATH", "Path:" + LevelActivity.getPath(uniqueImage.get(i)));
//                File file = new File(LevelActivity.getPath(uniqueImage.get(i)));
//                if (!file.exists()) {
//                    new DownLoadAsyncTask(this, LevelActivity.getPath(uniqueImage.get(i))).execute(IMAGE_URL + uniqueImage.get(i));
//                }
//
//            }
//        } else requestStoragePermission();
//
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();

        } else if (id == R.id.action_settings) {
            DialogSoundOnOff.dialogShow(this);
            return true;
        }

        //id unused
        return super.onOptionsItemSelected(item);
    }

    private void getLocalData() {
        mSubLevels = database.getSubLevelData(value);
        Log.e("getDb", "sublevel : " + mSubLevels.size());

        subLevelAdapter.setData(mSubLevels);


    }

    @Override
    protected void onRestart() {
        getLocalData();
        super.onRestart();
    }

    private void init() {
        database = new MyDatabase(this);
        textView = (TextView) findViewById(R.id.tct);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        // if (value == Utils.EASY) {

        //}

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        subLevelAdapter = new SubLevelAdapter(this);


    }

    private void prepareDisplay() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(subLevelAdapter);
    }

    @Override
    public void onClick(View v) {
       /* if (v.getPresentId() == R.id.imgseting) {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("Class1Activity Information");
            dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
            dialog.setContentView(R.layout.dialog_setting);
            Button btnWin = (Button) dialog.findViewById(R.id.btnStatics);
            btnWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Logic.getInstance(SubLevelActivity.this).showHistory();
                }
            });

            dialog.show();

        } */

    }


}

package com.example.nayan.game3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JEWEL on 8/4/2016.
 */
public class OpenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlay, btnNormal, btnHard, btnMedium;

    Toolbar toolbar;
    DrawerLayout drawerLayout;

 //   NavigationDrawerFragment drawerFragment;

    ArrayList<MLevel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_activity);
        list = new ArrayList<>();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(this);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(this);
        btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(this);


        prepareDisplay();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void prepareDisplay() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        //drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavDrewer);
//        drawerFragment.setUp(R.id.fragNavDrewer, drawerLayout, toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlay) {
            btnHard.setVisibility(View.VISIBLE);
            btnNormal.setVisibility(View.VISIBLE);
            btnMedium.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);

        } else if (v.getId() == R.id.btnNormal) {
            Toast.makeText(this, "normal", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("type", 2);
            startActivity(intent);

        } else if (v.getId() == R.id.btnHard) {
            Toast.makeText(this, "hard", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("type", 4);
            startActivity(intent);
        } else if (v.getId() == R.id.btnMedium) {
            Toast.makeText(this, "medium", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        }
    }
}

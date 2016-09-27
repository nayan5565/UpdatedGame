package com.example.nayan.game3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MContents;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/24/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_LEVEL_TABLE = "level";
    private static final String DATABASE_CONTENTS_TABLE = "contents";
    private static final String DATABASE_SUB_LEVEL_TABLE = "sub";

    private static final String KEY_UPDATE_DATE = "update_date";
    private static final String KEY_TOTAL_S_LEVEL = "total_slevel";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LEVEL_ID = "lid";
    private static final String KEY_MODEL_ID = "mid";
    private static final String KEY_SEN = "sen";
    private static final String KEY_IMAGE = "img";
    private static final String KEY_SOUNDS = "aud";
    private static final String KEY_NAME = "name";
    private static final String KEY_VIDEO = "vid";
    private static final String KEY_TEXT = "txt";
    private static final String KEY_COINS_PRICE = "coins_price";
    private static final String KEY_NO_OF_COINS = "no_of_coins";
    private static final String KEY_PRESENT_ID = "present_id";
    private static final String KEY_PRESENT_TYPE = "present_type";
    private static final String KEY_BEST_POINT = "best_point";
    private static final String KEY_LEVEL_WIN_COUNT = "win_count";


    private static final String DATABASE_CREATE_LEVEL_TABLE = "create table if not exists "
            + DATABASE_LEVEL_TABLE + "("
            + KEY_LEVEL_ID + " integer primary key, "
            + KEY_NAME + " text, "
            + KEY_UPDATE_DATE + " text, "
            + KEY_DIFFICULTY + " integer, "
            + KEY_BEST_POINT + " integer, "
            + KEY_LEVEL_WIN_COUNT + " integer, "
            + KEY_TOTAL_S_LEVEL + " text)";
    private static final String DATABASE_CREATE_CONTENTS_TABLE = "create table if not exists "
            + DATABASE_CONTENTS_TABLE + "("
            + KEY_LEVEL_ID + " integer, "
            + KEY_MODEL_ID + " integer primary key, "
            + KEY_PRESENT_ID + " integer, "
            + KEY_PRESENT_TYPE + " integer, "
            + KEY_IMAGE + " text, "
            + KEY_SEN + " text, "
            + KEY_TEXT + " text, "
            + KEY_VIDEO + " text, "
            + KEY_SOUNDS + " text)";
    private static final String DATABASE_CREATE_SUB_LEVEL_TABLE = "create table if not exists "
            + DATABASE_SUB_LEVEL_TABLE + "("
            + KEY_LEVEL_ID + " integer primary key, "
            + KEY_NAME + " text, "
            + KEY_COINS_PRICE + " text, "
            + KEY_NO_OF_COINS + " text)";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_LEVEL_TABLE);
        db.execSQL(DATABASE_CREATE_CONTENTS_TABLE);
        db.execSQL(DATABASE_CREATE_SUB_LEVEL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_LEVEL_TABLE);
        db.execSQL("drop table if exists " + DATABASE_CONTENTS_TABLE);
        db.execSQL("drop table if exists " + DATABASE_SUB_LEVEL_TABLE);
        onCreate(db);

    }

    public void addLevelFromJson(MLevel mLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, mLevel.getName());
            values.put(KEY_LEVEL_ID, mLevel.getLid());
            values.put(KEY_UPDATE_DATE, mLevel.getUpdate_date());
            values.put(KEY_DIFFICULTY, mLevel.getDifficulty());
            values.put(KEY_TOTAL_S_LEVEL, mLevel.getTotal_slevel());
            if (mLevel.getBestpoint() > 0) {
                values.put(KEY_BEST_POINT, mLevel.getBestpoint());
            }

            if (mLevel.getLevelWinCount() > 0) {
                values.put(KEY_LEVEL_WIN_COUNT, mLevel.getLevelWinCount());
            }

            String sql = "select * from " + DATABASE_LEVEL_TABLE + " where " + KEY_LEVEL_ID + "='" + mLevel.getLid() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_LEVEL_TABLE, values, KEY_LEVEL_ID + "=?", new String[]{mLevel.getLid() + ""});
                Log.e("log", "update : " + mLevel.getLid());
            } else {
                long v = db.insert(DATABASE_LEVEL_TABLE, null, values);
                Log.e("log", "return : " + v);

            }


            db.close();
        } catch (Exception e) {

        }

    }

    public void addSubFromJsom(MSubLevel mSubLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL_ID, mSubLevel.getLid());
            values.put(KEY_NAME, mSubLevel.getName());
            values.put(KEY_COINS_PRICE, mSubLevel.getCoins_price());
            values.put(KEY_NO_OF_COINS, mSubLevel.getNo_of_coins());

            String sql = "select * from " + DATABASE_SUB_LEVEL_TABLE + " where " + KEY_LEVEL_ID + "='" + mSubLevel.getLid() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_SUB_LEVEL_TABLE, values, KEY_LEVEL_ID + "=?", new String[]{mSubLevel.getLid() + ""});
                Log.e("log", "Assetupdate : " + update);
            } else {
                long v = db.insert(DATABASE_SUB_LEVEL_TABLE, null, values);
                Log.e("log", "Assetreturn : " + v);

            }


            db.close();
        } catch (Exception e) {

        }

    }
    public void addContentsFromJsom(MContents mContents) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL_ID, mContents.getLid());
            values.put(KEY_MODEL_ID, mContents.getMid());
            values.put(KEY_IMAGE, mContents.getImg());
            values.put(KEY_TEXT, mContents.getTxt());
            values.put(KEY_SOUNDS, mContents.getAud());
            values.put(KEY_VIDEO, mContents.getVid());
            values.put(KEY_SEN, mContents.getSen());

            String sql = "select * from " + DATABASE_CONTENTS_TABLE + " where " + KEY_MODEL_ID + "='" + mContents.getMid() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_CONTENTS_TABLE, values, KEY_MODEL_ID + "=?", new String[]{mContents.getMid() + ""});
                Log.e("log", "Assetupdate : " + update);
            } else {
                long v = db.insert(DATABASE_CONTENTS_TABLE, null, values);
                Log.e("log", "Assetreturn : " + v);

            }


            db.close();
        } catch (Exception e) {

        }

    }



    public ArrayList<MLevel> getLevelData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MLevel> levelArrayList = new ArrayList<>();

        MLevel mLevel;
        String sql = "select * from " + DATABASE_LEVEL_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        Log.e("cursor","count :"+cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.e("do","start");
                mLevel = new MLevel();
                mLevel.setLid(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
                mLevel.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                mLevel.setUpdate_date(cursor.getString(cursor.getColumnIndex(KEY_UPDATE_DATE)));
                mLevel.setTotal_slevel(cursor.getString(cursor.getColumnIndex(KEY_TOTAL_S_LEVEL)));
                mLevel.setDifficulty(cursor.getInt(cursor.getColumnIndex(KEY_DIFFICULTY)));
                mLevel.setBestpoint(cursor.getInt(cursor.getColumnIndex(KEY_BEST_POINT)));
                mLevel.setLevelWinCount(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_WIN_COUNT)));
                levelArrayList.add(mLevel);
                Log.e("do","end");
            } while (cursor.moveToNext());
        }

        db.close();


        return levelArrayList;
    }

    public ArrayList<MSubLevel> getSubLevelData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MSubLevel> assetArrayList = new ArrayList<>();

        MSubLevel mSubLevel;
        String sql = "select * from " + DATABASE_SUB_LEVEL_TABLE + " where " + KEY_LEVEL_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mSubLevel = new MSubLevel();
                mSubLevel.setLid(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
                mSubLevel.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                mSubLevel.setCoins_price(cursor.getString(cursor.getColumnIndex(KEY_COINS_PRICE)));
                mSubLevel.setNo_of_coins(cursor.getString(cursor.getColumnIndex(KEY_NO_OF_COINS)));
                assetArrayList.add(mSubLevel);

            } while (cursor.moveToNext());
        }

        db.close();


        return assetArrayList;
    }
    public ArrayList<MContents> getContentsData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MContents> assetArrayList = new ArrayList<>();

        MContents mContents;
        String sql = "select * from " + DATABASE_CONTENTS_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mContents = new MContents();
                mContents.setLid(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
                mContents.setMid(cursor.getInt(cursor.getColumnIndex(KEY_MODEL_ID)));
                mContents.setAud(cursor.getString(cursor.getColumnIndex(KEY_SOUNDS)));
                mContents.setVid(cursor.getString(cursor.getColumnIndex(KEY_VIDEO)));
                mContents.setTxt(cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
                mContents.setImg(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                mContents.setSen(cursor.getString(cursor.getColumnIndex(KEY_SEN)));
                assetArrayList.add(mContents);

            } while (cursor.moveToNext());
        }

        db.close();


        return assetArrayList;
    }
}

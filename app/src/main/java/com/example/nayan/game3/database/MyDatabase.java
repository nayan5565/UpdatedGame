package com.example.nayan.game3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.util.Log;

import com.example.nayan.game3.model.MContents;
import com.example.nayan.game3.model.MLevel;
import com.example.nayan.game3.model.MSubLevel;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by NAYAN on 8/24/2016.
 */
public class MyDatabase {
    private Context context;
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_LEVEL_TABLE = "level";
    private static final String DATABASE_CONTENTS_TABLE = "contents";
    private static final String DATABASE_SUB_LEVEL_TABLE = "sub";

    private static final String KEY_UPDATE_DATE = "update_date";
    private static final String KEY_TOTAL_S_LEVEL = "total_slevel";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LEVEL_ID = "lid";
    private static final String KEY_MODEL_ID = "mid";
    private static final String KEY_PARENT_ID = "pid";
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

    private final String TAG = getClass().getSimpleName();
    private final String PASS = "test123";

    private SQLiteDatabase db;


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
            + KEY_PARENT_ID + " integer, "
            + KEY_COINS_PRICE + " text, "
            + KEY_NO_OF_COINS + " text)";

    public MyDatabase(Context context) {

        this.context = context;
        openDB(context);
        onCreate();
    }

    private void openDB(Context context) {
        SQLiteDatabase.loadLibs(context);
        File databaseFile = context.getDatabasePath(DATABASE_NAME);
        if (!databaseFile.exists()) {
            databaseFile.mkdirs();
            databaseFile.delete();
        }
        db = SQLiteDatabase.openOrCreateDatabase(databaseFile, PASS, null);
    }


    public void onCreate() {
        db.execSQL(DATABASE_CREATE_LEVEL_TABLE);
        db.execSQL(DATABASE_CREATE_CONTENTS_TABLE);
        db.execSQL(DATABASE_CREATE_SUB_LEVEL_TABLE);

    }

    public void addLevelFromJson(MLevel mLevel) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, mLevel.getName());
            values.put(KEY_LEVEL_ID, mLevel.getLid());
            values.put(KEY_UPDATE_DATE, mLevel.getUpdate_date());
//            values.put(KEY_DIFFICULTY, mLevel.getDifficulty());
            values.put(KEY_TOTAL_S_LEVEL, mLevel.getTotal_slevel());
//            if (mLevel.getBestpoint() > 0) {
//                values.put(KEY_BEST_POINT, mLevel.getBestpoint());
//            }

            if (mLevel.getLevelWinCount() > 0) {
                values.put(KEY_LEVEL_WIN_COUNT, mLevel.getLevelWinCount());
            }

            String sql = "select * from " + DATABASE_LEVEL_TABLE + " where " + KEY_LEVEL_ID + "='" + mLevel.getLid() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_LEVEL_TABLE, values, KEY_LEVEL_ID + "=?", new String[]{mLevel.getLid() + ""});
                Log.e("log", "update : " + mLevel.getLid());
            } else {
                long v = db.insert(DATABASE_LEVEL_TABLE, null, values);
                Log.e("log", "return : " + v);

            }


        } catch (Exception e) {

        }
        cursor.close();
    }

    public void addSubFromJsom(MSubLevel mSubLevel) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL_ID, mSubLevel.getLid());
            values.put(KEY_PARENT_ID, mSubLevel.getParentId());
            values.put(KEY_NAME, mSubLevel.getName());
            values.put(KEY_COINS_PRICE, mSubLevel.getCoins_price());
            values.put(KEY_NO_OF_COINS, mSubLevel.getNo_of_coins());

            String sql = "select * from " + DATABASE_SUB_LEVEL_TABLE + " where " + KEY_LEVEL_ID + "='" + mSubLevel.getLid() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_SUB_LEVEL_TABLE, values, KEY_LEVEL_ID + "=?", new String[]{mSubLevel.getLid() + ""});
                Log.e("log", "sub level update : " + update);
            } else {
                long v = db.insert(DATABASE_SUB_LEVEL_TABLE, null, values);
                Log.e("log", "sub level insert : " + v);

            }


        } catch (Exception e) {

        }
        cursor.close();
    }

    public void addContentsFromJsom(MContents mContents) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL_ID, mContents.getLid());
            values.put(KEY_MODEL_ID, mContents.getMid());
            values.put(KEY_IMAGE, mContents.getImg());
            values.put(KEY_TEXT, mContents.getTxt());
            values.put(KEY_SOUNDS, mContents.getAud());
            values.put(KEY_PRESENT_TYPE, mContents.getPresentType());
            values.put(KEY_VIDEO, mContents.getVid());
            values.put(KEY_SEN, mContents.getSen());

            String sql = "select * from " + DATABASE_CONTENTS_TABLE + " where " + KEY_MODEL_ID + "='" + mContents.getMid() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_CONTENTS_TABLE, values, KEY_MODEL_ID + "=?", new String[]{mContents.getMid() + ""});
                Log.e("log", "content update : " + update);
            } else {
                long v = db.insert(DATABASE_CONTENTS_TABLE, null, values);
                Log.e("log", "content insert : " + v);

            }


        } catch (Exception e) {

        }

        cursor.close();
    }


    public ArrayList<MLevel> getLevelData() {
        ArrayList<MLevel> levelArrayList = new ArrayList<>();

        MLevel mLevel;
        String sql = "select * from " + DATABASE_LEVEL_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        Log.e("cursor", "count :" + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.e("do", "start");
                mLevel = new MLevel();
                mLevel.setLid(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
                mLevel.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                mLevel.setUpdate_date(cursor.getString(cursor.getColumnIndex(KEY_UPDATE_DATE)));
                mLevel.setTotal_slevel(cursor.getString(cursor.getColumnIndex(KEY_TOTAL_S_LEVEL)));
//                mLevel.setDifficulty(cursor.getInt(cursor.getColumnIndex(KEY_DIFFICULTY)));
//                mLevel.setBestpoint(cursor.getInt(cursor.getColumnIndex(KEY_BEST_POINT)));
                mLevel.setLevelWinCount(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_WIN_COUNT)));
                levelArrayList.add(mLevel);
                Log.e("do", "end");
            } while (cursor.moveToNext());
            cursor.close();
        }


        return levelArrayList;
    }

    public ArrayList<MSubLevel> getSubLevelData(int id) {
        ArrayList<MSubLevel> assetArrayList = new ArrayList<>();

        MSubLevel mSubLevel;
        String sql = "select * from " + DATABASE_SUB_LEVEL_TABLE + " where " + KEY_PARENT_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mSubLevel = new MSubLevel();
                mSubLevel.setLid(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
                mSubLevel.setParentId(cursor.getInt(cursor.getColumnIndex(KEY_PARENT_ID)));
                mSubLevel.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                mSubLevel.setCoins_price(cursor.getString(cursor.getColumnIndex(KEY_COINS_PRICE)));
                mSubLevel.setNo_of_coins(cursor.getString(cursor.getColumnIndex(KEY_NO_OF_COINS)));
                assetArrayList.add(mSubLevel);

            } while (cursor.moveToNext());
            cursor.close();
        }


        return assetArrayList;
    }

    public ArrayList<MContents> getContentsData() {
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
                mContents.setPresentType(cursor.getInt(cursor.getColumnIndex(KEY_PRESENT_TYPE)));
                assetArrayList.add(mContents);

            } while (cursor.moveToNext());
            cursor.close();
        }


        return assetArrayList;
    }
}

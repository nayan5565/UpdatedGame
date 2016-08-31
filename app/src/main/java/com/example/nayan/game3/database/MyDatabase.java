package com.example.nayan.game3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nayan.game3.model.MAsset;
import com.example.nayan.game3.model.MLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/24/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_LEVEL_TABLE = "level_table";
    private static final String DATABASE_ASSET_TABLE = "asset_table";

    private static final String KEY_ID = "id";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_COINS_PRICE = "coins_price";
    private static final String KEY_NO_OF_COINS = "no_of_coins";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LEVEL_ID = "level_id";
    private static final String KEY_HINTS = "hints";
    private static final String KEY_IMAGE = "images";
    private static final String KEY_SOUNDS = "sounds";
    private static final String KEY_PRESENT_ID = "present_id";
    private static final String KEY_PRESENT_TYPE = "present_type";
    private static final String KEY_BEST_POINT = "best_point";
    private static final String KEY_LEVEL_WIN_COUNT = "win_count";


    private static final String DATABASE_CREATE_LEVEL_TABLE = "create table " + DATABASE_LEVEL_TABLE + "("
            + KEY_ID + " integer, "
            + KEY_LEVEL + " text, "
            + KEY_COINS_PRICE + " text, "
            + KEY_DIFFICULTY + " integer, "
            + KEY_BEST_POINT + " integer, "
            + KEY_LEVEL_WIN_COUNT + " integer, "
            + KEY_NO_OF_COINS + " text)";
    private static final String DATABASE_CREATE_ASSET_TABLE = "create table " + DATABASE_ASSET_TABLE + "(" + KEY_LEVEL_ID + " integer, "
            + KEY_PRESENT_ID + " integer, "
            + KEY_PRESENT_TYPE + " integer, "
            + KEY_IMAGE + " text, "
            + KEY_HINTS + " text, "
            + KEY_SOUNDS + " text)";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_LEVEL_TABLE);
        db.execSQL(DATABASE_CREATE_ASSET_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_LEVEL_TABLE);
        db.execSQL("drop table if exists " + DATABASE_ASSET_TABLE);
        onCreate(db);

    }

    public void addLevelFromJson(MLevel mLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL, mLevel.getLevel());
            values.put(KEY_ID, mLevel.getId());
            values.put(KEY_COINS_PRICE, mLevel.getCoinPrice());
            values.put(KEY_DIFFICULTY, mLevel.getDifficulty());
            values.put(KEY_NO_OF_COINS, mLevel.getNoOfCoinPrice());
            values.put(KEY_BEST_POINT, mLevel.getBestpoint());
            values.put(KEY_LEVEL_WIN_COUNT, mLevel.getLevelWinCount());
            String sql = "select * from " + DATABASE_LEVEL_TABLE + " where " + KEY_ID + "='" + mLevel.getId() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_LEVEL_TABLE, values, KEY_ID + "=?", new String[]{mLevel.getId() + ""});
                Log.e("log", "update : " + update);
            } else {
                long v = db.insert(DATABASE_LEVEL_TABLE, null, values);
                Log.e("log", "return : " + v);

            }


            db.close();
        } catch (Exception e) {

        }

    }

    public void addAssetFromJson(MAsset mAsset) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL_ID, mAsset.getLevelId());
            values.put(KEY_IMAGE, mAsset.getImages());
            values.put(KEY_HINTS, mAsset.getHints());
            values.put(KEY_SOUNDS, mAsset.getSounds());
            values.put(KEY_PRESENT_ID, mAsset.getPresentId());
            values.put(KEY_PRESENT_TYPE, mAsset.getPresentType());

            String sql = "select * from " + DATABASE_ASSET_TABLE + " where " + KEY_LEVEL_ID + "='" + mAsset.getLevelId() + "' AND " + KEY_IMAGE + "='" + mAsset.getImages() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                int update = db.update(DATABASE_ASSET_TABLE, values, KEY_LEVEL_ID + "=? AND " + KEY_IMAGE + "=?", new String[]{mAsset.getLevelId() + "", mAsset.getImages()});
                Log.e("log", "Assetupdate : " + update);
            } else {
                long v = db.insert(DATABASE_ASSET_TABLE, null, values);
                Log.e("log", "Assetreturn : " + v);

            }


            db.close();
        } catch (Exception e) {

        }

    }


    public ArrayList<MLevel> getAllData(int diffculty) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MLevel> levelArrayList = new ArrayList<>();

        MLevel mLevel;
        String sql = "select * from " + DATABASE_LEVEL_TABLE + " where " + KEY_DIFFICULTY + "='" + diffculty + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mLevel = new MLevel();
                mLevel.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                mLevel.setLevel(cursor.getString(cursor.getColumnIndex(KEY_LEVEL)));
                mLevel.setCoinPrice(cursor.getString(cursor.getColumnIndex(KEY_COINS_PRICE)));
                mLevel.setNoOfCoinPrice(cursor.getString(cursor.getColumnIndex(KEY_NO_OF_COINS)));
                mLevel.setDifficulty(cursor.getInt(cursor.getColumnIndex(KEY_DIFFICULTY)));
                mLevel.setBestpoint(cursor.getInt(cursor.getColumnIndex(KEY_BEST_POINT)));
                mLevel.setLevelWinCount(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_WIN_COUNT)));
                levelArrayList.add(mLevel);

            } while (cursor.moveToNext());
        }

        db.close();


        return levelArrayList;
    }

    public ArrayList<MAsset> getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MAsset> assetArrayList = new ArrayList<>();

        MAsset mAsset;
        String sql = "select * from " + DATABASE_ASSET_TABLE + " where " + KEY_LEVEL_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mAsset = new MAsset();
                mAsset.setImages(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                mAsset.setSounds(cursor.getString(cursor.getColumnIndex(KEY_SOUNDS)));
                mAsset.setHints(cursor.getString(cursor.getColumnIndex(KEY_HINTS)));
                mAsset.setLevelId(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
                mAsset.setPresentId(cursor.getInt(cursor.getColumnIndex(KEY_PRESENT_ID)));
                mAsset.setPresentType(cursor.getInt(cursor.getColumnIndex(KEY_PRESENT_TYPE)));

                assetArrayList.add(mAsset);

            } while (cursor.moveToNext());
        }

        db.close();


        return assetArrayList;
    }
}

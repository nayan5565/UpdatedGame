package com.example.nayan.game3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nayan.game3.model.MLevel;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/24/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "game.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "level_table";

    public static final String KEY_ID = "id";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_COINS_PRICE = "coins_price";
    public static final String KEY_NO_OF_COINS = "no_of_coins";
    // public static final String KEY_HINTS = "hints";


    private static final String DATABASE_CREATE_TABLE = "create table " + DATABASE_TABLE + "(" + KEY_ID + " integer primary key autoincrement, " + KEY_LEVEL + " text, " + KEY_COINS_PRICE + " text, " + KEY_NO_OF_COINS + " text)";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_TABLE);
        onCreate(db);

    }

    public void addLevelFromJson(MLevel mLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LEVEL, mLevel.getLevel());
            values.put(KEY_COINS_PRICE, mLevel.getCoinPrice());

            values.put(KEY_NO_OF_COINS, mLevel.getNoOfCoinPrice());
            db.insert(DATABASE_TABLE, null, values);
            db.close();
        } catch (Exception e) {

        }

    }

    public ArrayList<MLevel> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MLevel> levelArrayList = new ArrayList<>();

        MLevel mLevel;
        String sql = "select * from " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.isAfterLast() == false) {
            mLevel = new MLevel();
            mLevel.setLevel(cursor.getString(cursor.getColumnIndex(KEY_LEVEL)));
            mLevel.setCoinPrice(cursor.getString(cursor.getColumnIndex(KEY_COINS_PRICE)));
            mLevel.setNoOfCoinPrice(cursor.getString(cursor.getColumnIndex(KEY_NO_OF_COINS)));
            levelArrayList.add(mLevel);
            cursor.moveToNext();
        }
        db.close();


        return levelArrayList;
    }
}

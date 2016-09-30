package com.example.nayan.game3.model;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/18/2016.
 */
public class MLevel {
    ArrayList<MSubLevel> mSubLevels = new ArrayList<>();
    //String eId;
    private int lid;
    private String coinPrice;
    private String noOfCoinPrice;
    private String image;
    private String name;
    private String sound;
    private String update_date;
    private int difficulty;
    private int type;
    private int status;
    private int bestpoint;
    private int levelWinCount;
    private String total_slevel;

    public String getTotal_slevel() {
        return total_slevel;
    }

    public void setTotal_slevel(String total_slevel) {
        this.total_slevel = total_slevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getLevelWinCount() {
        return levelWinCount;
    }

    public void setLevelWinCount(int levelWinCount) {
        this.levelWinCount = levelWinCount;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<MSubLevel> getmSubLevels() {
        return mSubLevels;
    }

    public void setmSubLevels(ArrayList<MSubLevel> mSubLevels) {
        this.mSubLevels = mSubLevels;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBestpoint() {
        return bestpoint;
    }

    public void setBestpoint(int bestpoint) {
        this.bestpoint = bestpoint;
    }
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(String coinPrice) {
        this.coinPrice = coinPrice;
    }

    public String getNoOfCoinPrice() {
        return noOfCoinPrice;
    }

    public void setNoOfCoinPrice(String noOfCoinPrice) {
        this.noOfCoinPrice = noOfCoinPrice;
    }
}

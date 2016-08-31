package com.example.nayan.game3.model;

import java.util.ArrayList;

/**
 * Created by NAYAN on 8/18/2016.
 */
public class MLevel {
    ArrayList<MAsset> asset = new ArrayList<>();
    //String eId;
    private String level;
    private String coinPrice;
    private String noOfCoinPrice;
    private String image;
    private String sound;
    private String hint;
    private int difficulty;
    private int id;
    private int type;
    private int status;
    private int bestpoint;
    private int levelWinCount;

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

    public ArrayList<MAsset> getAsset() {
        return asset;
    }

    public void setAsset(ArrayList<MAsset> asset) {
        this.asset = asset;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

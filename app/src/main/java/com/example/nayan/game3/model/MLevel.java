package com.example.nayan.game3.model;

/**
 * Created by ${NAYAN} on 8/18/2016.
 */
public class MLevel {
    String eId;
    String level;
    String coinPrice;
    String noOfCoinPrice;
    String image;
    String sound;
    int id,type,status,bestpoint;

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

    String hint;


    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
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

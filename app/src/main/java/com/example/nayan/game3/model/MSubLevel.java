package com.example.nayan.game3.model;

/**
 * Created by NAYAN on 9/26/2016.
 */

public class MSubLevel {
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    private int pId;
    private int lid;
    private String name;
    private String coins_price;

    public String getNo_of_coins() {
        return no_of_coins;
    }

    public void setNo_of_coins(String no_of_coins) {
        this.no_of_coins = no_of_coins;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoins_price() {
        return coins_price;
    }

    public void setCoins_price(String coins_price) {
        this.coins_price = coins_price;
    }

    private String no_of_coins;
}
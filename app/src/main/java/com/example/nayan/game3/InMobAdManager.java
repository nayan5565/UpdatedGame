package com.example.nayan.game3;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by NAYAN on 9/22/2016.
 */

public class InMobAdManager {
    private static InMobAdManager instance;

    public static InMobAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new InMobAdManager();
            MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713");
        }
        return instance;
    }

    public void loadAd(AdView mAdView){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}

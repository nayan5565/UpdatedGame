package com.example.nayan.game3.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nayan.game3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    public static final String PRE_FILE_NAME = "text";
    public static final String KEY_USER_LEARN_DRAWER="user_learn_drawer";
    public boolean mUserlearnedDrawe;
    public boolean mFromSaveInstanceState;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserlearnedDrawe=Boolean.valueOf(readFromPrefrence(getActivity(),KEY_USER_LEARN_DRAWER,"false"));
        if (savedInstanceState!=null){
            mFromSaveInstanceState=true;
        }
    }

    public static void saveToPrefarence(Context context, String prefranceName, String prefranceValue) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(prefranceName, prefranceValue);
        editor.apply();
    }

    public static String readFromPrefrence(Context context, String prefranceName, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(prefranceName, defaultValue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer2, container, false);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserlearnedDrawe){
                    mUserlearnedDrawe=true;
                    saveToPrefarence(getActivity(),KEY_USER_LEARN_DRAWER,mUserlearnedDrawe+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);
                }

            }
        };

        if (!mUserlearnedDrawe&&!mFromSaveInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(drawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
              drawerToggle.syncState();
            }
        });

    }

}

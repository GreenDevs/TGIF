package com.crackdeveloperz.tgif;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class NagivationDrawer extends Fragment {


    ActionBarDrawerToggle mActionBarDrawerToggle;
    DrawerLayout thisDrawerLayout;
    ImageView tumblr, twitter, google, insta, fb;





    public NagivationDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_nagivation_drawer, container, false);
        if(view!=null) view.setBackgroundColor(ColorsAndTitleForTheApp.currentToolBarColor);

        return view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity=getActivity();
        tumblr=(ImageView)activity.findViewById(R.id.tumblr_at_drawer);
        tumblr.setOnClickListener((WebActivity) activity);

        twitter=(ImageView)activity.findViewById(R.id.twitter_at_drawer);
        twitter.setOnClickListener((WebActivity)activity);

        google=(ImageView)activity.findViewById(R.id.googleplus_at_drawer);
        google.setOnClickListener((WebActivity)activity);

        insta=(ImageView)activity.findViewById(R.id.insta_at_drawer);
        insta.setOnClickListener((WebActivity)activity);

        fb=(ImageView)activity.findViewById(R.id.facebook_at_drawer);
        fb.setOnClickListener((WebActivity)activity);
    }

    public void setup(DrawerLayout mDrawerLayout, Toolbar mToolbar) {


        thisDrawerLayout  = mDrawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity() , mDrawerLayout ,mToolbar ,R.string.opened , R.string.closed){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }


        };

        thisDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        thisDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
    }


}

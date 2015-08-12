package com.crackdeveloperz.tgif;

import android.graphics.Color;


public class ColorsAndTitleForTheApp {

    public static final  String facebook = "FB";
    public static final  String twitter = "TW";
    public static final  String googlePlus = "GP";
    public static final  String tumblr = "TU";
    public static final  String instagram = "IN";



    public static  final int GOOGLE_PLUS_STATUSBAR = Color.rgb(215,65,52);
    public static  final int GOOGLE_PLUS_TOOLBAR = Color.rgb(220,68,55);

    public static  final int FACEBOOK_STATUSBAR = Color.rgb(58, 85, 148);
    public static  final int FACEBOOK_TOOLBAR = Color.rgb(60,90,152);

    public static  final int TWITTER_STATUSBAR = Color.rgb(55,137,196);
    public static  final int TWITTER_TOOLBAR = Color.rgb(60,142,201);

    public static  final int TUMBLR_STATUSBAR = Color.rgb(50,65,90);
    public static  final int TUMBLR_TOOLBAR = Color.rgb(54,70,93);

    public static  final int INSTA_STATUSBAR = Color.rgb(60,112,150);
    public static  final int INSTA_TOOLBAR = Color.rgb(63,115,154);

    public static final String FB_LINK = "https://m.facebook.com/";
    public static final String GP_LINK = "https://plus.google.com/";
    public static final String TW_LINK = "https://mobile.twitter.com/";
    public static final String IN_LINK = "https://instagram.com/";
    public static final String TU_LINK = "https://www.tumblr.com/dashboard/";


    public static final String STRING_FACEBOOK = "Facebook";
    public static final String STRING_GOOGLE_PLUS = "Google Plus";
    public static final String STRING_TWITTER = "Twitter";
    public static final String STRING_TUMBLR = "Tumblr";
    public static final String STRING_INSTAGRAM = "Instagram";



    public static int currentStatusBarColor =Color.rgb(88,5,5);
    public static int currentToolBarColor =Color.rgb(88,5,5);
    public static String currentTitle = "Facebook";
    public static String currentLink = "";


    public static String currentWebsite = ColorsAndTitleForTheApp.facebook;




   public static  void setWebSiteVariable (String c) {

       if (c.equals(ColorsAndTitleForTheApp.facebook)) {

           currentWebsite = c;

           currentStatusBarColor = FACEBOOK_STATUSBAR;
           currentToolBarColor = FACEBOOK_TOOLBAR;
           currentTitle = STRING_FACEBOOK;

           currentLink = FB_LINK;


       }

       if (c.equals(ColorsAndTitleForTheApp.twitter)) {

           currentWebsite = c;

           currentStatusBarColor = TWITTER_STATUSBAR;
           currentToolBarColor = TWITTER_TOOLBAR;
           currentTitle = STRING_TWITTER;

           currentLink = TW_LINK;


       }



       if (c.equals(ColorsAndTitleForTheApp.googlePlus)) {

           currentWebsite = c;

           currentStatusBarColor = GOOGLE_PLUS_STATUSBAR;
           currentToolBarColor = GOOGLE_PLUS_TOOLBAR;
           currentTitle = STRING_GOOGLE_PLUS;
                currentLink = GP_LINK;


       }


       if (c.equals(ColorsAndTitleForTheApp.tumblr)) {

           currentWebsite = c;

           currentStatusBarColor = TUMBLR_STATUSBAR;
           currentToolBarColor = TUMBLR_TOOLBAR;
           currentTitle = STRING_TUMBLR;

           currentLink = TU_LINK;


       }


       if (c.equals(ColorsAndTitleForTheApp.instagram)) {

           currentWebsite = c;

           currentStatusBarColor = INSTA_STATUSBAR;
           currentToolBarColor = INSTA_TOOLBAR;
           currentTitle = STRING_INSTAGRAM;

           currentLink = IN_LINK;


       }


    }





}

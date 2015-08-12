package com.crackdeveloperz.tgif.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

/**
 * Created by trees on 8/11/15.
 */
public class Utility
{
    public static boolean isNetAvailable  (Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void hideNagivation(Activity activity)
    {
        View decor_View = activity.getWindow().getDecorView();
        int ui_Options=0;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
        {
            ui_Options= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        else
        {
            ui_Options= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        decor_View.setSystemUiVisibility(ui_Options);
    }

}

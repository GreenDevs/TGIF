
package com.crackdeveloperz.tgif;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crackdeveloperz.tgif.utility.Utility;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ColorsAndTitleForTheApp.TUMBLR_STATUSBAR);
        }

    }

    public void launch(View v)
    {

            Intent intent = new Intent(this, WebActivity.class);
            switch (v.getId())
            {

                case R.id.fab_facebook :

                    ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.facebook);
                    startActivity(intent);
                    break;


                case R.id.fab_twitter:

                    ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.twitter);
                    startActivity(intent);
                    break;


                case R.id.fab_googlePlus:

                    ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.googlePlus);
                    startActivity(intent);

                    break;


                case R.id.fab_tumblr:

                    ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.tumblr);
                    startActivity(intent);
                    break;


                case R.id.fab_instagram:
                    ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.instagram);
                    startActivity(intent);
                    break;

                default:
                    break;

//            }


        }
        overridePendingTransition(R.anim.down_to_top, R.anim.static_anim);

    }


    @Override
    public void onBackPressed ()
    {
        Utility.hideNagivation(this);
        if ((System.currentTimeMillis()-WebActivity.timestamp[2]) <900){}
        else { super.onBackPressed();  }
    }


    @Override
    public void onResume ()
    {
        super.onResume();
        Utility.hideNagivation(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        Utility.hideNagivation(this);

    }



    ////FOR ABOUT US///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            startActivity(new Intent(this, AboutActivity.class));
            overridePendingTransition(R.anim.right_to_left, R.anim.static_anim);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}




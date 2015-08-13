package com.crackdeveloperz.tgif;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crackdeveloperz.tgif.utility.Utility;


public class AboutActivity extends ActionBarActivity implements View.OnClickListener
{


    private static final String FB_LINK="https://m.facebook.com/515289881959584";
    private static final String EMAIL="aaenriquez257@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        (findViewById(R.id.fblink)).setOnClickListener(this);
        (findViewById(R.id.emaillink)).setOnClickListener(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.about_toolbar);
         setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Utility.hideNagivation(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {

            return true;
        }

        if (id == android.R.id.home)
        {
            this.finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.emaillink:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", EMAIL, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                try
                {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                } catch (Exception e)
                {
                    Toast.makeText(this, "no email app found", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.fblink:


                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.facebook);
                ColorsAndTitleForTheApp.currentLink=FB_LINK;


                Intent intent = new Intent(this, WebActivity.class);


                try {
                    startActivity(intent);
                    overridePendingTransition(R.anim.down_to_top, R.anim.static_anim);
                    this.finish();
                } catch (Exception e) {
                    Toast.makeText(this, "link out of reach", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }

    }
}

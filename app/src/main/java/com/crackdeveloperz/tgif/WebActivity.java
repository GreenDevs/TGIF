package com.crackdeveloperz.tgif;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.crackdeveloperz.tgif.utility.Utility;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebActivity extends AppCompatActivity implements View.OnClickListener
{
    public int cancealCount = 2;
    public static long[] timestamp = {33, 33, 33};
    public static final String DATE_FORMAT="yyyyMMdd_HHmmSS";
    public static final int INPUT_FILE_REQUEST_CODE = 1;

    private WebView mWebView;
    private FloatingActionButton refreshFab;
    private String mCameraPhotoPath;
    NagivationDrawer mNagivationDrawer;
    private ValueCallback<Uri[]> mFilePathCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.web_app_bar);
        setSupportActionBar(toolbar);
        refreshFab=(FloatingActionButton)findViewById(R.id.refreshFabButton);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_for_webpage);
        mNagivationDrawer = (NagivationDrawer) getSupportFragmentManager().findFragmentById(R.id.nagivation_drawer);
        mNagivationDrawer.setup(mDrawerLayout, toolbar);
        mWebView = (WebView) findViewById(R.id.fragment_main_webview);
        setTheme();

        // set some properties of webview that we are going to use
        setUpWebViewDefaults(mWebView);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null)
        {
            // Restore the previous URL and history stack
            mWebView.restoreState(savedInstanceState);
        }


        mWebView.setWebChromeClient(new WebChromeClient()
        {
            // For Android 3.0+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg)
            {
                if (mFilePathCallback != null)
                {
                    mFilePathCallback.onReceiveValue(null);
                }

                mFilePathCallback = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), INPUT_FILE_REQUEST_CODE);
            }

            // For Android 3.0+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg, String acceptType)
            {
                if (mFilePathCallback != null)
                {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), INPUT_FILE_REQUEST_CODE);
            }


            // For Android 4.1
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg, String acceptType, String capture)
            {

                if (mFilePathCallback != null)
                {

                   mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");


                startActivityForResult(Intent.createChooser(i, "File Chooser"), WebActivity.INPUT_FILE_REQUEST_CODE);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (mFilePathCallback != null)
                {
                    mFilePathCallback.onReceiveValue(null);
                }

                // the url of multiple images that are being  called back .
                mFilePathCallback = filePathCallback;

                // intent to start camera
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    // Create the File where the photo should go
                    File photoFile = null;

                    try
                    {
                        photoFile = createImageFile();  // there is function to create an image below  which just creates a file with jpg format
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    }
                    catch (IOException ex)
                    {
                        // Error occurred while creating the File
                        // Log.e(TAG, "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null)
                    {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    }
                    else
                    {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if (takePictureIntent != null)
                {
                    intentArray = new Intent[]{takePictureIntent};
                }
                else
                {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

                return true;
            }
        });

        // Load the local index.html file
        if (mWebView.getUrl() == null)
        {
            mWebView.loadUrl(ColorsAndTitleForTheApp.currentLink);
        }


    }


    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName,  /* prefix */".jpg",/* suffix */ storageDir      /* directory */ );

    }

    /**
     * Convenience method to set some generic defaults for a
     * given WebView
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    private void setUpWebViewDefaults(WebView webView)
    {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        // settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
        {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // We set the WebViewClient to ensure links are consumed by the WebView rather
        // than passed to a browser if it can
        mWebView.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {

                refreshFab.setVisibility(View.VISIBLE);
                if (!mWebView.getUrl().equals("file:///android_asset/connection_fail.html"))
                {
                          ColorsAndTitleForTheApp.currentLink =mWebView.getUrl();
                }
                mWebView.loadUrl("file:///android_asset/connection_fail.html");
            }
         });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null)
        {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        // Check that the response is a good one
        if (resultCode == Activity.RESULT_OK)
        {
            if (data == null)
            {
                // If there is not data, then we may have taken a photo
                if (mCameraPhotoPath != null)
                {
                    results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                }

            }
            else
            {
                String dataString = data.getDataString();
                if (dataString != null)
                {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }

        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;


    }


    public void setTheme()
    {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ColorsAndTitleForTheApp.currentStatusBarColor);

        }

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(ColorsAndTitleForTheApp.currentToolBarColor));
            actionBar.setTitle(ColorsAndTitleForTheApp.currentTitle);

        }


    }


    @Override
    public void onResume()
    {
        super.onResume();
        Utility.hideNagivation(this);
    }


    @Override
    public void onBackPressed()
    {

        Utility.hideNagivation(this);


        if (shouldGoBackTOHome())
        {
            super.onBackPressed();
        }

        WebView mWebView = (WebView) findViewById(R.id.fragment_main_webview);
        if (mWebView.canGoBack())
        {
            mWebView.goBack();
            cancealCount = 3;

        }
        else
        {
            mWebView.scrollTo(0, 0);
            cancealCount--;
            if (cancealCount == 1)
            {

                Toast.makeText(getApplicationContext(), "Press Again To Exit", Toast.LENGTH_SHORT).show();
            }
            if (cancealCount < 1)
            {
                super.onBackPressed();
            }

        }

    }


    public boolean shouldGoBackTOHome()
    {
        boolean result = false;
        timestamp[0] = timestamp[1];
        timestamp[1] = timestamp[2];
        timestamp[2] = System.currentTimeMillis();
        if ((timestamp[2] - timestamp[0]) < 1200)
        {
            result = true;
        }

        return result;

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        Utility.hideNagivation(this);
    }


    ///ADDED BY SARKI TO KILL THE VIDEO PLAY IN WEB VEIW WHEN IT IS AWAY FROM THE VIEW///
    @Override
    protected void onPause()
    {
        super.onPause();
//            try
//            {
//                Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(mWebView, (Object[]) null);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
    }


    @Override
    public void onClick(View v)
    {
        Intent intent = getIntent();
        switch (v.getId())
        {

            case R.id.tumblr_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.tumblr);
                finish();
                startActivity(intent);
                break;

            case R.id.twitter_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.twitter);
                finish();
                startActivity(intent);
                break;

            case R.id.googleplus_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.googlePlus);
                finish();
                startActivity(intent);
                break;

            case R.id.insta_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.instagram);
                finish();
                startActivity(intent);
                break;

            case R.id.facebook_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.facebook);
                finish();
                startActivity(intent);
                break;

            default:
                break;

        }

        overridePendingTransition(R.anim.right_to_left, R.anim.static_anim);
    }


    public void refresh(View v)
    {
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vibrator.vibrate(70);

        mWebView.loadUrl(ColorsAndTitleForTheApp.currentLink);
        refreshFab.setVisibility(View.GONE);
    }


    ////FOR ABOUT US///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_up)
        {

            ///THIS CODE IS USE TO SCROLL WEB VIEW TO THE TOP OF IT BY ANIMATING IT
            if(mWebView!=null)
            {
                ObjectAnimator anim = ObjectAnimator.ofInt(mWebView, "scrollY", mWebView.getScrollY(), 0);
                anim.setDuration(500);
                anim.start();
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

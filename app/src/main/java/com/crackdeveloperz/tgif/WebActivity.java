package com.crackdeveloperz.tgif;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.crackdeveloperz.tgif.utility.GetExtenction;
import com.crackdeveloperz.tgif.utility.Utility;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebActivity extends AppCompatActivity implements View.OnClickListener  {
    private static boolean flag = false;

    private TextView progressText;
    private static final int tgif_count=5;
    private int tgif_progress_pos=0;
    public static long[] timestamp = {33, 33};
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmSS";
    public static final int INPUT_FILE_REQUEST_CODE = 1;

    private WebView mWebView;
    private FloatingActionButton refreshFab;
    private String mCameraPhotoPath;
    NagivationDrawer mNagivationDrawer;
    private ValueCallback<Uri[]> mFilePathCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        progressText=(TextView)findViewById(R.id.progressText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.web_app_bar);
        setSupportActionBar(toolbar);
        refreshFab = (FloatingActionButton) findViewById(R.id.refreshFabButton);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_for_webpage);
        mNagivationDrawer = (NagivationDrawer) getSupportFragmentManager().findFragmentById(R.id.nagivation_drawer);
        mNagivationDrawer.setup(mDrawerLayout, toolbar);
        mWebView = (WebView) findViewById(R.id.fragment_main_webview);


        setTheme();

        // set some properties of webview that we are going to use
        setUpWebViewDefaults(mWebView);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore the previous URL and history stack
            mWebView.restoreState(savedInstanceState);
        }


        setWebChromeChient();


        // Load the local index.html file
        if (mWebView.getUrl() == null) {
            mWebView.loadUrl(ColorsAndTitleForTheApp.currentLink);
        }


    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName,  /* prefix */".jpg",/* suffix */ storageDir      /* directory */);

    }

    /**
     * Convenience method to set some generic defaults for a
     * given WebView
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        // settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        // We set the WebViewClient to ensure links are consumed by the WebView rather
        // than passed to a browser if it can
        mWebView.setWebViewClient(new WebViewClient() {








            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // handle different requests for different type of files
                //fb ko view full size handa pic download huncha ;)

                // everything else the webview can handle normally
                if (GetExtenction.isValid(url)) { /*if url.endswith(".jpg") { // true && !(ColorsAndTitleForTheApp.currentLink.equals(ColorsAndTitleForTheApp.TU_LINK)/ */

                   Log.i("link " , url);
                    Uri source = Uri.parse(url);
                    // Make a new request pointing to the .apk url
                    DownloadManager.Request request = new DownloadManager.Request(source);
                    // appears the same in Notification bar while downloading
                    request.setDescription("Change Extenction if not image ");
                    request.setTitle(url);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    // save the file in the "Downloads" folder of SDCARD
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,    System.currentTimeMillis()+GetExtenction.a);
                    // get download service and enqueue file
                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }

                return false;
            }







            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                refreshFab.setBackgroundColor(ColorsAndTitleForTheApp.currentToolBarColor);
                refreshFab.setVisibility(View.VISIBLE);
                if (!mWebView.getUrl().equals("file:///android_asset/connection_fail.html")) {
                    ColorsAndTitleForTheApp.currentLink = mWebView.getUrl();
                }
                mWebView.loadUrl("file:///android_asset/connection_fail.html");


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                progressText.setText(".");
                progressText.setTextColor(getResources().getColor(R.color.google_plus_code));
                progressText.setVisibility(View.VISIBLE);

            }



            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                progressText.setVisibility(View.GONE);
            }


        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        // Check that the response is a good one
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                // If there is not data, then we may have taken a photo
                if (mCameraPhotoPath != null) {
                    results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                }

            } else {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }

        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;


    }


    public void setTheme() {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ColorsAndTitleForTheApp.currentStatusBarColor);

        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ColorsAndTitleForTheApp.currentToolBarColor));
            actionBar.setTitle(ColorsAndTitleForTheApp.currentTitle);

        }


    }


    @Override
    public void onResume() {
        super.onResume();
        Utility.hideNagivation(this);
    }


    @Override
    public void onBackPressed() {

        Utility.hideNagivation(this);
        boolean isNotAtHome = this.mWebView.canGoBack();
        boolean consecutivePress = isConsecutivePress();


        if (!isNotAtHome && !consecutivePress) {
            Toast.makeText(this, "Press again to exit ", Toast.LENGTH_SHORT).show();

        }


        else if (!isNotAtHome && consecutivePress) {
            mWebView.loadUrl("about:blank");
            finish();
            super.onBackPressed();

        }

        else if (isNotAtHome && !consecutivePress) {

           // mWebView.loadUrl("about:blank");
            mWebView.goBack();

        }


        else if (isNotAtHome && consecutivePress) {

           Intent i = getIntent();
            mWebView.loadUrl("about:blank");
            finish();
            startActivity(i);
        }


    }


    public boolean isConsecutivePress() {
        boolean result = false;
        timestamp[0] = timestamp[1];
        timestamp[1] = System.currentTimeMillis();

        if ((timestamp[1] - timestamp[0]) < 900) {
            result = true;
        }

        return result;

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utility.hideNagivation(this);
    }


    @Override
    public void onClick(View v) {
        vibrate(30);
        Intent intent = getIntent();
        switch (v.getId()) {

            case R.id.tumblr_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.tumblr);
                break;

            case R.id.twitter_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.twitter);
                break;

            case R.id.googleplus_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.googlePlus);
                break;

            case R.id.insta_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.instagram);
                break;

            case R.id.facebook_at_drawer:
                ColorsAndTitleForTheApp.setWebSiteVariable(ColorsAndTitleForTheApp.facebook);
                break;

            default:
                break;

        }
        if (mWebView != null) mWebView.loadUrl("about:blank");
        finish();
        startActivity(intent);

        overridePendingTransition(R.anim.right_to_left, R.anim.static_anim);
    }


    public void refresh(View v) {


        mWebView.loadUrl(ColorsAndTitleForTheApp.currentLink);

        vibrate(50);
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

        if (id == R.id.action_up) {
            vibrate(30);

            ///THIS CODE IS USE TO SCROLL WEB VIEW TO THE TOP OF IT BY ANIMATING IT
            if (mWebView != null) {
                ObjectAnimator anim = ObjectAnimator.ofInt(mWebView, "scrollY", mWebView.getScrollY(), 0);
                anim.setDuration(500);
                anim.start();
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void vibrate (int time) {

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(time);
    }



   public void  setWebChromeChient () {

        mWebView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg) {
                if (mFilePathCallback != null) {
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
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg, String acceptType) {
                if (mFilePathCallback != null) {
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
            public void openFileChooser(ValueCallback<Uri[]> uploadMsg, String acceptType, String capture) {

                if (mFilePathCallback != null) {

                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");


                startActivityForResult(Intent.createChooser(i, "File Chooser"), WebActivity.INPUT_FILE_REQUEST_CODE);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }

                // the url of multiple images that are being  called back .
                mFilePathCallback = filePathCallback;

                // intent to start camera
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;

                    try {
                        photoFile = createImageFile();  // there is function to create an image below  which just creates a file with jpg format
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        // Log.e(TAG, "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

                return true;
            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setProgressColor(tgif_progress_pos);
                tgif_progress_pos = (tgif_progress_pos + 1) % tgif_count;
            }

        });

    }


    private void setProgressColor(int pos)
    {
        Resources res=getResources();
        switch (pos)
        {
            case 0:
                progressText.setText(".");
                progressText.setTextColor(res.getColor(R.color.tumblr_code));
                break;
            case 1:
                progressText.setText("..");
                progressText.setTextColor(res.getColor(R.color.twitter_code));
                break;
            case 2:
                progressText.setText("...");
                progressText.setTextColor(res.getColor(R.color.google_plus_code));
                break;
            case 3:
                progressText.setText(".....");
                progressText.setTextColor(res.getColor(R.color.instagram_code));
                break;
            case 4:
                progressText.setText(".....");
                progressText.setTextColor(res.getColor(R.color.facebook_code));
                break;

            default:
                progressText.setText(".");
                progressText.setTextColor(res.getColor(R.color.tumblr_code));
                break;

        }
    }

}

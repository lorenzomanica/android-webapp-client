package br.pro.lmit.android.webappclient;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final String JS_TAG = "Native";

    private WebView mWebFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebFrame = findViewById(R.id.web_frame);

        setupWebFrame();

        mWebFrame.loadUrl(getString(R.string.default_url));
    }

    private void setupWebFrame() {
        WebSettings settings = mWebFrame.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebFrame.addJavascriptInterface(this, JS_TAG);
        mWebFrame.setWebViewClient(new RestrictedDomainWebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebFrame.canGoBack()) {
            mWebFrame.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @JavascriptInterface
    public void showAlert(@NonNull String msg) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .show();
    }

    @JavascriptInterface
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(takePictureIntent);
        }
    }

}

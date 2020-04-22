package br.pro.lmit.android.webappclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class RestrictedDomainWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        Context context = view.getContext();

        if (context.getString(R.string.app_host).equals(uri.getHost())
                || "file".equals(uri.getScheme())) {
            return false;
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            return true;
        }
    }
}

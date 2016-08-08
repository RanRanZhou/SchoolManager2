package com.zhou.schoolmanager.tabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import com.zhou.schoolmanager.R;


public class timeTab extends Fragment {
WebView webView;
    Context context;
    public timeTab() {
        //necessary empty constructor
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       context = getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View rootTab = inflater.inflate(R.layout.fragment_time_tab, container, false);
        webView = (WebView) rootTab.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.getSettings().setUseWideViewPort(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://nameless-earth-81447.herokuapp.com/");
        return rootTab;
    }

}

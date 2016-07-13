package com.zhou.schoolmanager.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

import com.zhou.schoolmanager.R;


public class timeTab extends Fragment {

    public timeTab() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View rootTab = inflater.inflate(R.layout.fragment_time_tab, container, false);

            WebView webView = (WebView)rootTab.findViewById(R.id.webView);
            webView.loadUrl("https://www.google.com/#gws_rd=ssl");
            webView.getSettings().setJavaScriptEnabled(true);

        return rootTab;
    }

}

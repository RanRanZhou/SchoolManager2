package com.zhou.schoolmanager.tabs;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.zhou.schoolmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class icTab extends Fragment {
Button languageArts,socialStudies,infiniteCampus,calculator,b4,b5;

    public icTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootTab = inflater.inflate(R.layout.fragment_ic_tab, container, false);
        languageArts = (Button) rootTab.findViewById(R.id.LArts);
        socialStudies = (Button) rootTab.findViewById(R.id.aHistory);
        infiniteCampus = (Button) rootTab.findViewById(R.id.infiniteCampus);
        calculator = (Button) rootTab.findViewById(R.id.b3);
        b4 = (Button) rootTab.findViewById(R.id.b4);
        b5 = (Button) rootTab.findViewById(R.id.b5);
        //button styling
        languageArts.setText("Language Arts");
        socialStudies.setText("American History");
        infiniteCampus.setText("Infinite Campus");
        calculator.setText("Calcula-tor");
        languageArts.setBackgroundColor(Color.GRAY);
        socialStudies.setBackgroundColor(Color.GRAY);
        infiniteCampus.setBackgroundColor(Color.GRAY);
        calculator.setBackgroundColor(Color.GRAY);
        b4.setBackgroundColor(Color.GRAY);
        b5.setBackgroundColor(Color.GRAY);
        languageArts.setTextColor(Color.WHITE);
        socialStudies.setTextColor(Color.WHITE);
        infiniteCampus.setTextColor(Color.WHITE);
        //on click listeners net.openvpn.openvpn/net.openvpn.openvpn.OpenVPNClient
        languageArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Uri uri = Uri.parse("http://tinyurl.com/eliasonla");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
            }
        });
        socialStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://tinyurl.com/mrsadan");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        infiniteCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://campus.norman.k12.ok.us/campus/norman.jsp");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName("net.openvpn.openvpn","net.openvpn.openvpn.OpenVPNClient");
                startActivity(i);
            }
        });
        return rootTab;
    }


}

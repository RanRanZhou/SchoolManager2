package com.zhou.schoolmanager.tabs;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhou.schoolmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class linksTab extends Fragment {
Button button1, button2, button3, button4, button5, button6;

    public linksTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootTab = inflater.inflate(R.layout.fragment_links_tab, container, false);

        button1 = (Button) rootTab.findViewById(R.id.button1);
        button2 = (Button) rootTab.findViewById(R.id.button2);
        button3 = (Button) rootTab.findViewById(R.id.button3);
        button4 = (Button) rootTab.findViewById(R.id.button4);
        button5 = (Button) rootTab.findViewById(R.id.button5);
        button6 = (Button) rootTab.findViewById(R.id.button6);
        //button styling
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");

        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.GRAY);
        button3.setBackgroundColor(Color.GRAY);
        button4.setBackgroundColor(Color.GRAY);
        button5.setBackgroundColor(Color.GRAY);
        button6.setBackgroundColor(Color.GRAY);

        button1.setTextColor(Color.WHITE);
        button2.setTextColor(Color.WHITE);
        button3.setTextColor(Color.WHITE);
        button4.setTextColor(Color.WHITE);
        button5.setTextColor(Color.WHITE);
        button6.setTextColor(Color.WHITE);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Uri uri = Uri.parse("");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        return rootTab;
    }


}

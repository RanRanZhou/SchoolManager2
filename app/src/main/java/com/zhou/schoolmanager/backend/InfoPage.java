package com.zhou.schoolmanager.backend;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.zhou.schoolmanager.R;

import org.w3c.dom.Text;

public class InfoPage extends Activity {
TextView infoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        infoText = (TextView) findViewById(R.id.info_text);
        infoText.setText ("App developed by Shengran Zhou.\nFeedback and bug reports should be sent to shengran@gmail.com");
    }
}

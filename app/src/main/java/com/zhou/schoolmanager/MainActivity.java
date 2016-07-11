package com.zhou.schoolmanager;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zhou.schoolmanager.SQL.TaskContract;
import com.zhou.schoolmanager.SQL.TaskDBHelper;
import com.zhou.schoolmanager.tabs.tabManager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    com.zhou.schoolmanager.tabs.tabManager tabManager;
    ViewPager pager;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_tab);

        tabManager = new tabManager(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(tabManager);
        final ActionBar actionBar = getActionBar();

        pager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                }
        );


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Period").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Homework").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Classes").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Resources").setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
    public void taskDelete(View view) {

        View v = (View) view.getParent();
        checkBox = (CheckBox) v.findViewById(R.id.doneButton);
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();
        if(checkBox.isChecked()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                    TaskContract.TABLE,
                    TaskContract.Columns.TASK,
                    task);


            TaskDBHelper helper = new TaskDBHelper(this);
            SQLiteDatabase sqlDB = helper.getWritableDatabase();
            sqlDB.execSQL(sql);
            Log.e("tag", "checked");
            checkBox.setEnabled(false);
        }


    }
}

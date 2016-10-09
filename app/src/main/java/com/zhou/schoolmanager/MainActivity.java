package com.zhou.schoolmanager;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zhou.schoolmanager.SQL.MyDBHelper;
import com.zhou.schoolmanager.SQL.TaskContract;
import com.zhou.schoolmanager.backend.InfoPage;
import com.zhou.schoolmanager.tabs.tabManager;

public class MainActivity extends AppCompatActivity {
    com.zhou.schoolmanager.tabs.tabManager tabManager;
    ViewPager pager;
    CheckBox checkBox;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        tabManager = new tabManager(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(tabManager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return (super.onCreateOptionsMenu(menu));
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.infopage: {
                Intent intent = new Intent(this, InfoPage.class);
                startActivity(intent);
            }
            return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    //deleting sqltasks in the homework tab
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


            MyDBHelper helper = new MyDBHelper(this,null,null,1);
            SQLiteDatabase sqlDB = helper.getWritableDatabase();
            sqlDB.execSQL(sql);
            Log.e("tag", "checked");
            checkBox.setEnabled(false);
        }


    }
}

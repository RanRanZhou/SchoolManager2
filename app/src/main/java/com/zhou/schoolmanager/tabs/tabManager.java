package com.zhou.schoolmanager.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ranran on 8/13/2015.
 */
public class tabManager extends FragmentPagerAdapter {
    public tabManager(FragmentManager fm) {
        super(fm);
    }

    @Override

    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new timeTab();
            case 1:
                return new homeworkTab();
            case 2:
                return new classTab();

            default:
                break;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Period";
                case 1:
                    return "Homework";
                case 2:
                    return "Classes";
                default:
                    return null;
            }

    }

    @Override
    public int getCount() {
        return 3;
    }
}

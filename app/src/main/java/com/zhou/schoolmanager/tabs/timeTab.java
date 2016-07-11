package com.zhou.schoolmanager.tabs;


import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhou.schoolmanager.R;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class timeTab extends Fragment {
    TextView displayedText;
    TextView dayCounter;
    int period;
    int timeLeft;
    int hour;
    int minute;
    int second;
    Calendar thatDay;
    String nextperiod;
    String displayTime;
    int time;
    Timer timer;
    public Calendar c;
    //zero hour
    int p0A = 730;
    int p0B = 820;
    //first
    int p1A = 835;
    int p1B = 925;
    //second
    int p2A = 929;
    int p2B = 1019;
    //third
    int p3A = 1023;
    int p3B = 1113;
    //fourth
    int p4A = 1117;
    int p4B = 1207;
    //fifth
    int p5A = 1211;
    int p5B = 1301;
    //sixth
    int p6A = 1305;
    int p6B = 1355;
    //seventh
    int p7A = 1359;
    int p7B  = 1449;
    //eighth
    int p8A = 1454;
    int p8B = 1540;


    public timeTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         timer = new Timer();

        thatDay = Calendar.getInstance(TimeZone.getDefault());
        thatDay.set(Calendar.DAY_OF_MONTH, 26);
        thatDay.set(Calendar.MONTH, 4);
        thatDay.set(Calendar.YEAR, 2016);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_time_tab, container, false);
        displayedText = (TextView) rootView.findViewById(R.id.displayText);
        displayedText.setTextSize(30);
        dayCounter = (TextView) rootView.findViewById(R.id.dayCounter);
        dayCounter.setTextSize(30);
        refresh();
        return rootView;
    }
    public void nextPeriod(){
        if(period == 0) {nextperiod = "Orchestra at 8:35";}
        if(period == 1) {nextperiod = "Language arts at 9:29";}
        if(period == 2) {nextperiod = "Social Studies at 10:23";}
        if(period == 3) {nextperiod = "Health at 11:17";}
        if(period == 4) {nextperiod = "Lunch at 12:11";}
        if(period == 5) {nextperiod = "Science at 1:05";}
        if(period == 6) {nextperiod = "Math at 1:59";}
        if(period == 7) {nextperiod = "Eighth at 2:54";}
        if(period == 8) {nextperiod = "Biology at 7:30";}
    }
    public void refresh() {
        Log.e("refresh", "refreshed");
        //simple method to make editing messages for the nextperiod string easier
        nextPeriod();

        //refresh times
        c = Calendar.getInstance(TimeZone.getDefault());
        minute = c.get(Calendar.MINUTE); // minute in an hour
        hour = c.get(Calendar.HOUR_OF_DAY); //24 hour time
        second = 60- c.get(Calendar.SECOND); // seconds in minute

        int hour12 = c.get(Calendar.HOUR);   //used for displaying time that people would look at

        //converts the milliseconds til to the of school to days
        long millisecondsUntilOver = thatDay.getTimeInMillis() - c.getTimeInMillis();
        long daysLeft = TimeUnit.MILLISECONDS.toDays(millisecondsUntilOver);

       //converts the minutes into double digit format (01,02...)
        String mMinute = String.format("%02d", minute);
        displayTime = hour +""+ mMinute;
        time = Integer.parseInt(displayTime);
       // if(c.get(Calendar.DAY_OF_WEEK) != c.get(Calendar.SATURDAY) ||c.get(Calendar.DAY_OF_WEEK) != c.get(Calendar.SUNDAY) ) {
            //Zero 7:30-8:20
            if (time >= p0A && time <= p0B) {
                period = 0;
                if (hour == 7) {
                    timeLeft = p0B - time - 40;
                } else {
                    timeLeft = p0B - time;
                }
                Log.e("hour", "zero hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft + " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }
            //First 8:35 - 9:25
            else if (time >= p1A && time <= p1B) {
                period = 1;
                if (hour == 8) {
                    timeLeft = p1B - time - 40;
                } else {
                    timeLeft = p1B - time;
                }
                Log.e("hour", "first hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft + " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }
            //Second 9:29-10:19
            else if (time >= p2A && time <= p2B) {
                period = 2;
                if (hour == 9) {
                    timeLeft = p2B - time - 40;
                } else {
                    timeLeft = p2B - time;
                }
                Log.e("hour", "second hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft+ " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }//Third 10:23-11:13
            else if (time >= p3A && time <= p3B) {
                period = 3;
                if (hour == 10) {
                    timeLeft = p3B - time - 40;
                } else {
                    timeLeft = p3B - time;
                }
                Log.e("hour", "third hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft+ " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }//Fourth 11:17-12:07
            else if (time >= p4A && time <= p4B) {
                period = 4;
                if (hour == 11) {
                    timeLeft = p4B - time - 40;
                } else {
                    timeLeft = p4B - time;
                }
                Log.e("hour", "fourth hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft+ " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }//Fifth 12:11 - 1:01
            else if (time >= p5A && time <= p5B) {
                period = 5;
                if (hour == 12) {
                    timeLeft = p5B - time - 40;

                } else {
                    timeLeft = p5B - time;
                }
                Log.e("hour", "fifth hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft+ " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }//Sixth 1:05 - 1:55
            else if (time >= p6A && time <= p6B) {
                period = 6;
//                if (hour == 13) {
//                    timeLeft = p6B - time - 40;
//                } else {
//
//                }
                timeLeft = p6B - time;
                Log.e("hour", "sixth hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft + " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }//Seventh 1:59-2:49
            else if (time >= p7A && time <= p7B) {
                period = 7;
                if (hour == 13) {
                    timeLeft = p7B - time - 40;
                } else {
                    timeLeft = p7B - time;
                }
                Log.e("hour", "seventh hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft+ " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }//Eighth 2:54 - 3:40
            else if (time >= p8A && time <= p8B) {
                period = 8;
                if (hour == 14) {
                    timeLeft = p8B - time - 40;
                } else {
                    timeLeft = p8B - time;
                }
                Log.e("hour", "eighth hour");
                displayedText.setText("the current period is:" + period + "\n" + "there are " + timeLeft+ " minutes left" + "\n" + "next period:" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            } else if (time > p8B || time < p0A) {
                if(c.get(Calendar.AM_PM) == Calendar.AM) {
                    displayTime = hour12 +":"+ mMinute + " AM";
                }
                else {
                    displayTime = hour12 + ":" + mMinute + " PM";
                }
                displayedText.setText("School starts at 7:30 AM \n it is " + displayTime + "\n" + "" + time);
                dayCounter.setText(daysLeft + " days left");
            } else {

                displayedText.setText("passing period" + "\n" + nextperiod);
                dayCounter.setText(daysLeft + " days left");
            }




    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            refresh(); //this is the textview
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();

            }
        }, 0, 5000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}

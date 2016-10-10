package com.zhou.schoolmanager.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.zhou.schoolmanager.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class timeTab extends Fragment {
    SimpleDateFormat format;
    TextView timeView;
    TextView daysLeft;
    Timer t;
    boolean freshman;
    Calendar endSchool;
    Calendar calendar;
    public timeTab() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootTab = inflater.inflate(R.layout.fragment_time_tab, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("mypreferences", Context.MODE_PRIVATE);

        boolean isFirstUsage = prefs.getBoolean("first_usage", true);
        String isFreshmanAnswered = prefs.getString("isFreshmanAnswered", "");
        Log.e("tag", String.valueOf(isFirstUsage));

        if (isFirstUsage && isFreshmanAnswered.equals("")) {
            //"Yes" button code
            Button YesButton = (Button) rootTab.findViewById(R.id.YesButton);
            YesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putBoolean("isFreshman", true);
                    editor.putString("isFreshmanAnswered", "");
                    editor.apply();
                    reload();
                    Toast.makeText(getActivity(), "Settings can be changed with the menu on the top right", Toast.LENGTH_SHORT).show();
                }
            });


            //"No" button code
            Button NoButton = (Button) rootTab.findViewById(R.id.NoButton);
            NoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putBoolean("isFreshman", false);
                    editor.putString("isFreshmanAnswered", "");
                    editor.apply();
                    reload();
                    Toast.makeText(getActivity(), "Settings can be changed with the menu on the top right", Toast.LENGTH_SHORT).show();
                }
            });

            // save preferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_usage", false);
            editor.apply();
        } else {
            rootTab = inflater.inflate(R.layout.fragment_display_tab, container, false);
            timeView = (TextView) rootTab.findViewById(R.id.displayTime);
            format = new SimpleDateFormat("HH:mm:ss", Locale.US);
            freshman = prefs.getBoolean("isFreshman", true);
            t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh(format, timeView, freshman);
                            }
                        });
                    }
                }
            }, 0, 1000);
            endSchool = Calendar.getInstance();
            endSchool.set(Calendar.DAY_OF_MONTH, 25);
            endSchool.set(Calendar.MONTH, 4);
            endSchool.set(Calendar.YEAR, 2017);

            calendar = Calendar.getInstance();

            long diff = endSchool.getTimeInMillis() - calendar.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            daysLeft = (TextView) rootTab.findViewById(R.id.daysLeft);
            daysLeft.setText(String.valueOf(days) + " Days Left");
        }


        return rootTab;
    }

    public boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
            boolean valid = false;
            //Start Time
            java.util.Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            java.util.Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            java.util.Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }

    }

    public void refresh(SimpleDateFormat format, TextView timeView, boolean freshman) {
        String strTime = format.format(new Date());


        calendar = Calendar.getInstance();
        try {
            //USUAL FRESHMAN SCHEDULE
            boolean schoolHours = isTimeBetweenTwoTime(Hours.fstart0, Hours.fend7, strTime);
            if (freshman && schoolHours) {
                if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {

                    if (isTimeBetweenTwoTime(Hours.fstart0, Hours.fend0, strTime)) {
                        //Zero Hour
                        refreshVars(Hours.fend0, strTime, "0");

                    }  else if (isTimeBetweenTwoTime(Hours.fstartSTRETCH, Hours.fendSTRETCH, strTime)) {
                        //STRETCH
                        refreshVars(Hours.fendSTRETCH, strTime, "STRETCH");

                    } else if (isTimeBetweenTwoTime(Hours.fendSTRETCH, Hours.fstart1, strTime)) {
                        //PP
                        refreshVars(Hours.fstart1, strTime, "passing period");

                    } else if (isTimeBetweenTwoTime(Hours.fstart1, Hours.fend1, strTime)) {
                        //First Hour
                        refreshVars(Hours.fend1, strTime, "1st");

                    } else if (isTimeBetweenTwoTime(Hours.fend1, Hours.fstart2, strTime)) {
                        //PP
                        refreshVars(Hours.fstart2, strTime, "Passing Period");

                    } else if (isTimeBetweenTwoTime(Hours.fstart2, Hours.fend2, strTime)) {
                        //Second Hour
                        refreshVars(Hours.fend2, strTime, "2nd");

                    } else if (isTimeBetweenTwoTime(Hours.fstart3, Hours.fend3, strTime)) {
                        //Third Hour
                        refreshVars(Hours.fend3, strTime, "3rd");

                    } else if (isTimeBetweenTwoTime(Hours.fstart4, Hours.fend4, strTime)) {
                        //Fourth Hour
                        refreshVars(Hours.fend4, strTime, "4th");

                    } else if (isTimeBetweenTwoTime(Hours.fend4, Hours.fstart5, strTime)) {
                       //PP
                        refreshVars(Hours.fstart5, strTime, "Passing Period");

                    } else if (isTimeBetweenTwoTime(Hours.fstart5, Hours.fend5, strTime)) {
                        //Fifth Hour
                        refreshVars(Hours.fend5, strTime, "5th");

                    } else if (isTimeBetweenTwoTime(Hours.fend5, Hours.fstart6, strTime)) {
                        //PP
                        refreshVars(Hours.fstart6, strTime, "Passing Period");

                    } else if (isTimeBetweenTwoTime(Hours.fstart6, Hours.fend6, strTime)) {
                        //Sixth Hour
                        refreshVars(Hours.fend6, strTime, "6th");

                    } else if (isTimeBetweenTwoTime(Hours.fend6, Hours.fstart7, strTime)) {
                        //PP
                        refreshVars(Hours.fstart7, strTime, "Passing Period");

                    } else if (isTimeBetweenTwoTime(Hours.fstart7, Hours.fend7, strTime)) {
                        //Seventh Hour
                        refreshVars(Hours.fend7, strTime, "7th");
                    } else {
                        timeView.setText("Passing Period");
                    }
                    //FRESHMAN STARS
                } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    if (isTimeBetweenTwoTime(Hours.fstart0, Hours.fend0, strTime)) {
                        //Zero Hour
                        refreshVars(Hours.fend0, strTime, "0 hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstartSTRETCH, Hours.fendSTRETCH, strTime)) {
                        //STRETCH
                        refreshVars(Hours.fendSTRETCH, strTime, "STRETCH");
                    } else if (isTimeBetweenTwoTime(Hours.fstart1STARS, Hours.fend1STARS, strTime)) {
                        //First Hour
                        refreshVars(Hours.fend1STARS, strTime, "1st hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstartTTS, Hours.fendTTS, strTime)) {
                        //STARS
                        refreshVars(Hours.fendTTS, strTime, "STARS hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstart2STARS, Hours.fend2STARS, strTime)) {
                        //Second Hour
                        refreshVars(Hours.fend2STARS, strTime, "2nd hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstart3STARS, Hours.fend3STARS, strTime)) {
                        //Third Hour
                        refreshVars(Hours.fend3STARS, strTime, "3rd hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstart4STARS, Hours.fend4STARS, strTime)) {
                        //Fourth Hour
                        refreshVars(Hours.fend4STARS, strTime, "4th hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstart5STARS, Hours.fend5STARS, strTime)) {
                        //Fifth Hour
                        refreshVars(Hours.fend5STARS, strTime, "5th hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstartTTS2, Hours.fendTTS2, strTime)) {
                        //STARS
                        refreshVars(Hours.fendTTS2, strTime, "STARS");
                    } else if (isTimeBetweenTwoTime(Hours.fstart6STARS, Hours.fend6STARS, strTime)) {
                        //Sixth Hour
                        refreshVars(Hours.fend6STARS, strTime, "6th hour");
                    } else if (isTimeBetweenTwoTime(Hours.fstart7STARS, Hours.fend7STARS, strTime)) {
                        //Seventh Hour
                        refreshVars(Hours.fend7STARS, strTime, "7th hour");
                    } else {
                        timeView.setText("Passing Period");
                    }
                }
            } else if (!freshman && schoolHours) {
                //USUAL UPPERCLASSMAN SCHEDULE
                if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
                    if (isTimeBetweenTwoTime(Hours.ustart0, Hours.uend0, strTime)) {
                        //Zero Hour
                        refreshVars(Hours.uend0, strTime, "0 hour");
                    } else if (isTimeBetweenTwoTime(Hours.uend0, Hours.ustartSTRETCH, strTime)) {
                        //PP
                        refreshVars(Hours.ustartSTRETCH, strTime, "passing period");

                    } else if (isTimeBetweenTwoTime(Hours.ustartSTRETCH, Hours.uendSTRETCH, strTime)) {
                        //STRETCH
                        refreshVars(Hours.uendSTRETCH, strTime, "STRETCH");
                    } else if (isTimeBetweenTwoTime(Hours.ustart1, Hours.uend1, strTime)) {
                        //First Hour
                        refreshVars(Hours.uend1, strTime, "1st hour");
                    } else if (isTimeBetweenTwoTime(Hours.uend1, Hours.ustart2, strTime)) {
                        //PP
                        refreshVars(Hours.ustart2, strTime, "passing period");

                    } else if (isTimeBetweenTwoTime(Hours.ustart2, Hours.uend2, strTime)) {
                        //Second Hour
                        refreshVars(Hours.uend2, strTime, "2nd hour");
                    } else if (isTimeBetweenTwoTime(Hours.uend2, Hours.ustart3, strTime)) {
                        //PP
                        refreshVars(Hours.ustart3, strTime, "passing period");

                    } else if (isTimeBetweenTwoTime(Hours.ustart3, Hours.uend3, strTime)) {
                        //Third Hour
                        refreshVars(Hours.uend3, strTime, "3rd hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustart4, Hours.uend4, strTime)) {
                        //Lunch
                        refreshVars(Hours.uend4, strTime, "4th hour");
                    }  else if (isTimeBetweenTwoTime(Hours.ustart5, Hours.uend5, strTime)) {
                        //Fifth Hour
                        refreshVars(Hours.uend5, strTime, "5th hour");
                    } else if (isTimeBetweenTwoTime(Hours.uend5, Hours.ustart6, strTime)) {
                        //PP
                        refreshVars(Hours.ustart6, strTime, "passing period");

                    } else if (isTimeBetweenTwoTime(Hours.ustart6, Hours.uend6, strTime)) {
                        //Sixth Hour
                        refreshVars(Hours.uend6, strTime, "6th hour");
                    } else if (isTimeBetweenTwoTime(Hours.uend6, Hours.ustart7, strTime)) {
                        //PP
                        refreshVars(Hours.ustart7, strTime, "passing period");

                    } else if (isTimeBetweenTwoTime(Hours.ustart7, Hours.uend7, strTime)) {
                        //Seventh Hour
                        refreshVars(Hours.uend7, strTime, "7th hour");
                    } else {
                        timeView.setText("Passing Period");

                    }
                }
                //UPPERCLASSMAN STARS
                else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    if (isTimeBetweenTwoTime(Hours.ustart0, Hours.uend0, strTime)) {
                        refreshVars(Hours.uend0, strTime, "0 hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustartSTRETCH, Hours.uendSTRETCH, strTime)) {
                        refreshVars(Hours.uendSTRETCH, strTime, "STRETCH");
                    } else if (isTimeBetweenTwoTime(Hours.ustart1STARS, Hours.uend1STARS, strTime)) {
                        refreshVars(Hours.uend1STARS, strTime, "1st hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustartTTS, Hours.uendTTS, strTime)) {
                        refreshVars(Hours.uendTTS, strTime, "STARS");
                    } else if (isTimeBetweenTwoTime(Hours.ustart2STARS, Hours.uend2STARS, strTime)) {
                        refreshVars(Hours.uend2STARS, strTime, "2nd hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustart3STARS, Hours.uend3STARS, strTime)) {
                        refreshVars(Hours.uend3STARS, strTime, "3rd hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustart4STARS, Hours.uend4STARS, strTime)) {
                        refreshVars(Hours.uend4STARS, strTime, "4th hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustart5STARS, Hours.uend5STARS, strTime)) {
                        refreshVars(Hours.uend5STARS, strTime, "5th hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustartTTS2, Hours.uendTTS2, strTime)) {
                        refreshVars(Hours.uendTTS2, strTime, "STARS");
                    } else if (isTimeBetweenTwoTime(Hours.ustart6STARS, Hours.uend6STARS, strTime)) {
                        refreshVars(Hours.uend6STARS, strTime, "6th hour");
                    } else if (isTimeBetweenTwoTime(Hours.ustart7STARS, Hours.uend7STARS, strTime)) {
                        refreshVars(Hours.uend7STARS, strTime, "7th hour");
                    } else {
                        timeView.setText("Passing Period");

                    }
                }


            } else {
                timeView.setText("The Current Time is" + "\n" + strTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void refreshVars(String endHour, String strTime, String currentHour) {
        try {
            Date timeLeft;
            Date currentTime = format.parse(strTime);

            long diff;
            long diffMinutes;
            long diffSeconds;

            String displayTimeLeft;

            timeLeft = format.parse(endHour);
            diff = timeLeft.getTime() - currentTime.getTime();
            diffMinutes = diff / (60 * 1000);
            diffSeconds = diff / 1000 % 60;
            displayTimeLeft =
                    String.valueOf(diffMinutes) + ":" + String.format("%02d", diffSeconds) +
                            "\n" +
                            "Minutes left in " + currentHour;
            SpannableString s = new SpannableString(displayTimeLeft);
            s.setSpan(new RelativeSizeSpan(2.5f), 0, 5, 0);
            timeView.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_time_tab, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.resetFreshman: {
                SharedPreferences prefs = getActivity().getSharedPreferences("mypreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("isFreshmanAnswered", "");
                editor.putBoolean("first_usage", true);
                editor.apply();
                reload();
            }
            return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void reload() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (t != null) {
            t.cancel();
        }
    }

    class Hours {
        //Freshman school hours
        private static final String fstart0 = "07:35:00";
        private static final String fend0 = "08:40:00";

        private static final String fstartSTRETCH = "08:40:00";
        private static final String fendSTRETCH = "08:55:00";

        private static final String fstart1 = "09:00:00";
        private static final String fend1 = "10:00:00";

        private static final String fstart2 = "10:08:00";
        private static final String fend2 = "11:04:00";

        private static final String fstart3 = "11:04:00";
        private static final String fend3 = "11:50:00";

        private static final String fstart4 = "11:59:00";
        private static final String fend4 = "12:54:00";

        private static final String fstart5 = "13:02:00";
        private static final String fend5 = "13:58:00";

        private static final String fstart6 = "14:06:00";
        private static final String fend6 = "15:02:00";

        private static final String fstart7 = "15:10:00";
        private static final String fend7 = "16:05:00";

        //Upperclassmen Hours

        private static final String ustart0 = "07:35:00";
        private static final String uend0 = "08:40:00";

        private static final String ustartSTRETCH = "08:40:00";
        private static final String uendSTRETCH = "08:55:00";

        private static final String ustart1 = "09:00:00";
        private static final String uend1 = "10:00:00";

        private static final String ustart2 = "10:08:00";
        private static final String uend2 = "11:04:00";

        private static final String ustart3 = "11:12:00";
        private static final String uend3 = "12:07:00";

        private static final String ustart4 = "12:07:00";
        private static final String uend4 = "13:02:00";

        private static final String ustart5 = "13:02:00";
        private static final String uend5 = "13:58:00";

        private static final String ustart6 = "14:06:00";
        private static final String uend6 = "15:02:00";

        private static final String ustart7 = "15:10:00";
        private static final String uend7 = "16:05:00";


        //STARS SCHEDULE
        private static final String fstart1STARS = "09:00:00";
        private static final String fend1STARS = "09:48:00";

        private static final String fstartTTS = "09:56:00";
        private static final String fendTTS = "10:21:00";

        private static final String fstart2STARS = "10:29:00";
        private static final String fend2STARS = "11:14:00";

        private static final String fstart3STARS = "11:14:00";
        private static final String fend3STARS = "11:56:00";

        private static final String fstart4STARS = "12:04:00";
        private static final String fend4STARS = "12:55:00";

        private static final String fstart5STARS = "13:03:00";
        private static final String fend5STARS = "13:48:00";

        private static final String fstartTTS2 = "13:56:00";
        private static final String fendTTS2 = "14:20:00";

        private static final String fstart6STARS = "14:28:00";
        private static final String fend6STARS = "15:13:00";

        private static final String fstart7STARS = "15:21:00";
        private static final String fend7STARS = "16:05:00";
        //STARS UPPERCLASSMEN
        private static final String ustart1STARS = "09:00:00";
        private static final String uend1STARS = "09:48:00";

        private static final String ustartTTS = "09:56:00";
        private static final String uendTTS = "10:21:00";

        private static final String ustart2STARS = "10:29:00";
        private static final String uend2STARS = "11:14:00";

        private static final String ustart3STARS = "11:22:00";
        private static final String uend3STARS = "12:13:00";

        private static final String ustart4STARS = "12:13:00";
        private static final String uend4STARS = "13:03:00";

        private static final String ustart5STARS = "13:03:00";
        private static final String uend5STARS = "13:48:00";

        private static final String ustartTTS2 = "13:56:00";
        private static final String uendTTS2 = "14:20:00";

        private static final String ustart6STARS = "14:28:00";
        private static final String uend6STARS = "15:13:00";

        private static final String ustart7STARS = "15:21:00";
        private static final String uend7STARS = "16:05:00";


        /*
        private final String[] fclassesbeg = new String[] {"07:35:00", "08:41:00", "09:00:00", "10:08:00", "11:04:00", "11:59:00", "13:02:00", "14:06:00", "15:10:00"};
        private final String[] fclassesend = new String[] {"08:40:00", "08:55:00", "10:00:00", "11:04:00", "11:50:00", "12:54:00", "13:58:00", "15:02:00", "16:05:00"};
         */

        //PASSING PERIOD FRESHMAN

        //PASSING PERIOD UPPERCLASSMEN
    }
}

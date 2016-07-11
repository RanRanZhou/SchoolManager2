package com.zhou.schoolmanager.backend;

import android.widget.Button;

/**
 * Created by Shengran on 8/19/2015.
 */
public class Subject {
   public String subject;
    public String teacher;
    public String time;



    public Subject(String subject,String teacher, String time) {
        super();
   this.subject = subject;
        this.teacher = teacher;
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }
}

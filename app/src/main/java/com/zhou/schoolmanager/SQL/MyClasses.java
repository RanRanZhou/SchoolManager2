package com.zhou.schoolmanager.SQL;

/**
 * Created by Shengran on 8/18/2016.
 */
public class MyClasses {

    private int _id;
    private String _teachername;
    private String _subjectname;
    private String _roomname;

    public MyClasses() {

    }

    public MyClasses(String subjectname, String teachername, String roomname) {
        this._teachername = teachername;
        this._subjectname = subjectname;
        this._roomname = roomname;
    }

    public int get_id() {
        return _id;
    }

    public String get_teachername() {
        return _teachername;
    }

    public String get_subjectname() {
        return _subjectname;
    }

    public String get_roomname() {
        return _roomname;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_teachername(String _teachername) {
        this._teachername = _teachername;
    }

    public void set_subjectname(String _subjectname) {
        this._subjectname = _subjectname;
    }

    public void set_roomname(String _roomname) {
        this._roomname = _roomname;
    }
}

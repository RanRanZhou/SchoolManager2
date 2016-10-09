package com.zhou.schoolmanager.SQL;

/**
 * Created by Shengran on 8/17/2015.
 */
import android.provider.BaseColumns;

public class TaskContract {
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }
}
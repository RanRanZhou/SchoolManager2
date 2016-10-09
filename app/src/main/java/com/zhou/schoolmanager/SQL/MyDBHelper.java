
package com.zhou.schoolmanager.SQL;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "classes.db";
    public static  final String TABLE_CLASSES = "myclasses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECTNAME = "subjectname";
    public static final String COLUMN_TEACHERNAME = "teachername";
    public static final String COLUMN_ROOMNAME = "roomname";

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_CLASSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SUBJECTNAME + " TEXT," +
                COLUMN_TEACHERNAME + " TEXT," +
                COLUMN_ROOMNAME + " TEXT " +
                ");";
        String sqlQuery =
                String.format("CREATE TABLE IF NOT EXISTS %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT)", TaskContract.TABLE,
                        TaskContract.Columns.TASK);
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CLASSES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TaskContract.TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addClass(MyClasses myclass) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(COLUMN_SUBJECTNAME, myclass.get_subjectname());
        values.put(COLUMN_TEACHERNAME, myclass.get_teachername());
        values.put(COLUMN_ROOMNAME, myclass.get_roomname());
        SQLiteDatabase db = getWritableDatabase();
        db.insertWithOnConflict(TABLE_CLASSES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }
    public void deleteClass(String className) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CLASSES + " WHERE " + COLUMN_SUBJECTNAME + "=\"" + className + "\";" );
        db.close();
    }

    public void editClass (String newClassName, String teacher, String room, String oldClassName) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(COLUMN_SUBJECTNAME, newClassName);
        values.put(COLUMN_TEACHERNAME, teacher);
        values.put(COLUMN_ROOMNAME, room);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_CLASSES, values,COLUMN_SUBJECTNAME + "=\"" + oldClassName + "\"", null);
        db.close();

    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLASSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}

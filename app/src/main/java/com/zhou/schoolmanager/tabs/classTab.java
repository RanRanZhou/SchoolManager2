package com.zhou.schoolmanager.tabs;


import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;

import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.schoolmanager.R;
import com.zhou.schoolmanager.SQL.MyClasses;
import com.zhou.schoolmanager.SQL.MyDBHelper;
import com.zhou.schoolmanager.backend.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class classTab extends ListFragment {
    AlertDialog.Builder builder;
    MyDBHelper helper;

    public classTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        setHasOptionsMenu(true);
        helper = new MyDBHelper(getActivity(), null, null, 1);
        updateClasses();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_class_tab, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.addButton: {
                builder.setTitle("Add a Class");
                builder.setCancelable(true);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.addclassdialog, null);
                builder.setView(dialogView);

                final EditText subjectText = (EditText) dialogView.findViewById(R.id.subjectEditText);
                final EditText teacherText = (EditText) dialogView.findViewById(R.id.teacherEditText);
                final EditText roomText = (EditText) dialogView.findViewById(R.id.roomEditText);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyClasses myclass = new MyClasses(
                                subjectText.getText().toString(),
                                teacherText.getText().toString(),
                                roomText.getText().toString());

                        helper.addClass(myclass);
                        Log.e("tag", String.valueOf(myclass.get_id()));
                        updateClasses();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.show();

                return true;
            }


            case R.id.deleteButton: {

                deleteAll();

            }


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        Log.e("tag",String.valueOf(position));
//        Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
//
//        String subjectName = cursor.getString(cursor.getColumnIndexOrThrow(helper.COLUMN_SUBJECTNAME));
//        helper.deleteClass(subjectName);
//        updateClasses();
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.class_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.deleteListItem:
                Log.e("tag", String.valueOf(info.position));
                Cursor cursor = (Cursor) getListView().getItemAtPosition(info.position);
                final String subjectName = cursor.getString(cursor.getColumnIndexOrThrow(helper.COLUMN_SUBJECTNAME));
                helper.deleteClass(subjectName);
                updateClasses();
                cursor.close();
                return true;
            case R.id.EditListItem:
                builder.setTitle("Edit a Class");
                builder.setCancelable(true);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.addclassdialog, null);
                builder.setView(dialogView);

                final EditText subjectText = (EditText) dialogView.findViewById(R.id.subjectEditText);
                final EditText teacherText = (EditText) dialogView.findViewById(R.id.teacherEditText);
                final EditText roomText = (EditText) dialogView.findViewById(R.id.roomEditText);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cursor cursor = (Cursor) getListView().getItemAtPosition(info.position);
                        helper.editClass(subjectText.getText().toString(),teacherText.getText().toString(),roomText.getText().toString(),cursor.getString(cursor.getColumnIndexOrThrow(helper.COLUMN_SUBJECTNAME)));
                        updateClasses();

                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    public void updateClasses() {
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(helper.TABLE_CLASSES,
                new String[]{helper.COLUMN_ID, helper.COLUMN_SUBJECTNAME, helper.COLUMN_TEACHERNAME, helper.COLUMN_ROOMNAME},
                null, null, null, null, null);

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.subject_view,
                cursor,
                new String[]{helper.COLUMN_SUBJECTNAME, helper.COLUMN_TEACHERNAME, helper.COLUMN_ROOMNAME},
                new int[]{R.id.subject, R.id.teacher, R.id.classroom},
                0
        );

        this.setListAdapter(listAdapter);

    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(helper.TABLE_CLASSES, null, null);
        updateClasses();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        registerForContextMenu(getListView());
        super.onActivityCreated(savedInstanceState);
    }
}

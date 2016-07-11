package com.zhou.schoolmanager.tabs;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.zhou.schoolmanager.SQL.TaskContract;
import com.zhou.schoolmanager.SQL.TaskDBHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeworkTab extends ListFragment implements AdapterView.OnItemSelectedListener {
    Button button;
    String subject;
    Button doneButton;
    AlertDialog.Builder builder;
    private TaskDBHelper helper;
    public homeworkTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        updateTasks();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootTab = inflater.inflate(R.layout.fragment_homework_tab, container, false);
        button = (Button) rootTab.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                buttonClick();

            }
        });
        doneButton = (Button) rootTab.findViewById(R.id.refreshButton);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                updateTasks();

            }
        });

        return rootTab;
    }

    public void buttonClick() {
        builder.setTitle("new homework");
        builder.setCancelable(true);

        LinearLayout layout = new TableLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        final Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.subjects, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        final EditText details = new EditText(getActivity());

        layout.addView(spinner);
        layout.addView(details);
        builder.setView(layout);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helper = new TaskDBHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.Columns.TASK,subject + details.getText().toString());

                db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                updateTasks();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();


    }
    private void updateTasks() {
        helper = new TaskDBHelper(getActivity());
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

         ListAdapter listAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.task_view,
                cursor,
                new String[] { TaskContract.Columns.TASK},
                new int[] { R.id.taskTextView},
                0
        );
        this.setListAdapter(listAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject =  parent.getItemAtPosition(position).toString() + ": ";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
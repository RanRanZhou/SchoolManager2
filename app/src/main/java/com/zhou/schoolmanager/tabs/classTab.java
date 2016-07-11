package com.zhou.schoolmanager.tabs;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.schoolmanager.R;
import com.zhou.schoolmanager.backend.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class classTab extends ListFragment {
    View rootTab;
    ListView list;
    private final List<Subject> myClasses = new ArrayList<>();
    public classTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myClasses.add(new Subject("Biology","Ms.Hendrick","7:30"));
        myClasses.add(new Subject("Orchestra","Ms.Kirtley","8:35"));
        myClasses.add(new Subject("Language Arts","Mrs.Eliason","9:29"));
        myClasses.add(new Subject("Social Studies","Mrs.Adan","10:23"));
        myClasses.add(new Subject("Health","Mr.Munday","11:17"));
        myClasses.add(new Subject("Lunch","Alice Grady","12:11"));
        myClasses.add(new Subject("Science","Mr.Mccoy","1:05"));
        myClasses.add(new Subject("Math","Mr.Dial","1:59"));
        myClasses.add(new Subject("Science Olympiad", "Mr.Mccoy", "2:54"));
    }

    //10:23,11:17,12:11,1:05,1:59,2:54
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootTab = inflater.inflate(R.layout.fragment_class_tab, container, false);

        ArrayAdapter<Subject> adapter = new MyListAdapter();
        list = (ListView) rootTab.findViewById(android.R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
               String test = parent.getItemAtPosition(position).toString();
                Log.e("test",test);
            }
        });
        return rootTab;
    }




        private class MyListAdapter extends ArrayAdapter<Subject> {

        public MyListAdapter() {
            super(getActivity(),R.layout.subject_view,myClasses);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.subject_view, parent, false);
            }


            Subject temp = myClasses.get(position);

            // subject:
            TextView subject = (TextView) itemView.findViewById(R.id.textView);
            subject.setText(temp.getSubject());

            // time:
            TextView time = (TextView) itemView.findViewById(R.id.classTime);
            time.setText(temp.getTime());

            // teacher:
            TextView teacher = (TextView) itemView.findViewById(R.id.teacher);
            teacher.setText(temp.getTeacher());



            itemView.setBackgroundColor(Color.BLACK);
            return itemView;
        }
    }
}


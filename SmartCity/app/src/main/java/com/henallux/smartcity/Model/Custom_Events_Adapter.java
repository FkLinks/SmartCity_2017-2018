package com.henallux.smartcity.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henallux.smartcity.Model.Event;
import com.henallux.smartcity.R;

import java.util.ArrayList;

public class Custom_Events_Adapter extends ArrayAdapter<Event> {
    public Custom_Events_Adapter(@NonNull Context context, @NonNull ArrayList<Event> list_home_menu) {
        super(context, R.layout.list_view_home_menu, list_home_menu);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View shapeOfList = layout.inflate(R.layout.list_view_events_listing, viewGroup, false);
        Event line_menu = getItem(position);
        TextView nameChoice = (TextView) shapeOfList.findViewById(R.id.choiceEventListView);
        nameChoice.setText(line_menu.getName());
        TextView dateChoice = (TextView) shapeOfList.findViewById(R.id.eventDateListView);

        String[] DateWOHourStart = line_menu.getStartTime().split("T");
        String[] DateWOHourEnd = line_menu.getEndTime().split("T");
        dateChoice.setText(DateWOHourStart[0] + " - " + ((!(line_menu.getEndTime()==null))? DateWOHourEnd[0]: "..."));
        return shapeOfList;
    }
}

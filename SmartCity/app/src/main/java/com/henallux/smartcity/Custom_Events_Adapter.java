package com.henallux.smartcity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henallux.smartcity.Model.Event;

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
        dateChoice.setText(line_menu.getStartTime() + ((!line_menu.getEndTime().equals(""))?" - " + line_menu.getEndTime(): ""));
        return shapeOfList;
    }
}

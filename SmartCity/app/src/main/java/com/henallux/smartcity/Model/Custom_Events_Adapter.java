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
        super(context, R.layout.list_view_events_listing, list_home_menu);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View shapeOfList = layout.inflate(R.layout.list_view_events_listing, viewGroup, false);
        Event event = getItem(position);

        TextView nameChoice = shapeOfList.findViewById(R.id.choiceEventListView);
        nameChoice.setText(event.getName());

        TextView dateChoice = shapeOfList.findViewById(R.id.eventDateListView);

        String[] DateWOHourStart = event.getStartTime().split("T");
        String DateWOHourEnd = ((!(event.getEndTime()==null))? event.getEndTime().split("T")[0]: "...");

        dateChoice.setText(DateWOHourStart[0] + " - " + DateWOHourEnd);

        return shapeOfList;
    }
}

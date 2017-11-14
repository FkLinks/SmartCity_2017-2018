package com.henallux.smartcity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_Home_Adapter extends ArrayAdapter<String> {
    public Custom_Home_Adapter(@NonNull Context context, @NonNull ArrayList<String> list_home_menu) {
        super(context, R.layout.list_view_home_menu, list_home_menu);
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View shapeOfList = layout.inflate(R.layout.list_view_home_menu, viewGroup, false);
        String line_menu = getItem(position);
        TextView nameChoice = (TextView) shapeOfList.findViewById(R.id.txtChoixListView);
        nameChoice.setText(line_menu);
        return shapeOfList;
    }
}

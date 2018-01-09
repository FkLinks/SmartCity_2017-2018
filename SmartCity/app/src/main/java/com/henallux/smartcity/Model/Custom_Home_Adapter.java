package com.henallux.smartcity.Model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henallux.smartcity.R;

import java.util.ArrayList;

public class Custom_Home_Adapter extends ArrayAdapter<String> {
    public Custom_Home_Adapter(@NonNull Context context, @NonNull ArrayList<String> list_home_menu) {
        super(context, R.layout.list_view_home_menu, list_home_menu);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View shapeOfList = layout.inflate(R.layout.list_view_home_menu, viewGroup, false);
        String choice = getItem(position);
        TextView nameChoice = shapeOfList.findViewById(R.id.txtChoixListView);
        nameChoice.setText(choice);
        return shapeOfList;
    }
}

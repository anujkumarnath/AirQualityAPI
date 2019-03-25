package com.androiddreams.airquality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<DataItem> {
    ArrayList<DataItem> dataItems;
    public DataAdapter(Context context, ArrayList<DataItem> dataItems) {
        super(context, 0, dataItems);
        this.dataItems = dataItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
        }

        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        tvLocation.setText(dataItems.get(position).getmLocation());

        TextView tvCity = convertView.findViewById(R.id.tvCity);
        tvCity.setText(dataItems.get(position).getmCity());

        TextView tvCO = convertView.findViewById(R.id.tvCO);
        tvCO.setText(dataItems.get(position).getmCOcontent());

        TextView tvPM10 = convertView.findViewById(R.id.tvPM10);
        tvPM10.setText(dataItems.get(position).getmPM10content());

        TextView tvPM25 = convertView.findViewById(R.id.tvPM25);
        tvPM25.setText(dataItems.get(position).getmPM25content());

        TextView tvSO2 = convertView.findViewById(R.id.tvSO2);
        tvSO2.setText(dataItems.get(position).getmSO2content());

        TextView tvO3 = convertView.findViewById(R.id.tvO3);
        tvO3.setText(dataItems.get(position).getmO3content());

        return convertView;
    }
}

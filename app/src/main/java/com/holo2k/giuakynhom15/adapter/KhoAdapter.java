package com.holo2k.giuakynhom15.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holo2k.giuakynhom15.R;
import com.holo2k.giuakynhom15.model.Kho;

import java.util.ArrayList;

public class KhoAdapter extends ArrayAdapter<Kho> {
    Context context;
    int resource;
    ArrayList<Kho> khoArrayList;

    public KhoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Kho> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.khoArrayList = objects;
    }

    @Override
    public int getCount() {
        return this.khoArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvMaKho = convertView.findViewById(R.id.tvMaKho);
        TextView tvTenKho = convertView.findViewById(R.id.tvTenKho);
        Kho kho = khoArrayList.get(position);
        tvMaKho.setText(String.valueOf(kho.getMaKho()));
        tvTenKho.setText(kho.getTenKho());
        return convertView;
    }
}

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
import java.util.List;

public class KhoItemSniperAdapter extends ArrayAdapter<Kho> {

    public KhoItemSniperAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Kho> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kho_sniper, parent, false);
        TextView tvItemKhoSniper = convertView.findViewById(R.id.tvItemKhoSniper);
        Kho kho = this.getItem(position);
        if (kho != null){
            tvItemKhoSniper.setText(kho.getTenKho());
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelected);
        Kho kho = this.getItem(position);
        if (kho != null){
            tvSelected.setText(kho.getTenKho());
        }
        return convertView;
    }
}

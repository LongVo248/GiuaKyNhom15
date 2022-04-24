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
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;

import java.util.ArrayList;

public class ThemPhieuAdapter extends ArrayAdapter<ChiTietPhieuNhap> {
    Context context;
    int resource;
    ArrayList<ChiTietPhieuNhap> themPhieuNhapArrayList;
    public ThemPhieuAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietPhieuNhap> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.themPhieuNhapArrayList = objects;
    }

    @Override
    public int getCount() {
        return this.themPhieuNhapArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvMaTVThemPhieu = convertView.findViewById(R.id.tvMaVTThemPhieu);
        TextView tvTenTVThemPhieu = convertView.findViewById(R.id.tvTenVTThemPhieu);
        TextView tvSLVTThemPhieu = convertView.findViewById(R.id.tvSLVTThemPhieu);
        ChiTietPhieuNhap chiTietPhieuNhap = themPhieuNhapArrayList.get(position);
        tvMaTVThemPhieu.setText(String.valueOf(chiTietPhieuNhap.getMaVatTu()));
        tvSLVTThemPhieu.setText(String.valueOf(chiTietPhieuNhap.getSoLuong()));
        return convertView;
    }

}
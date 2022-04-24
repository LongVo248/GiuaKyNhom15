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
import com.holo2k.giuakynhom15.model.PhieuNhap;

import java.util.ArrayList;

public class DSPhieuNhapAdapter extends ArrayAdapter<PhieuNhap> {
    Context context;
    int resource;
    ArrayList<PhieuNhap> phieuNhapArrayList;
    public DSPhieuNhapAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhieuNhap> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.phieuNhapArrayList = objects;
    }

    @Override
    public int getCount() {
        return this.phieuNhapArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvMaPhieu = convertView.findViewById(R.id.tvMaVTPhieu);
        TextView tvTenKhoPhieu = convertView.findViewById(R.id.tvTenKhoPhieu);
        TextView tvNgayNhap = convertView.findViewById(R.id.tvNgaynhap);
        PhieuNhap phieuNhap = phieuNhapArrayList.get(position);
        tvMaPhieu.setText(String.valueOf(phieuNhap.getMaPhieuNhap()));
        tvTenKhoPhieu.setText(phieuNhap.getMaKho());
        tvNgayNhap.setText(String.valueOf(phieuNhap.getNgayNhapPhieu()));
        return convertView;
    }

}
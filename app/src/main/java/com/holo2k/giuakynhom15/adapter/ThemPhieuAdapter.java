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
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class ThemPhieuAdapter extends ArrayAdapter<VatTuPhieuNhap> {
    Context context;
    int resource;
    ArrayList<VatTuPhieuNhap> vatTuPhieuNhapArrayList;

    public ThemPhieuAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VatTuPhieuNhap> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.vatTuPhieuNhapArrayList = objects;
    }


    @Override
    public int getCount() {
        return this.vatTuPhieuNhapArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvMaTVThemPhieu = convertView.findViewById(R.id.tvMaVTThemPhieu);
        TextView tvTenTVThemPhieu = convertView.findViewById(R.id.tvTenVTThemPhieu);
        TextView tvSLVTThemPhieu = convertView.findViewById(R.id.tvSLVTThemPhieu);
        TextView tvDVVTThemPhieu = convertView.findViewById(R.id.tvDVVTThemPhieu);
        VatTuPhieuNhap vatTuPhieuNhap = vatTuPhieuNhapArrayList.get(position);
        tvMaTVThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getMaVT()));
        tvSLVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getsL()));
        tvTenTVThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getTenVT()));
        tvDVVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getdV()));
        return convertView;
    }

}
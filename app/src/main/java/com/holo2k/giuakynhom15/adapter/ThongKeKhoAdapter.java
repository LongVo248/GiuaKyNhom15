package com.holo2k.giuakynhom15.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holo2k.giuakynhom15.ActivityChiTietPhieuNhap;
import com.holo2k.giuakynhom15.ActivityPhieuNhap;
import com.holo2k.giuakynhom15.ActivityThemPhieuNhap;
import com.holo2k.giuakynhom15.MainActivity;
import com.holo2k.giuakynhom15.R;
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class ThongKeKhoAdapter extends ArrayAdapter<VatTuPhieuNhap> {
    Context context;
    int resource;
    ArrayList<VatTuPhieuNhap> vatTuPhieuNhapArrayList;

    public ThongKeKhoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VatTuPhieuNhap> objects) {
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
        TextView tvMaVTThongKe = convertView.findViewById(R.id.tvMaVTThongKe);
        TextView tvTenVTThongKe = convertView.findViewById(R.id.tvTenVTThongKe);
        TextView tvSLVTThongKe = convertView.findViewById(R.id.tvSLVTThongKe);
        TextView tvDVVTThongke = convertView.findViewById(R.id.tvDVVTThongKe);
        VatTuPhieuNhap vatTuPhieuNhap = vatTuPhieuNhapArrayList.get(position);
        tvMaVTThongKe.setText(String.valueOf(vatTuPhieuNhap.getMaVT()));
        tvTenVTThongKe.setText(String.valueOf(vatTuPhieuNhap.getTenVT()));
        tvDVVTThongke.setText(String.valueOf(vatTuPhieuNhap.getdV()));
        tvSLVTThongKe.setText(String.valueOf(vatTuPhieuNhap.getsL()));


        return convertView;
    }

}
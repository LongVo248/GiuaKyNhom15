package com.holo2k.giuakynhom15.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holo2k.giuakynhom15.ActivityThemPhieuNhap;
import com.holo2k.giuakynhom15.R;
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class VatTuPhieuNhapAdapter extends ArrayAdapter<VatTuPhieuNhap> {
    Context context;
    int resource;
    ArrayList<VatTuPhieuNhap> vatTuPhieuNhapArrayList;

    public VatTuPhieuNhapAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VatTuPhieuNhap> objects) {
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
        TextView tvMaVTThemPhieu = convertView.findViewById(R.id.tvMaVTThemPhieu);
        TextView tvTenVTThemPhieu = convertView.findViewById(R.id.tvTenVTThemPhieu);
        EditText editSLVTThemPhieu = convertView.findViewById(R.id.editSLVTThemPhieu);
        EditText editDVVTThemPhieu = convertView.findViewById(R.id.editDVVTThemPhieu);
        VatTuPhieuNhap vatTuPhieuNhap = vatTuPhieuNhapArrayList.get(position);
        tvMaVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getMaVT()));
        tvTenVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getTenVT()));
        editDVVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getdV()));
        editSLVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getsL()));
        editSLVTThemPhieu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityThemPhieuNhap.vatTuPhieuNhaps.get(position).setsL(editSLVTThemPhieu.getText().toString());
            }
        });
        editDVVTThemPhieu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ActivityThemPhieuNhap.vatTuPhieuNhaps.get(position).setdV(editDVVTThemPhieu.getText().toString());
            }
        });
        return convertView;
    }

}
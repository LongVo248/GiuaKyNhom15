package com.holo2k.giuakynhom15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holo2k.giuakynhom15.ActivityThemPhieuNhap;
import com.holo2k.giuakynhom15.MainActivity;
import com.holo2k.giuakynhom15.R;
import com.holo2k.giuakynhom15.model.VatTu;

import java.util.ArrayList;

public class ChonVatTuThemPhieuAdapter extends ArrayAdapter<VatTu> {
    Context context;
    int resource;
    ArrayList<VatTu> vatTuArrayList;


    public ChonVatTuThemPhieuAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VatTu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.vatTuArrayList = objects;
    }

    @Override
    public int getCount() {
        return this.vatTuArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvMaVatTu = convertView.findViewById(R.id.tvMaVatTuCheckBox);
        TextView tvTenVatTu = convertView.findViewById(R.id.tvTenVatTuCheckBox);
        TextView tvXuatXu = convertView.findViewById(R.id.tvXuatXuCheckBox);
        ImageView imgHinhAnhVatTuCBThemPhieu = convertView.findViewById(R.id.imgHinhVatTuCheckBox);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()) {
                    ActivityThemPhieuNhap.viTriCB.add(vatTuArrayList.get(position));
                }
            }
        });
        VatTu vatTu = vatTuArrayList.get(position);
        tvMaVatTu.setText(String.valueOf(vatTu.getMaVatTu()));
        tvTenVatTu.setText(vatTu.getTenVatTu());
        tvXuatXu.setText(vatTu.getXuatXu());
        imgHinhAnhVatTuCBThemPhieu.setImageBitmap(MainActivity.chuyenByteSangHinhAnh(vatTu.getUri(), context));
        return convertView;
    }
}

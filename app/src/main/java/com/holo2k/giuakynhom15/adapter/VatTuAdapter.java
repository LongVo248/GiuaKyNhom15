package com.holo2k.giuakynhom15.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import com.holo2k.giuakynhom15.MainActivity;
import com.holo2k.giuakynhom15.R;
import com.holo2k.giuakynhom15.model.VatTu;

import java.util.ArrayList;

public class VatTuAdapter extends ArrayAdapter<VatTu> {
    Context context;
    int resource;
    ArrayList<VatTu> vatTuArrayList;


    public VatTuAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VatTu> objects) {
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
        TextView tvMaVatTu = convertView.findViewById(R.id.tvMaVatTu);
        TextView tvTenVatTu = convertView.findViewById(R.id.tvTenVatTu);
        TextView tvXuatXu = convertView.findViewById(R.id.tvXuatXu);
        ImageView imgHinhAnhVT = convertView.findViewById(R.id.imgVatTuShow);
        VatTu vatTu = vatTuArrayList.get(position);
        tvMaVatTu.setText(String.valueOf(vatTu.getMaVatTu()));
        tvTenVatTu.setText(vatTu.getTenVatTu());
        tvXuatXu.setText(vatTu.getXuatXu());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            context.getContentResolver().takePersistableUriPermission(vatTu.getUri(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        }
        imgHinhAnhVT.setImageBitmap(MainActivity.chuyenByteSangHinhAnh(vatTu.getUri(), context));
        return convertView;
    }
}

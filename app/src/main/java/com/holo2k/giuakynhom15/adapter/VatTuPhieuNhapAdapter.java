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
        ImageView imgLoaiBoVatTu = convertView.findViewById(R.id.imgLoaiBoVatTu);
        tvMaVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getMaVT()));
        tvTenVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getTenVT()));
        editDVVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getdV()));
        editSLVTThemPhieu.setText(String.valueOf(vatTuPhieuNhap.getsL()));
        imgLoaiBoVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VatTu vatTu = MainActivity.dbVatTu.getVatTu(vatTuPhieuNhap.getMaVT());
                if (ActivityPhieuNhap.clickThemChiTietPhieuNhap == true) {
                    ActivityThemPhieuNhap.vatTuPhieuNhaps.remove(vatTuPhieuNhap);
                    ActivityThemPhieuNhap.vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(context, R.layout.item_vat_tu_them_phieu, ActivityThemPhieuNhap.vatTuPhieuNhaps);
                    ActivityThemPhieuNhap.lvDSVatTuPhieu.setAdapter(ActivityThemPhieuNhap.vatTuPhieuNhapAdapter);

                    ActivityThemPhieuNhap.vatTus.add(vatTu);
                }
                if (ActivityPhieuNhap.clickChiTietPhieuNhap == true) {
                    ActivityChiTietPhieuNhap.vatTuPhieuNhaps.remove(vatTuPhieuNhap);
                    MainActivity.dbVatTu.deleteChiTietPhieuNhap(ActivityChiTietPhieuNhap.phieuNhap.getMaPhieuNhap(), vatTu.getMaVatTu());
                    ActivityChiTietPhieuNhap.vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(context, R.layout.item_vat_tu_them_phieu, ActivityChiTietPhieuNhap.vatTuPhieuNhaps);
                    ActivityChiTietPhieuNhap.lvDSVatTuChiTietPhieu.setAdapter(ActivityChiTietPhieuNhap.vatTuPhieuNhapAdapter);

                    ActivityChiTietPhieuNhap.vatTus.add(vatTu);
                }

            }
        });
        editSLVTThemPhieu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ActivityPhieuNhap.clickThemChiTietPhieuNhap == true) {
                    ActivityThemPhieuNhap.vatTuPhieuNhaps.get(position).setsL(editSLVTThemPhieu.getText().toString());
                }
                if (ActivityPhieuNhap.clickChiTietPhieuNhap == true) {
                    ActivityChiTietPhieuNhap.vatTuPhieuNhaps.get(position).setsL(editSLVTThemPhieu.getText().toString());
                }
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
                if (ActivityPhieuNhap.clickThemChiTietPhieuNhap == true) {
                    ActivityThemPhieuNhap.vatTuPhieuNhaps.get(position).setdV(editDVVTThemPhieu.getText().toString());
                }
                if (ActivityPhieuNhap.clickChiTietPhieuNhap == true) {
                    ActivityChiTietPhieuNhap.vatTuPhieuNhaps.get(position).setdV(editDVVTThemPhieu.getText().toString());
                }
            }
        });
        return convertView;
    }

}
package com.holo2k.giuakynhom15;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.holo2k.giuakynhom15.adapter.VatTuPhieuNhapAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.PhieuNhap;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;

public class ActivityChiTietPhieuNhap extends AppCompatActivity {
    Button btnThemVTChiTietPhieuNhap, btnXoaChiTietPhieuNhap;
    TextView tvNgayNhapPhieuChiTiet, tvMaPhieuChiTiet, tvTenKhoPhieuNhapChiTiet;
    ImageView imgThoatPhieuNhapChiTiet;
    ListView lvDSVatTuChiTietPhieu;
    VatTuPhieuNhapAdapter vatTuPhieuNhapAdapter;
    DBVatTu dbChiTietPhieuNhap = new DBVatTu(this);
    ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu_nhap);
        setControl();
        setEvents();
    }

    private void setEvents() {
        layDL();
        imgThoatPhieuNhapChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void layDL() {
        PhieuNhap phieuNhap = (PhieuNhap) getIntent().getSerializableExtra("chitietphieunhap");
        tvTenKhoPhieuNhapChiTiet.setText(dbChiTietPhieuNhap.getTenKhotrongChiTietPhieuNhap(phieuNhap.getMaKho()));
        tvMaPhieuChiTiet.setText(phieuNhap.getMaPhieuNhap());
        tvNgayNhapPhieuChiTiet.setText(phieuNhap.getNgayNhapPhieu());
        vatTuPhieuNhaps = dbChiTietPhieuNhap.getChiTietPhieuNhap(phieuNhap.getMaPhieuNhap());
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
        btnThemVTChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityChiTietPhieuNhap.this, ActivityChonVatTu.class);
                startActivityForResult(intent, 6);
            }
        });
    }

    private void setControl() {
        tvNgayNhapPhieuChiTiet = findViewById(R.id.tvNgayNhapPhieuChiTiet);
        btnThemVTChiTietPhieuNhap = findViewById(R.id.btnThemVTChiTietPhieuNhap);
        btnXoaChiTietPhieuNhap = findViewById(R.id.btnXoaChiTietPhieuNhap);
        tvMaPhieuChiTiet = findViewById(R.id.tvMaPhieuChiTiet);
        tvTenKhoPhieuNhapChiTiet = findViewById(R.id.tvTenKhoPhieuNhapChiTiet);
        imgThoatPhieuNhapChiTiet = findViewById(R.id.imgThoatPhieuNhapChiTiet);
        lvDSVatTuChiTietPhieu = findViewById(R.id.lvDSVatTuChiTietPhieu);
    }

    public void layDLVT(Intent data) {
        VatTuPhieuNhap vatTuPhieuNhap = (VatTuPhieuNhap) data.getSerializableExtra("vat_tu_phieu_nhap");
        vatTuPhieuNhaps.add(vatTuPhieuNhap);
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6) {
            if (resultCode == 1){
                layDLVT(data);
            }
        }
    }
}
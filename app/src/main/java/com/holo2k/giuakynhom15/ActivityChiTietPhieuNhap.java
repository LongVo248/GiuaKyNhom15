package com.holo2k.giuakynhom15;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.holo2k.giuakynhom15.adapter.ChonVatTuChiTietPhieuAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuPhieuNhapAdapter;
import com.holo2k.giuakynhom15.model.PhieuNhap;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;

public class ActivityChiTietPhieuNhap extends AppCompatActivity {
    Button btnThemVTChiTietPhieuNhap, btnXoaChiTietPhieuNhap, btnThemVTPN, btnLuuPhieuChiTiet;
    TextView tvNgayNhapPhieuChiTiet, tvMaPhieuChiTiet, tvTenKhoPhieuNhapChiTiet;
    ImageView imgThoatPhieuNhapChiTiet;
    ListView lvDSVatTuChiTietPhieu;
    VatTuPhieuNhapAdapter vatTuPhieuNhapAdapter;
    Dialog dialog;
    ListView lvChonVatTu;
    ChonVatTuChiTietPhieuAdapter chonVatTuAdapter;
    ImageView imgThoatChonVT;
    ArrayList<VatTu> vatTus = new ArrayList<>();
    public static ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps = new ArrayList<>();
    public static ArrayList<VatTu> viTriCB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu_nhap);
        setControl();
        setEvents();
    }

    private void setEvents() {
        layDL();
        removeVatTuTrongChonVT(vatTuPhieuNhaps);
        imgThoatPhieuNhapChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemVTChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vatTus.size() == 0) {
                    Toast.makeText(ActivityChiTietPhieuNhap.this, "Không còn vật tư để thêm", Toast.LENGTH_LONG).show();
                } else {
                    dialogChonVT();
                }
            }
        });
        btnLuuPhieuChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void removeVatTuTrongChonVT(ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps) {
        for (VatTuPhieuNhap vatTuPhieuNhap : vatTuPhieuNhaps) {
            for (int i = 0; i < vatTus.size(); i++) {
                if (vatTuPhieuNhap.getMaVT().equals(vatTus.get(i).getMaVatTu())) {
                    System.out.println("\n\n\n\n\n\n\n\n Mã VTPN: " + vatTuPhieuNhap.getMaVT() + "\n\n\n\n\n Mã VT: " + vatTus.get(i).getMaVatTu());
                    vatTus.remove(vatTus.get(i));
                }
            }
        }
    }

    public void layDL() {
        PhieuNhap phieuNhap = (PhieuNhap) getIntent().getSerializableExtra("chitietphieunhap");
        tvTenKhoPhieuNhapChiTiet.setText(MainActivity.dbVatTu.getTenKhotrongChiTietPhieuNhap(phieuNhap.getMaKho()));
        tvMaPhieuChiTiet.setText(phieuNhap.getMaPhieuNhap());
        tvNgayNhapPhieuChiTiet.setText(phieuNhap.getNgayNhapPhieu());
        vatTuPhieuNhaps = MainActivity.dbVatTu.getChiTietPhieuNhap(phieuNhap.getMaPhieuNhap());
        vatTus = MainActivity.dbVatTu.getAllVatTu();
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
        btnThemVTChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        btnLuuPhieuChiTiet = findViewById(R.id.btnLuuPhieuChiTiet);
    }

    public void layDLVT(Intent data) {
        VatTuPhieuNhap vatTuPhieuNhap = (VatTuPhieuNhap) data.getSerializableExtra("vat_tu_phieu_nhap");
        vatTuPhieuNhaps.add(vatTuPhieuNhap);
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
    }

    private void dialogChonVT() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chon_vat_tu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //tắt click ngoài là thoát'

        dialog.setCanceledOnTouchOutside(false);
        lvChonVatTu = dialog.findViewById(R.id.lvChonVatTu);
        btnThemVTPN = dialog.findViewById(R.id.btnThemVTPN);
        imgThoatChonVT = dialog.findViewById(R.id.imgThoatChonVTTrongPhieu);
        chonVatTuAdapter = new ChonVatTuChiTietPhieuAdapter(
                dialog.getContext(),
                R.layout.item_vat_tu_check_box,
                vatTus);
        lvChonVatTu.setAdapter(chonVatTuAdapter);
        lvChonVatTu.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChonVatTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(dialog.getContext(), "Bấm dô ròi nè", Toast.LENGTH_LONG);
            }
        });
        imgThoatChonVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnThemVTPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                themVaoVTPN(viTriCB);
                xoaVTDaChon(viTriCB);
                viTriCB.clear();
            }
        });
    }

    public void themVaoVTPN(ArrayList<VatTu> viTri) {
        for (VatTu vt : viTri) {
            VatTuPhieuNhap vatTuPhieuNhap = new VatTuPhieuNhap(vt.getMaVatTu(), vt.getTenVatTu(), "-", "0");
            vatTuPhieuNhaps.add(vatTuPhieuNhap);
        }
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
    }

    public void xoaVTDaChon(ArrayList<VatTu> viTri) {
        for (VatTu vt : viTri) {
            vatTus.remove(vt);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6) {
            if (resultCode == 1) {
                layDLVT(data);
            }
        }
    }
}
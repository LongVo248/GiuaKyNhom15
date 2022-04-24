package com.holo2k.giuakynhom15;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.holo2k.giuakynhom15.adapter.ThemPhieuAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;

import java.util.ArrayList;

public class ActivityThemPhieuNhap extends AppCompatActivity {
    TextView tvMaPhieuThemPhieu;
    EditText editTenKhoChiTiet;
    TextView tvNgayNhapPhieu;
    ListView lvDSVatTuPhieu;
    Button btnLuuPhieu, btnThemVTThemPhieu, btnDatLai;
    ArrayList<ChiTietPhieuNhap> chiTietPhieuNhapArrayList =  new ArrayList<>();
    ThemPhieuAdapter themPhieuAdapter;
    DBVatTu dbChiTietPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu);
        setControls();
        setEvents();
    }
    private void setControls(){
        tvMaPhieuThemPhieu = findViewById(R.id.tvMaPhieuThemPhieu);
        editTenKhoChiTiet = findViewById(R.id.editTenKhoChiTiet);
        tvNgayNhapPhieu = findViewById(R.id.tvNgayNhapPhieu);
        lvDSVatTuPhieu = findViewById(R.id.lvDSVatTuPhieu);
        btnDatLai = findViewById(R.id.btnDatLai);
        btnLuuPhieu = findViewById(R.id.btnLuuPhieu);
        btnThemVTThemPhieu = findViewById(R.id.btnThemVTthemPhieu);
    }

    private void setEvents(){

    }
}

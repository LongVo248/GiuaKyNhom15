package com.holo2k.giuakynhom15;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.holo2k.giuakynhom15.adapter.ThemPhieuAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;
import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityThemPhieuNhap extends AppCompatActivity {
    TextView tvMaPhieuThemPhieu;
    EditText editTenKhoChiTiet;
    TextView tvNgayNhapPhieu;
    ListView lvDSVatTuPhieu;
    ImageView imgCalendar;
    Button btnLuuPhieu, btnThemVTThemPhieu, btnDatLai;
    ArrayList<VatTuPhieuNhap> vatTuPhieuNhapArrayList = new ArrayList<>();
    ThemPhieuAdapter themPhieuAdapter;
    DBVatTu dbChiTietPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phieu_nhap);
        setControls();
        setEvents();
    }

    private void setControls() {
        tvMaPhieuThemPhieu = findViewById(R.id.tvMaPhieuThemPhieu);
        editTenKhoChiTiet = findViewById(R.id.editTenKhoChiTiet);
        tvNgayNhapPhieu = findViewById(R.id.tvNgayNhapPhieu);
        lvDSVatTuPhieu = findViewById(R.id.lvDSVatTuPhieu);
        btnDatLai = findViewById(R.id.btnDatLai);
        btnLuuPhieu = findViewById(R.id.btnLuuPhieu);
        btnThemVTThemPhieu = findViewById(R.id.btnThemVTthemPhieu);
        imgCalendar = findViewById(R.id.imgCalendar);
    }

    private void setEvents() {

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityThemPhieuNhap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvNgayNhapPhieu.setText(String.format("%d/%d/%d", i2, i1 + 1, i));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnThemVTThemPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityThemPhieuNhap.this, ActivityChonVatTu.class);
                startActivityForResult(intent, 4);
            }
        });

    }

    public void layDL(Intent data) {
        VatTuPhieuNhap vatTuPhieuNhap = (VatTuPhieuNhap) data.getSerializableExtra("vat_tu_phieu_nhap");
        System.out.println("\n\n\n\n" + vatTuPhieuNhap.getMaVT() + "\n\n\n");
        vatTuPhieuNhapArrayList.add(vatTuPhieuNhap);
        themPhieuAdapter = new ThemPhieuAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhapArrayList);
        lvDSVatTuPhieu.setAdapter(themPhieuAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if (resultCode == 1) {
                layDL(data);
            }
        }
    }
}

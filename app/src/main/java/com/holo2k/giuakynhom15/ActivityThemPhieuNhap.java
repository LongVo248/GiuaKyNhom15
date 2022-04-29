package com.holo2k.giuakynhom15;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.holo2k.giuakynhom15.adapter.ChonVatTuAdapter;
import com.holo2k.giuakynhom15.adapter.KhoItemSniperAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuPhieuNhapAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.PhieuNhap;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityThemPhieuNhap extends AppCompatActivity {
    TextView tvMaPhieuThemPhieu;
    Spinner spKho;
    Kho kho;
    ArrayList<VatTu> vatTus = new ArrayList<>();
    ChonVatTuAdapter chonVatTuAdapter;
    EditText editTenKhoChiTiet;
    TextView tvNgayNhapPhieu;
    ListView lvDSVatTuPhieu;
    ImageView imgCalendar, imgThoat;
    Button btnLuuPhieu, btnThemVTThemPhieu, btnDatLai;
    VatTuPhieuNhapAdapter vatTuPhieuNhapAdapter;
    DBVatTu dbChiTietPhieu = new DBVatTu(this);
    Dialog dialog;
    ListView lvChonVatTu;
    Button btnThemVTPN;
    ImageView imgThoatChonVT;
    public static ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps = new ArrayList<>();
    public static ArrayList<VatTu> viTriCB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phieu_nhap);
        setControls();
        setEvents();
    }

    private void setControls() {
        tvMaPhieuThemPhieu = findViewById(R.id.tvMaPhieuChiTiet);
        editTenKhoChiTiet = findViewById(R.id.editTenKhoChiTiet);
        tvNgayNhapPhieu = findViewById(R.id.tvNgayNhapPhieuChiTiet);
        lvDSVatTuPhieu = findViewById(R.id.lvDSVatTuPhieu);
        btnDatLai = findViewById(R.id.btnDatLai);
        btnLuuPhieu = findViewById(R.id.btnLuuPhieu);
        btnThemVTThemPhieu = findViewById(R.id.btnThemVTthemPhieu);
        imgCalendar = findViewById(R.id.imgCalendar);
        spKho = findViewById(R.id.spKho);
        imgThoat = findViewById(R.id.imgThoatPhieuNhapChiTiet);
    }

    private void setEvents() {
        vatTus = dbChiTietPhieu.getAllVatTu();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String ngayNhap = new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).toString();
        String maPhieuNhapTuDong = new StringBuilder()
                .append("PN")
                .append(year)
                .append(month)
                .append(calendar.get(Calendar.WEEK_OF_YEAR))
                .append(day)
                .append(calendar.get(Calendar.HOUR))
                .append(calendar.get(Calendar.MINUTE))
                .append(calendar.get(Calendar.SECOND))
                .append(calendar.get(Calendar.MILLISECOND)).toString();
        tvMaPhieuThemPhieu.setText(maPhieuNhapTuDong);
        tvNgayNhapPhieu.setText(ngayNhap);
        dbChiTietPhieu = new DBVatTu(this);
        KhoItemSniperAdapter khoItemSniperAdapter = new KhoItemSniperAdapter(this, R.layout.item_selected, getAllKho());
        spKho.setAdapter(khoItemSniperAdapter);
        spKho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kho = khoItemSniperAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(0, intent);
                finish();
            }
        });
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityThemPhieuNhap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvNgayNhapPhieu.setText(String.format("%d/%d/%d", i2, i1 + 1, i));
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btnThemVTThemPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vatTus.size() == 0) {
                    Toast.makeText(ActivityThemPhieuNhap.this, "Không còn vật tư để thêm", Toast.LENGTH_LONG).show();
                } else {
                    dialogChonVT();
                }
            }
        });

        btnLuuPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuNhap phieuNhap = new PhieuNhap();
                phieuNhap.setNgayNhapPhieu(tvNgayNhapPhieu.getText().toString());
                phieuNhap.setMaKho(kho.getMaKho());
                dbChiTietPhieu.themPhieunhap(phieuNhap);
                Intent intent = new Intent(ActivityThemPhieuNhap.this, ActivityPhieuNhap.class);
                startActivity(intent);
            }
        });
        btnLuuPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuNhap phieuNhap = new PhieuNhap();
                phieuNhap.setMaPhieuNhap(tvMaPhieuThemPhieu.getText().toString());
                phieuNhap.setNgayNhapPhieu(tvNgayNhapPhieu.getText().toString());
                phieuNhap.setMaKho(kho.getMaKho());
                dbChiTietPhieu.themPhieunhap(phieuNhap);

                if (vatTuPhieuNhaps.size() != 0 || vatTuPhieuNhaps != null) {
                    System.out.println("\n\n\n\nthem duoc vat tu roi ne\n\n\n");
                    for (VatTuPhieuNhap vatTuPhieuNhap : vatTuPhieuNhaps) {
                        dbChiTietPhieu.themChiTietPhieuNhap(phieuNhap.getMaPhieuNhap(), vatTuPhieuNhap);
                    }
                }
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }
        });

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
        chonVatTuAdapter = new ChonVatTuAdapter(dialog.getContext(), R.layout.item_vat_tu_check_box, vatTus);
        lvChonVatTu.setAdapter(chonVatTuAdapter);
        lvChonVatTu.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChonVatTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
            }
        });
        imgThoat.setOnClickListener(new View.OnClickListener() {
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
        lvDSVatTuPhieu.setAdapter(vatTuPhieuNhapAdapter);
    }
    public void xoaVTDaChon(ArrayList<VatTu> viTri){
        for (VatTu vt : viTri) {
            vatTus.remove(vt);
        }
    }

    public ArrayList<Kho> getAllKho() {
        return dbChiTietPhieu.getAllKho();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if (resultCode == 1) {
            }
        }
    }
}

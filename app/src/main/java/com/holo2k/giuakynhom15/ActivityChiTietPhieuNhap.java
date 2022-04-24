package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ActivityChiTietPhieuNhap extends AppCompatActivity {
    Button btnNgay;
    TextView tvNgayNhapPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu);
        setControl();
        setEvents();
    }

    private void setEvents() {
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("\n\n\n\n helloooooooooooooooooooooooooo \n\n\n");
                Toast.makeText(ActivityChiTietPhieuNhap.this, "Hello", Toast.LENGTH_LONG);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityChiTietPhieuNhap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvNgayNhapPhieu.setText(String.format("%d/%d/%d", i, i1, i2));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void setControl() {
        tvNgayNhapPhieu = findViewById(R.id.tvNgayNhapPhieu);
    }
}
package com.holo2k.giuakynhom15;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.holo2k.giuakynhom15.adapter.DSPhieuNhapAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.PhieuNhap;

import java.util.ArrayList;

public class ActivityPhieuNhap extends AppCompatActivity {
    ListView lvDSPhieu;
    Button btnChiTietPhieu;
    Button btnThemPhieu;
    ImageView imgThoat;
    ArrayList<PhieuNhap> phieuNhapArrayList = new ArrayList<>();
    DSPhieuNhapAdapter phieuNhapAdapter;
    DBVatTu dbPhieuNhap;
    EditText edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_nhap);
        setControls();
        setEvents();
    }

    private void setControls() {
        lvDSPhieu = findViewById(R.id.lvDSPhieu);
        btnChiTietPhieu = findViewById(R.id.btnChiTietPhieu);
        btnThemPhieu = findViewById(R.id.btnThemPhieu);
        imgThoat = findViewById(R.id.imgThoatPhieuNhap);
        edSearch = findViewById(R.id.editSearchDSPhieu);
    }

    private void setEvents() {
        showDBPhieuNhap();
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showResultSearchPhieuNhap(edSearch.getText().toString());
            }
        });
        imgThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvDSPhieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityPhieuNhap.this, ActivityChiTietPhieuNhap.class);
                intent.putExtra("chitietphieunhap", phieuNhapArrayList.get(i));
                startActivityForResult(intent, 1);
            }
        });
        btnThemPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActivityPhieuNhap.this, ActivityThemPhieuNhap.class);
                startActivity(intent);
            }
        });
    }


    public void showDBPhieuNhap() {
        dbPhieuNhap = new DBVatTu(ActivityPhieuNhap.this);
        phieuNhapArrayList = dbPhieuNhap.getAllPhieuNhap();
        phieuNhapAdapter = new DSPhieuNhapAdapter(this, R.layout.item_phieu_nhap, phieuNhapArrayList);
        lvDSPhieu.setAdapter(phieuNhapAdapter);
    }

    public void showResultSearchPhieuNhap(String data) {
        dbPhieuNhap = new DBVatTu(ActivityPhieuNhap.this);
        phieuNhapArrayList = dbPhieuNhap.searchPhieuNhap(data);
        phieuNhapAdapter = new DSPhieuNhapAdapter(this, R.layout.item_phieu_nhap, phieuNhapArrayList);
        lvDSPhieu.setAdapter(phieuNhapAdapter);
    }


}
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.holo2k.giuakynhom15.adapter.DSPhieuNhapAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.PhieuNhap;

import java.util.ArrayList;

public class ActivityPhieuNhap extends AppCompatActivity {
    ListView lvDSPhieu;
    Button btnThemPhieu;
    ImageView imgThoat;
    ArrayList<PhieuNhap> phieuNhapArrayList = new ArrayList<>();
    DSPhieuNhapAdapter phieuNhapAdapter;
    EditText edSearch;
    public static boolean clickChiTietPhieuNhap = false;
    public static boolean clickThemChiTietPhieuNhap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_nhap);
        setControls();
        setEvents();
    }

    private void setControls() {
        lvDSPhieu = findViewById(R.id.lvDSPhieu);
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
                ActivityPhieuNhap.clickChiTietPhieuNhap = true;
                startActivityForResult(intent, 3);
            }
        });
        btnThemPhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActivityPhieuNhap.this, ActivityThemPhieuNhap.class);
                ActivityPhieuNhap.clickThemChiTietPhieuNhap = true;
                startActivityForResult(intent, 5);
            }
        });
    }


    public void  showDBPhieuNhap() {
        phieuNhapArrayList = MainActivity.dbVatTu.getAllPhieuNhap();
        phieuNhapAdapter = new DSPhieuNhapAdapter(this, R.layout.item_phieu_nhap, phieuNhapArrayList);
        lvDSPhieu.setAdapter(phieuNhapAdapter);
    }

    public void showResultSearchPhieuNhap(String data) {
        phieuNhapArrayList = MainActivity.dbVatTu.searchPhieuNhap(data);
        phieuNhapAdapter = new DSPhieuNhapAdapter(this, R.layout.item_phieu_nhap, phieuNhapArrayList);
        lvDSPhieu.setAdapter(phieuNhapAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5){
            ActivityPhieuNhap.clickThemChiTietPhieuNhap = false;
            if (resultCode == 1){
                showDBPhieuNhap();
            }
        }
        if (requestCode == 3){
            ActivityPhieuNhap.clickChiTietPhieuNhap = false;
            if (resultCode == 1){
                showDBPhieuNhap();
                Toast.makeText(this, "Xoá thành công phiếu nhập", Toast.LENGTH_LONG).show();
            }
            if (resultCode == 2){
                showDBPhieuNhap();
                Toast.makeText(this, "Chỉnh sửa thành công phiếu nhập", Toast.LENGTH_LONG).show();
            }
        }
    }
}

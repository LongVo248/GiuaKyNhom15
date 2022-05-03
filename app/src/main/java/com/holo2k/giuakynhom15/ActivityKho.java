package com.holo2k.giuakynhom15;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.holo2k.giuakynhom15.adapter.KhoAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.error.CheckError;
import com.holo2k.giuakynhom15.model.Kho;

import java.util.ArrayList;

public class ActivityKho extends AppCompatActivity {
    ListView lvDSKho;
    Button btnThemKho, btnThongKeKho, btnThemKhoDialog;
    ImageView imgThoatKho, imgThoatThemKho;
    ArrayList<Kho> khoArrayList = new ArrayList<>();
    KhoAdapter khoAdapter;
    EditText editSearch, editThemTenKho;
    TextInputLayout txtInputThemMaKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho);
        setControls();
        setEvents();
    }

    private void setEvents() {
        showDB();
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showResultSearch(editSearch.getText().toString());
            }
        });
        imgThoatKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvDSKho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityKho.this, ActivityChiTietKho.class);
                intent.putExtra("chitietkho", khoArrayList.get(i));
                startActivityForResult(intent, 1);
            }
        });
        btnThemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemKho();
            }
        });
    }

    public void showResultSearch(String data) {
        khoArrayList = MainActivity.dbVatTu.searchKho(data);
        khoAdapter = new KhoAdapter(this, R.layout.item_kho, khoArrayList);
        lvDSKho.setAdapter(khoAdapter);
    }
    public void showDB() {
        khoArrayList = MainActivity.dbVatTu.getAllKho();
        khoAdapter = new KhoAdapter(this, R.layout.item_kho, khoArrayList);
        lvDSKho.setAdapter(khoAdapter);
    }

    private void setControls() {
        lvDSKho = findViewById(R.id.lvDSKho);
        btnThemKho = findViewById(R.id.btnThemKho);
        btnThongKeKho = findViewById(R.id.btnThongKeKho);
        imgThoatKho = findViewById(R.id.imgThoatKho);
        editSearch = findViewById(R.id.editSearch);

    }

    private void dialogThemKho() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_kho);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //tắt click ngoài là thoát'

        dialog.setCanceledOnTouchOutside(false);
        txtInputThemMaKho = dialog.findViewById(R.id.txtInputThemMaKho);
        btnThemKhoDialog = dialog.findViewById(R.id.btnThemKhoChiTiet);
        imgThoatThemKho = dialog.findViewById(R.id.imgThoatThemKho);
        editThemTenKho = dialog.findViewById(R.id.editThemTenKho);
        btnThemKhoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckError.checkEmptyEditText(editThemTenKho, txtInputThemMaKho)){
                    Kho kho = new Kho();
                    kho.setTenKho(editThemTenKho.getText().toString());
                    MainActivity.dbVatTu.themKho(kho);
                    dialog.cancel();
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    Toast.makeText(ActivityKho.this, "Thêm kho thành công", Toast.LENGTH_LONG).show();
                    showDB();
                }
            }
        });

        imgThoatThemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Boolean checkXoaChiTietKho = (Boolean) data.getSerializableExtra("xac_nhan_xoa");
            if (checkXoaChiTietKho) {
                Toast.makeText(this, "Xoá kho thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            Boolean checkSuaChiTietKho = (Boolean) data.getSerializableExtra("xac_nhan_sua");
            if (checkSuaChiTietKho) {
                Toast.makeText(this, "Sửa kho thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        if (requestCode == 1) {
            if (resultCode == 0 || resultCode == 1 || resultCode == 2) {
                showDB();
            }
        }
    }

}
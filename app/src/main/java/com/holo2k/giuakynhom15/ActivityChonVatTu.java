package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.holo2k.giuakynhom15.adapter.VatTuAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;

public class ActivityChonVatTu extends AppCompatActivity {
    Button btnChon;
    ListView lvDSVatTu;
    ImageView imgThoatVatTu;
    ArrayList<VatTu> vatTuArrayList = new ArrayList<>();
    VatTuAdapter vatTuAdapter;
    EditText editSearch;
    VatTuPhieuNhap vatTuPhieuNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_vat_tu);
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
        imgThoatVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvDSVatTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogChonVT(vatTuArrayList.get(i));
            }
        });

    }

    private void setControls() {
        btnChon = findViewById(R.id.btnChonVatTu);
        lvDSVatTu = findViewById(R.id.lvDSChonVatTu);
        editSearch = findViewById(R.id.editSearchChonVT);
        imgThoatVatTu = findViewById(R.id.imgThoatChonVatTu);

    }

    public void showResultSearch(String data) {
        vatTuArrayList = MainActivity.dbVatTu.searchVatTu(data);
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void showDB() {
        vatTuArrayList = MainActivity.dbVatTu.getAllVatTu();
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void dialogChonVT(VatTu vatTu) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chon_sl_vattu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //tắt click ngoài là thoát'
        dialog.setCanceledOnTouchOutside(false);

        Button btnThemSLVT = dialog.findViewById(R.id.btnThemSLVT);
        ImageView imgThoatChonSLVT = dialog.findViewById(R.id.imgThoatChonSLVT);
        EditText editThemDVT = dialog.findViewById(R.id.editThemDVT);
        EditText editThemSLVT = dialog.findViewById(R.id.editThemSL);
        btnThemSLVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                ActivityChonVatTu.this.vatTuPhieuNhap = new VatTuPhieuNhap(
                        vatTu.getMaVatTu(),
                        vatTu.getTenVatTu(),
                        editThemDVT.getText().toString(),
                        editThemSLVT.getText().toString());
                Intent intent = new Intent(ActivityChonVatTu.this, ActivityThemPhieuNhap.class);
                intent.putExtra("vat_tu_phieu_nhap", vatTuPhieuNhap);
                setResult(1, intent);
                finish();

            }
        });

        imgThoatChonSLVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}
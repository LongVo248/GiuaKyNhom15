package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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

import com.holo2k.giuakynhom15.adapter.VatTuAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;
import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.util.ArrayList;

public class ActivityChonVatTu extends AppCompatActivity {

    ListView lvChonVT;
    Button btnChon;
    ListView lvDSVatTu;
    ImageView imgThoatVatTu;
    ArrayList<VatTu> vatTuArrayList = new ArrayList<>();
    VatTuAdapter vatTuAdapter;
    DBVatTu dbVatTu;
    EditText editSearch;

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

                ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap();
                dialogChonVT(vatTuArrayList.get(i));
            }
        });

    }

    private void setControls() {
        lvChonVT = findViewById(R.id.lvDSChonVatTu);
        btnChon = findViewById(R.id.btnChonVatTu);
    }

    public void showResultSearch(String data) {
        dbVatTu = new DBVatTu(ActivityChonVatTu.this);
        vatTuArrayList = dbVatTu.searchVatTu(data);
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void showDB() {
        dbVatTu = new DBVatTu(ActivityChonVatTu.this);
        vatTuArrayList = dbVatTu.getAllVatTu();
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void dialogChonVT(VatTu vatTu) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chon_vat_tu);

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
                VatTuPhieuNhap vatTuPhieuNhap = new VatTuPhieuNhap();
                vatTuPhieuNhap.setdV(editThemDVT.getText().toString());
                vatTuPhieuNhap.setsL(editThemSLVT.getText().toString());
                vatTuPhieuNhap.setMaVT(vatTu.getMaVatTu());
                vatTuPhieuNhap.setTenVT(dbVatTu.getTenvt(vatTu.getMaVatTu()));
                dialog.cancel();
                Intent intent = new Intent();
                intent.putExtra("chitietchonvattu", vatTuPhieuNhap);
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
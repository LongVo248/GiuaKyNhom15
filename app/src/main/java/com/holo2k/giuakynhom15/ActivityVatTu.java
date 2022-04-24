package com.holo2k.giuakynhom15;

import androidx.annotation.Nullable;
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

import com.holo2k.giuakynhom15.adapter.KhoAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.VatTu;

import java.util.ArrayList;

public class ActivityVatTu extends AppCompatActivity {
    ListView lvDSVatTu;
    Button btnThemVatTu, btnThongKeVatTu;
    ImageView imgThoatVatTu;
    ArrayList<VatTu> vatTuArrayList = new ArrayList<>();
    VatTuAdapter vatTuAdapter;
    DBVatTu dbVatTu;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_tu);
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
                Intent intent = new Intent(ActivityVatTu.this, ActivityChiTietVatTu.class);
                intent.putExtra("chitietvattu", vatTuArrayList.get(i));
                startActivityForResult(intent, 1);
            }
        });
        btnThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemVatTu();
            }
        });
    }

    private void dialogThemVatTu() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_vat_tu);

        dialog.show();
        //tắt click ngoài là thoát'

        dialog.setCanceledOnTouchOutside(false);

        Button btnThemVatTu = dialog.findViewById(R.id.btnThemVatTuChiTiet);
        ImageView imgThoatThemVatTu = dialog.findViewById(R.id.imgThoatThemVatTu);
        EditText editThemMaVatTu = dialog.findViewById(R.id.editThemMaVatTu);
        EditText editThemTenVatTu = dialog.findViewById(R.id.editThemTenVatTu);
        EditText editThemXuatXu = dialog.findViewById(R.id.editThemXuatXu);
        btnThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VatTu vatTu = new VatTu();
                vatTu.setMaVatTu(editThemMaVatTu.getText().toString());
                vatTu.setXuatXu(editThemXuatXu.getText().toString());
                vatTu.setTenVatTu(editThemTenVatTu.getText().toString());
                dbVatTu.themVatTu(vatTu);
                dialog.cancel();
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                Toast.makeText(ActivityVatTu.this, "Thêm vật tư thành công", Toast.LENGTH_LONG).show();
                showDB();
            }
        });

        imgThoatThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void showResultSearch(String data) {
        dbVatTu = new DBVatTu(ActivityVatTu.this);
        vatTuArrayList = dbVatTu.searchVatTu(data);
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void showDB() {
        dbVatTu = new DBVatTu(ActivityVatTu.this);
        vatTuArrayList = dbVatTu.getAllVatTu();
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void setControls() {
        lvDSVatTu = findViewById(R.id.lvDSVatTu);
        btnThemVatTu = findViewById(R.id.btnThemVatTu);
        btnThongKeVatTu = findViewById(R.id.btnThongKeVatTu);
        imgThoatVatTu = findViewById(R.id.imgThoatVatTu);
        editSearch = findViewById(R.id.editSearch);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Boolean checkXoaChiTietVatTu = (Boolean) data.getSerializableExtra("xac_nhan_xoa");
            if (checkXoaChiTietVatTu) {
                Toast.makeText(this, "Xoá vật tư thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            Boolean checkSuaChiTietVatTu = (Boolean) data.getSerializableExtra("xac_nhan_sua");
            if (checkSuaChiTietVatTu) {
                Toast.makeText(this, "Sửa vật tư thành công", Toast.LENGTH_SHORT).show();
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
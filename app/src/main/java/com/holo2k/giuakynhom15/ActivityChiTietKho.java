package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.model.Kho;

public class ActivityChiTietKho extends AppCompatActivity {
    Kho kho;
    Button btnSua, btnXoa;
    ImageView imgThoat;
    TextView tvMaKhoChiTiet;
    EditText editTenKhoChiTiet;
    DBVatTu dbChiTietKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_kho);
        setControl();
        setEvents();
    }

    private void setEvents() {
        layDL();
        dbChiTietKho = new DBVatTu(ActivityChiTietKho.this);
        imgThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(0, intent);
                finish();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogXoa();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSua();
            }
        });
    }

    public void showXoaKhongThanhCong(boolean check) {
        if (check == false) {
            Toast.makeText(this, "Kho đã có trong phiếu nhập\n Không thể xoá!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("xac_nhan_xoa", check);
            setResult(1, intent);
            finish();
        }
    }
    public void showSuaKhongThanhCong(boolean check) {
        if (check == false) {
            Toast.makeText(this, "Kho đã có trong phiếu nhập\n Không thể sửa!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("xac_nhan_sua", check);
            setResult(2, intent);
            finish();
        }
    }

    private void dialogXoa() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_xoa);

        dialog.show();
        //tắt click ngoài là thoát
        dialog.setCanceledOnTouchOutside(false);

        Button btnCo = dialog.findViewById(R.id.btnCo);
        Button btnKhong = dialog.findViewById(R.id.btnKhong);

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                showXoaKhongThanhCong(dbChiTietKho.deleteKho(ActivityChiTietKho.this.kho.getMaKho()));
            }
        });

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    private void dialogSua() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_sua);

        dialog.show();
        //tắt click ngoài là thoát
        dialog.setCanceledOnTouchOutside(false);

        Button btnCo = dialog.findViewById(R.id.btnCo);
        Button btnKhong = dialog.findViewById(R.id.btnKhong);

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                kho = new Kho(kho.getMaKho(), editTenKhoChiTiet.getText().toString());
                showSuaKhongThanhCong(dbChiTietKho.capNhatKho(kho));
            }
        });

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    private void layDL() {
        kho = (Kho) getIntent().getSerializableExtra("chitietkho");
        tvMaKhoChiTiet.setText(String.valueOf(kho.getMaKho()));
        editTenKhoChiTiet.setText(kho.getTenKho());
    }

    private void setControl() {
        btnSua = findViewById(R.id.btnSuaKho);
        btnXoa = findViewById(R.id.btnXoaKho);
        imgThoat = findViewById(R.id.imgThoatChiTietKho);
        tvMaKhoChiTiet = findViewById(R.id.tvMaKhoChiTiet);
        editTenKhoChiTiet = findViewById(R.id.editTenKhoChiTiet);
    }
}
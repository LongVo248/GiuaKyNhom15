package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnKho, btnVatTu, btnPhieuNhap, btnTimKiem, btnThongKe, btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //quản lý kho
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển qua activity kho
                Intent intent= new Intent(MainActivity.this, ActivityKho.class);
                startActivity(intent);
            }
        });

        //Nhấn nút thoát app
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogExit();
            }
        });
    }

    //Hiển thị cửa sổ dialog Thoát
    private void DialogExit() {
        Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialogthoat);

        //tắt click ngoài là thoát
        dialog.setCanceledOnTouchOutside(false);

        Button btnCo= dialog.findViewById(R.id.btnCo);
        Button btnKhong= dialog.findViewById(R.id.btnKhong);

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

                //Thoát
                Intent intent1= new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
            }
        });

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void setControl() {
        btnKho= findViewById(R.id.btnKho);
        btnVatTu= findViewById(R.id.btnVatTu);
        btnPhieuNhap= findViewById(R.id.btnPhieuNhap);
        btnTimKiem= findViewById(R.id.btnTimKiem);
        btnThongKe= findViewById(R.id.btnThongKe);
        btnThoat= findViewById(R.id.btnThoat);
    }
}
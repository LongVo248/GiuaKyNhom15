package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.holo2k.giuakynhom15.database.DBVatTu;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    Button btnKho, btnVatTu, btnPhieuNhap, btnTimKiem, btnThongKe, btnThoat;
    public static DBVatTu dbVatTu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbVatTu = new DBVatTu(this);
        dbVatTu.deleteAllTable("vattu");
        setControl();
        setEvent();
    }

    public static Bitmap chuyenByteSangHinhAnh(Uri selectedFileUri, Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(selectedFileUri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void setEvent() {
        //tạo database
        //quản lý kho
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển qua activity kho
                Intent intent= new Intent(MainActivity.this, ActivityKho.class);
                startActivity(intent);
            }
        });

        btnVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, ActivityVatTu.class);
                startActivity(intent);
            }
        });

        btnPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, ActivityPhieuNhap.class);
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
        dialog.setContentView(R.layout.dialog_thoat);

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
package com.holo2k.giuakynhom15.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.holo2k.giuakynhom15.model.Kho;

import java.util.ArrayList;
import java.util.Objects;

public class DBVatTu extends SQLiteOpenHelper {
    //Tên database
    private static final String DATABASE_NAME = "QUANLYNHAPKHO";
    //Bảng Kho
    private static final String KHO = "KHO";
    private static final String TENKHO = "TENKHO";
    private static final String MAKHO = "MAKHO";
    private static final int VERSION = 1;

    //Bảng Phiếu Nhập
    private static final String PHIEUNHAP = "PHIEUNHAP";
    private static final String SOPHIEU = "SOPHIEU";
    private static final String NGAYLAP = "NGAYLAP";

    //Bảng Chi tiết phiếu nhập
    private static final String CHITIETPHIEUNHAP = "CHITIETPHIEUNHAP";
    private static final String DVT = "DVT";
    private static final String SOLUONG = "SOLUONG";

    //Bảng Vật tư
    private static final String VATTU = "VATTU";
    private static final String MAVT = "MAVT";
    private static final String TENVT = "TENVT";
    private static final String XUATXU = "XUATXU";

    //Tạo bảng KHO
    private String createTableKho = "CREATE TABLE " + KHO + " ( " + MAKHO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TENKHO + " TEXT) ";

    //Tạo bảng VATTU
    private String createTableVatTu = "CREATE TABLE " + VATTU + " ( " + MAVT + " TEXT PRIMARY KEY , "
            + TENVT + " TEXT, "
            + XUATXU + " TEXT) ";

    //Tạo bảng PHIEUNHAP
    private String createTablePhieuNhap = "CREATE TABLE " + PHIEUNHAP + " ( " + SOPHIEU + " TEXT PRIMARY KEY , "
            + NGAYLAP + " TEXT, "
            + MAKHO + " TEXT ,FOREIGN KEY ( " + MAKHO + " ) REFERENCES " + KHO + "(" + MAKHO + "))";

    //Tạo bảng CHITIETPHIEUNHAP
    private String createTableChiTietPhieuNhap = "CREATE TABLE " + CHITIETPHIEUNHAP + " ( "
            + SOPHIEU + " TEXT , "
            + MAVT + " TEXT, "
            + DVT + " TEXT, "
            + SOLUONG + " TEXT, "
            + "PRIMARY KEY (" + SOPHIEU + "," + MAVT + "), "
            + "FOREIGN KEY (" + SOPHIEU + ") REFERENCES " + PHIEUNHAP + "( " + SOPHIEU + ") ON DELETE CASCADE ON UPDATE NO ACTION,"
            + "FOREIGN KEY (" + MAVT + ") REFERENCES " + VATTU + " (" + MAVT + ") ON DELETE CASCADE ON UPDATE NO ACTION )";

    public DBVatTu(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableKho);
        sqLiteDatabase.execSQL(createTableVatTu);
        sqLiteDatabase.execSQL(createTablePhieuNhap);
        sqLiteDatabase.execSQL(createTableChiTietPhieuNhap);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void themKho(Kho kho) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TENKHO, kho.getTenKho());

        db.insert(KHO, null, values);
        db.close();
    }

    public boolean CapNhatKho(Kho kho, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MAKHO, kho.getMaKho());
        values.put(TENKHO, kho.getTenKho());

        db.update(KHO, values, MAKHO + "=" + id, null);
        return true;
    }

    public ArrayList<Kho> getAllKho() {
        ArrayList<Kho> khoArrayList = new ArrayList<>();
        String getAllKho = "SELECT * FROM " + KHO;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllKho, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int maKho;
            String tenKho;
            maKho = cursor.getInt(0);
            tenKho = cursor.getString(1);
            Kho kho = new Kho(maKho, tenKho);
            khoArrayList.add(kho);
            cursor.moveToNext();
        }
        return khoArrayList;
    }

    public boolean checkMaKhoTrongPhieuNhap(int maKho) {
        String getAllKho = "SELECT * FROM " + PHIEUNHAP + " WHERE MAKHO = " + "'" + String.valueOf(maKho) + "'";
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(getAllKho, null);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    public boolean deleteKho(Integer id) {
        //chú ý : getWritableDatabase là cả đọc và ghi
        if (true) {
            SQLiteDatabase db = this.getWritableDatabase();
                db.delete(KHO, MAKHO + " = " +"'" + id.toString() + "'", null);
            return true;
        } else {
            return true;
        }
    }
}

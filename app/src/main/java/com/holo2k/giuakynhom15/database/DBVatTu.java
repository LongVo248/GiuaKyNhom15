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
import com.holo2k.giuakynhom15.model.PhieuNhap;
import com.holo2k.giuakynhom15.model.VatTu;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

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

    public boolean capNhatKho(Kho kho) {
        if (!checkMaKhoTrongPhieuNhap(kho.getMaKho())) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MAKHO, kho.getMaKho());
            values.put(TENKHO, kho.getTenKho());
            db.update(KHO, values, MAKHO + "=" + String.valueOf(kho.getMaKho()), null);
            return true;
        } else {
            return false;
        }
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

    public ArrayList<Kho> searchKho(String data) {
        ArrayList<Kho> khoArrayList = new ArrayList<>();
        String getAllKho = new StringBuilder().append("SELECT * FROM ").append(KHO).append(" WHERE ").append(TENKHO)
                .append(" LIKE '%").append(data).append("%'").toString();
        SQLiteDatabase db = getWritableDatabase();
        try {
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
        } catch (NullPointerException e) {
            Logger.getLogger(String.valueOf(e));
        }
        return getAllKho();
    }

    public boolean checkMaKhoTrongPhieuNhap(int maKho) {
        //true la tim thay
        //false khong tim thay

        String getAllKho = "SELECT * FROM " + PHIEUNHAP + " WHERE MAKHO = " + String.valueOf(maKho);
        System.out.println(getAllKho);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllKho, null);
        return cursor.getCount() == 0 ? false : true;
    }

    public boolean deleteKho(int id) {
        //chú ý : getWritableDatabase là cả đọc và ghi
        if (!checkMaKhoTrongPhieuNhap(id)) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(KHO, MAKHO + " = " + "'" + String.valueOf(id) + "'", null);
            return true;
        } else {
            return false;
        }
    }


    // CRUD Vat Tu
    public void themVatTu(VatTu vatTu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MAVT, vatTu.getMaVatTu());
        values.put(TENVT, vatTu.getTenVatTu());
        values.put(XUATXU, vatTu.getXuatXu());

        db.insert(VATTU, null, values);
        db.close();
    }

    public boolean capNhatVatTu(VatTu vatTu) {
        if (!checkMaVatTuTrongPhieuNhap(vatTu.getMaVatTu())) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MAVT, vatTu.getMaVatTu());
            values.put(TENVT, vatTu.getTenVatTu());
            values.put(XUATXU, vatTu.getXuatXu());
            db.update(VATTU, values, MAVT + "=" + String.valueOf(vatTu.getMaVatTu()), null);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<VatTu> getAllVatTu() {
        ArrayList<VatTu> vatTuArrayList = new ArrayList<>();
        String getAllVatTu = "SELECT * FROM " + VATTU;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllVatTu, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maVatTu;
            String tenVatTu;
            String xuatXu;
            maVatTu = cursor.getString(0);
            tenVatTu = cursor.getString(1);
            xuatXu = cursor.getString(2);
            VatTu vatTu = new VatTu(maVatTu, tenVatTu, xuatXu);
            vatTuArrayList.add(vatTu);
            cursor.moveToNext();
        }
        return vatTuArrayList;
    }

    public ArrayList<VatTu> searchVatTu(String data) {
        ArrayList<VatTu> vatTuArrayList = new ArrayList<>();
        String getAllVatTu = new StringBuilder().append("SELECT * FROM ").append(VATTU).append(" WHERE ").append(TENVT)
                .append(" LIKE '%").append(data).append("%'").toString();
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(getAllVatTu, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maVatTu;
                String tenVatTu;
                String xuatXu;
                maVatTu = cursor.getString(0);
                tenVatTu = cursor.getString(1);
                xuatXu = cursor.getString(2);
                VatTu vatTu = new VatTu(maVatTu, tenVatTu, xuatXu);
                vatTuArrayList.add(vatTu);
                cursor.moveToNext();
            }
            return vatTuArrayList;
        } catch (NullPointerException e) {
            Logger.getLogger(String.valueOf(e));
        }
        return getAllVatTu();
    }

    public boolean checkMaVatTuTrongPhieuNhap(String maVatTu) {
        //true la tim thay
        //false khong tim thay

        String getAllVatTu = "SELECT * FROM " + PHIEUNHAP + " WHERE MAVT = " + String.valueOf(maVatTu);
        System.out.println(getAllVatTu);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllVatTu, null);
        return cursor.getCount() == 0 ? false : true;
    }

    public boolean deleteVatTu(String id) {
        //chú ý : getWritableDatabase là cả đọc và ghi
        if (!checkMaVatTuTrongPhieuNhap(id)) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(VATTU, MAVT + " = " + "'" + id + "'", null);
            return true;
        } else {
            return false;
        }
    }

    public String getTenvt(String maVT){
        String getTenvt = "SELECT TENVT FORM " + VATTU + " WHERE MAVT = " +String.valueOf(maVT);
        System.out.println(getTenvt);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getTenvt, null);
        cursor.moveToFirst();
                return cursor.getString(0);
    }
    //Phiếu nhập

    public void themPhieunhap(PhieuNhap phieuNhap) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MAKHO, phieuNhap.getMaKho());
        values.put(NGAYLAP, String.valueOf(phieuNhap.getNgayNhapPhieu()));
        db.insert(PHIEUNHAP, null, values);
        db.close();
    }

    public ArrayList<PhieuNhap> getAllPhieuNhap() {
        ArrayList<PhieuNhap> phieuNhapArrayList = new ArrayList<>();
        String getAllPhieuNhap = "SELECT * FROM " + PHIEUNHAP;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllPhieuNhap, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maPhieuNhap;
            String tenKho;
            String ngayNhap;
            maPhieuNhap = cursor.getString(0);
            tenKho = cursor.getString(1);
            ngayNhap = cursor.getString(2);
            PhieuNhap phieuNhap = new PhieuNhap(maPhieuNhap, tenKho, ngayNhap);
            phieuNhapArrayList.add(phieuNhap);
            cursor.moveToNext();
        }
        return phieuNhapArrayList;
    }

    public ArrayList<PhieuNhap> searchPhieuNhap(String data) {
        ArrayList<PhieuNhap> phieuNhapArrayList = new ArrayList<>();
        String getALLPhieuNhap = new StringBuilder().append("SELECT * FROM ").append(PHIEUNHAP).append(" WHERE ").append(SOPHIEU)
                .append(" LIKE '%").append(data).append("%'").toString();
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(getALLPhieuNhap, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maPhieuNhap;
                String tenKho;
                String ngayNhap;
                maPhieuNhap = cursor.getString(0);
                tenKho = cursor.getString(1);
                ngayNhap = cursor.getString(2);
                PhieuNhap phieuNhap = new PhieuNhap(maPhieuNhap, tenKho, ngayNhap);
                phieuNhapArrayList.add(phieuNhap);
                cursor.moveToNext();
            }
            return phieuNhapArrayList;
        } catch (NullPointerException e) {
            Logger.getLogger(String.valueOf(e));
        }
        return getAllPhieuNhap();
    }
}

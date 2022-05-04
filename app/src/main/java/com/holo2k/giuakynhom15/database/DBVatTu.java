package com.holo2k.giuakynhom15.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.PhieuNhap;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
    private static final String HINHANH = "HINHANH";

    //Tạo bảng KHO
    private String createTableKho = "CREATE TABLE " + KHO + " ( " + MAKHO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TENKHO + " TEXT) ";

    //Tạo bảng VATTU
    private String createTableVatTu = "CREATE TABLE " + VATTU + " ( " + MAVT + " TEXT PRIMARY KEY , "
            + TENVT + " TEXT, "
            + XUATXU + " TEXT, URI TEXT)";

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

    public byte[] chuyenHinhAnhSangByte(ImageView imgVT) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgVT.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 75, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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

        String sql = "INSERT INTO VATTU VALUES(?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, vatTu.getMaVatTu());
        statement.bindString(2, vatTu.getTenVatTu());
        statement.bindString(3, vatTu.getXuatXu());
        statement.bindString(4, vatTu.getUri().toString());
        statement.executeInsert();
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
        while (cursor.moveToNext()) {
            vatTuArrayList.add(
                    new VatTu(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));
        }
        cursor.close();
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
                String hinhAnh = cursor.getString(3);
                VatTu vatTu = new VatTu(maVatTu, tenVatTu, xuatXu, hinhAnh);
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

        String getAllVatTu = "SELECT * FROM " + CHITIETPHIEUNHAP + " WHERE MAVT = " + "'" + maVatTu + "'";
        System.out.println(getAllVatTu);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllVatTu, null);
        System.out.println("\n\n\n\n" + cursor.getCount() + "\n\n\n\n");
        return cursor.getCount() == 0 ? false : true;
    }

    public boolean deleteVatTu(String id) {
        //chú ý : getWritableDatabase là cả đọc và ghi
        if (!checkMaVatTuTrongPhieuNhap(id)) {
            SQLiteDatabase db = this.getWritableDatabase();
            System.out.println("\n\n\n\n DA XOAAAAAAAAAAA \n\n\n\n");
            db.delete(VATTU, MAVT + " = " + "'" + id + "'", null);
            return true;
        } else {
            return false;
        }
    }

    public VatTu getVatTu(String maVT) {
        String getVT = "SELECT * FROM VATTU WHERE MAVT = '" + maVT + "'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getVT, null);
        cursor.moveToFirst();
        VatTu vatTu = new VatTu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        return vatTu;
    }
    //Phiếu nhập

    public void themPhieunhap(PhieuNhap phieuNhap) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SOPHIEU, phieuNhap.getMaPhieuNhap());
        values.put(MAKHO, phieuNhap.getMaKho());
        values.put(NGAYLAP, phieuNhap.getNgayNhapPhieu());
        db.insert(PHIEUNHAP, null, values);
        db.close();
    }

    public void deleteAllTable(String table) {
        String query = "delete from " + table.toUpperCase(Locale.ROOT);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    public void dropVatTu() {
        String query = "drop table vattu";
        String query2 = createTableVatTu;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.execSQL(query2);
    }

    public ArrayList<PhieuNhap> getAllPhieuNhap() {
        ArrayList<PhieuNhap> phieuNhapArrayList = new ArrayList<>();
        String getAllPhieuNhap = "SELECT * FROM " + PHIEUNHAP;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllPhieuNhap, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maPhieuNhap;
            int maKho;
            String ngayNhap;
            maPhieuNhap = cursor.getString(0);
            ngayNhap = cursor.getString(1);
            maKho = cursor.getInt(2);
            PhieuNhap phieuNhap = new PhieuNhap(maPhieuNhap, maKho, ngayNhap);
            phieuNhapArrayList.add(phieuNhap);
            cursor.moveToNext();
        }
        return phieuNhapArrayList;
    }


    public ArrayList<PhieuNhap> searchPhieuNhap(String data) {
        ArrayList<PhieuNhap> phieuNhapArrayList = new ArrayList<>();
        String getALLPhieuNhap = new StringBuilder().append("SELECT * FROM ")
                .append(PHIEUNHAP).append(" WHERE ").append(SOPHIEU)
                .append(" LIKE '%").append(data).append("%' ")
                .append("OR ").toString();
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(getALLPhieuNhap, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String maPhieuNhap;
                int maKho;
                String ngayNhap;
                maPhieuNhap = cursor.getString(0);
                ngayNhap = cursor.getString(1);
                maKho = cursor.getInt(2);
                PhieuNhap phieuNhap = new PhieuNhap(maPhieuNhap, maKho, ngayNhap);
                phieuNhapArrayList.add(phieuNhap);
                cursor.moveToNext();
            }
            return phieuNhapArrayList;
        } catch (NullPointerException e) {
            Logger.getLogger(String.valueOf(e));
        }
        return getAllPhieuNhap();
    }

    public String getTenKhotrongChiTietPhieuNhap(int maKho) {
        String get = "SELECT TENKHO FROM KHO WHERE MAKHO = " + maKho;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(get, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            String tenKho = cursor.getString(0);
            cursor.close();
            return tenKho;
        } else {
            cursor.close();
            return null;
        }
    }

    public ArrayList<VatTuPhieuNhap> getChiTietPhieuNhap(String maPhieuNhap) {
        ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps = new ArrayList<>();
        String query = "SELECT * FROM " + CHITIETPHIEUNHAP + " WHERE SOPHIEU = '" + maPhieuNhap + "'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maVT = cursor.getString(1);
            String dVT = cursor.getString(2);
            String sL = cursor.getString(3);
            String tenVT = getVatTuPN(maVT);
            VatTuPhieuNhap vatTuPhieuNhap = new VatTuPhieuNhap(maVT, tenVT, dVT, sL);
            vatTuPhieuNhaps.add(vatTuPhieuNhap);
            cursor.moveToNext();
        }
        cursor.close();
        return vatTuPhieuNhaps;
    }

    public String getVatTuPN(String maVT) {
        String tenVT = " SELECT TENVT FROM VATTU WHERE MAVT = '" + maVT + "'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(tenVT, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public void themChiTietPhieuNhap(String maPhieuNhap, VatTuPhieuNhap vatTuPhieuNhap) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SOPHIEU, maPhieuNhap);
        values.put(MAVT, vatTuPhieuNhap.getMaVT());
        values.put(DVT, vatTuPhieuNhap.getdV());
        values.put(SOLUONG, vatTuPhieuNhap.getsL());
        db.insert(CHITIETPHIEUNHAP, null, values);
        db.close();
    }

    public int deletePhieuNhap(String maPN) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(PHIEUNHAP, SOPHIEU + " = " + "'" + maPN + "'", null);
    }

//    public String layMaPhieuMax(){
//        String layMaPhieuMax = "SELECT MAX(MAVT) FROM " + VATTU;
//        SQLiteDatabase db = getWritableDatabase();
//        try{
//        Cursor cursor = db.rawQuery(layMaPhieuMax, null);
//        return cursor.getString(0);
//        } catch (NullPointerException e) {
//            Logger.getLogger(String.valueOf(e));
//        } return String.valueOf(1);
//    }
}

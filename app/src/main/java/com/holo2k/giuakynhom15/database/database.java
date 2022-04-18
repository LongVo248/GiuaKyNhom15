package com.holo2k.giuakynhom15.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.holo2k.giuakynhom15.model.Kho;

public class database extends SQLiteOpenHelper {
    //Tên database
    private static String DATABASE_NAME = "QUANLYNHAPKHO";
    //Bảng Kho
    private static String KHO= "KHO";
    private static String TENKHO = "TENKHO";
    private static String MAKHO = "MAKHO";
    private static int VERSION = 1;

    //Bảng Phiếu Nhập
    private static String PHIEUNHAP= "PHIEUNHAP";
    private static String SOPHIEU= "SOPHIEU";
    private static String NGAYLAP= "NGAYLAP";

    //Bảng Chi tiết phiếu nhập
    private static String CHITIETPHIEUNHAP= "CHITIETPHIEUNHAP";
    private static String DVT= "DVT";
    private static String SOLUONG= "SOLUONG";

    //Bảng Vật tư
    private static String VATTU="VATTU";
    private static String MAVT="MAVT";
    private static String TENVT= "TENVT";
    private static String XUATXU="XUATXU";

    //Tạo bảng KHO
    private String SQLQuery = "CREATE TABLE "+ KHO +" ( "+MAKHO+" TEXT PRIMARY KEY , "
            +TENKHO+" TEXT) ";

    //Tạo bảng VATTU
    private String SQLQuery1 = "CREATE TABLE "+ VATTU +" ( "+MAVT+" TEXT PRIMARY KEY , "
            +TENVT+" TEXT, "
            +XUATXU+" TEXT) ";

    //Tạo bảng PHIEUNHAP
    private String SQLQuery2 = "CREATE TABLE "+ PHIEUNHAP +" ( "+SOPHIEU+" TEXT PRIMARY KEY , "
            +NGAYLAP+" TEXT, "
            +MAKHO+" TEXT ,FOREIGN KEY ( "+ MAKHO +" ) REFERENCES "+ KHO+"("+MAKHO+"))";

    //Tạo bảng CHITIETPHIEUNHAP
    private String SQLQuery3 = "CREATE TABLE "+ CHITIETPHIEUNHAP +" ( "
            +SOPHIEU+" TEXT , "
            +MAVT+" TEXT, "
            +DVT+" TEXT, "
            +SOLUONG+" TEXT, "
            +"PRIMARY KEY (" +SOPHIEU +"," + MAVT +"), "
            +"FOREIGN KEY ("+ SOPHIEU +") REFERENCES "+ PHIEUNHAP + "( "+ SOPHIEU +") ON DELETE CASCADE ON UPDATE NO ACTION,"
            +"FOREIGN KEY ("+ MAVT +") REFERENCES "+ VATTU +" ("+ MAVT +") ON DELETE CASCADE ON UPDATE NO ACTION )";

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        sqLiteDatabase.execSQL(SQLQuery1);
        sqLiteDatabase.execSQL(SQLQuery2);
        sqLiteDatabase.execSQL(SQLQuery3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void ThemKho(Kho kho){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(MAKHO, kho.getMaKho());
        values.put(TENKHO, kho.getTenKho());

        db.insert(KHO, null, values);
        db.close();
    }

    public boolean CapNhatKho(Kho kho, String id){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(MAKHO, kho.getMaKho());
        values.put(TENKHO, kho.getTenKho());

        db.update(KHO,  values, MAKHO+"="+id, null);
        return true;
    }

    public Cursor getDataKho(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+KHO, null);
        return cursor;
    }

    public int DeleteKho(String id){
        //chú ý : getWritableDatabase là cả đọc và ghi
        SQLiteDatabase db= this.getWritableDatabase();
        int res= db.delete(KHO, MAKHO +" = "+id, null);
        return res;
    }
}

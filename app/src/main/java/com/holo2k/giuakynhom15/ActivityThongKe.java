package com.holo2k.giuakynhom15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.holo2k.giuakynhom15.adapter.KhoItemSniperAdapter;
import com.holo2k.giuakynhom15.adapter.ThongKeKhoAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuPhieuNhapAdapter;
import com.holo2k.giuakynhom15.model.ChiTietPhieuNhap;
import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityThongKe extends AppCompatActivity {

    Button btnXuatPDFThongKe;
    Spinner spKhoThongKe;
    Kho kho = new Kho();
    ImageView imgThoatThongKe;
    ArrayList<VatTuPhieuNhap> vatTuKhos = new ArrayList<>();
    ArrayList<ChiTietPhieuNhap> chiTietPhieuNhaps = new ArrayList<>();
    public static ListView lvDSVatTuKho;
    public static ThongKeKhoAdapter thongKeKhoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        ActivityCompat.requestPermissions(ActivityThongKe.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        setControls();
        setEvents();

    }

    private void setEvents() {
        KhoItemSniperAdapter khoItemSniperAdapter = new KhoItemSniperAdapter(this, R.layout.item_selected, getAllKho());
        spKhoThongKe.setAdapter(khoItemSniperAdapter);
        spKhoThongKe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kho = khoItemSniperAdapter.getItem(i);
                vatTuKhos = MainActivity.dbVatTu.getSumVT(MainActivity.dbVatTu.getChiTietPhieuTheoKho(kho.getMaKho(),
                        MainActivity.dbVatTu.getAllPhieuNhap(),
                        MainActivity.dbVatTu.getAllChiTietPhieuNhap()));
                if (vatTuKhos != null){
                    thongKeKhoAdapter = new ThongKeKhoAdapter(ActivityThongKe.this, R.layout.item_vat_tu_thong_ke, vatTuKhos);
                    lvDSVatTuKho.setAdapter(thongKeKhoAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnXuatPDFThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();

                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();

                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTextSize(12.0f);
                myPaint.setColor(Color.BLACK);
                myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawText("THỐNG KÊ KHO",myPageInfo1.getPageWidth()/2, 30,myPaint);

                myPaint.setTextSize(8.0f);
                myPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Mã kho:    " + kho.getMaKho(),myPageInfo1.getPageWidth()/2-100, 50, myPaint);
                canvas.drawText("Tên kho:  " + kho.getTenKho(), myPageInfo1.getPageWidth()/2, 50, myPaint);
                canvas.drawLine(60,50,100,50, myPaint);
                canvas.drawLine(160,50,230,50, myPaint);

                myPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("DANH SÁCH VẬT TƯ",myPageInfo1.getPageWidth()/2,70,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(6.0f);
                int xStart = 20, xStop = 230 , yStart = 80, yStop = 90;
                canvas.drawLine(xStart, yStart, xStop, yStart,myPaint);
                canvas.drawText("Mã VT", xStart+2,yStart+8,myPaint);
                canvas.drawText("Tên Vật Tư", xStart+60,yStart+8,myPaint);
                canvas.drawText("Đơn vị", xStart+155,yStart+8,myPaint);
                canvas.drawText("Số lượng", xStart+184,yStart+8,myPaint);
                canvas.drawLine(xStart,yStart,xStart,yStop, myPaint);
                canvas.drawLine(xStart+30,yStart,xStart+30,yStop, myPaint);
                canvas.drawLine(xStart+150,yStart,xStart+150,yStop, myPaint);
                canvas.drawLine(xStart+180,yStart,xStart+180,yStop, myPaint);
                canvas.drawLine(xStart+210,yStart,xStart+210,yStop, myPaint);
                canvas.drawLine(xStart, yStop, xStop, yStop,myPaint);

                for (VatTuPhieuNhap vTPN: vatTuKhos) {
                    yStart=yStart+10;
                    yStop=yStop+10;
                    canvas.drawText(vTPN.getMaVT().toString(), xStart+2,yStart+8,myPaint);
                    canvas.drawText(vTPN.getTenVT().toString(), xStart+60,yStart+8,myPaint);
                    canvas.drawText(vTPN.getdV().toString(), xStart+155,yStart+8,myPaint);
                    canvas.drawText(vTPN.getsL().toString(), xStart+184,yStart+8,myPaint);
                    canvas.drawLine(xStart,yStart,xStart,yStop, myPaint);
                    canvas.drawLine(xStart+30,yStart,xStart+30,yStop, myPaint);
                    canvas.drawLine(xStart+150,yStart,xStart+150,yStop, myPaint);
                    canvas.drawLine(xStart+180,yStart,xStart+180,yStop, myPaint);
                    canvas.drawLine(xStart+210,yStart,xStart+210,yStop, myPaint);
                    canvas.drawLine(xStart, yStop, xStop, yStop,myPaint);
                }




                myPdfDocument.finishPage(myPage1);


                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/"+kho.getTenKho()+".pdf");
                try {
                    if(file.exists()){
                        file.delete();
                    }
                    myPdfDocument.writeTo(new FileOutputStream(file));
                    Toast toast = Toast.makeText(ActivityThongKe.this, "Tao PDF thanh cong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }catch (IOException e)
                {
                    Toast toast = Toast.makeText(ActivityThongKe.this, "Tao PDF  khong thanh cong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                myPdfDocument.close();
            }
        });

        imgThoatThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public void getDL() {

    }

    private void setControls() {
        spKhoThongKe = findViewById(R.id.spKhoThongKe);
        imgThoatThongKe = findViewById(R.id.imgThoatThongKe);
        lvDSVatTuKho = findViewById(R.id.lvDSVatTuThongKe);
        btnXuatPDFThongKe = findViewById((R.id.btnXuatPDFThongKe));
    }

    public ArrayList<Kho> getAllKho() {
        return MainActivity.dbVatTu.getAllKho();
    }
}
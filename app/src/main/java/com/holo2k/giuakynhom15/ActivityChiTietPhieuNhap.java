package com.holo2k.giuakynhom15;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.holo2k.giuakynhom15.adapter.ChonVatTuChiTietPhieuAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuPhieuNhapAdapter;
import com.holo2k.giuakynhom15.model.PhieuNhap;
import com.holo2k.giuakynhom15.model.VatTu;
import com.holo2k.giuakynhom15.model.VatTuPhieuNhap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityChiTietPhieuNhap extends AppCompatActivity {
    Button btnThemVTChiTietPhieuNhap, btnXoaChiTietPhieuNhap, btnThemVTPN, btnLuuPhieuChiTiet, btnXuatPDFChiTietPhieuNhap;
    TextView tvNgayNhapPhieuChiTiet, tvMaPhieuChiTiet, tvTenKhoPhieuNhapChiTiet;
    ImageView imgThoatPhieuNhapChiTiet;
    public static ListView lvDSVatTuChiTietPhieu;
    public static VatTuPhieuNhapAdapter vatTuPhieuNhapAdapter;
    Dialog dialog;
    ListView lvChonVatTu;
    ChonVatTuChiTietPhieuAdapter chonVatTuAdapter;
    ImageView imgThoatChonVT;
    public static ArrayList<VatTu> vatTus = new ArrayList<>();
    public static ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps = new ArrayList<>();
    public static ArrayList<VatTu> viTriCB = new ArrayList<>();
    public static PhieuNhap phieuNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu_nhap);
        ActivityCompat.requestPermissions(ActivityChiTietPhieuNhap.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        setControl();
        setEvents();
    }

    private void setEvents() {
        layDL();
        removeVatTuTrongChonVT(vatTuPhieuNhaps);
        imgThoatPhieuNhapChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemVTChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vatTus.size() == 0) {
                    Toast.makeText(ActivityChiTietPhieuNhap.this, "Không còn vật tư để thêm", Toast.LENGTH_LONG).show();
                } else {
                    dialogChonVT();
                }
            }
        });
        btnXoaChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogXoa();
            }
        });
        btnLuuPhieuChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vatTuPhieuNhaps.size() != 0 || vatTuPhieuNhaps != null) {
                    for (VatTuPhieuNhap vatTuPhieuNhap : vatTuPhieuNhaps) {
                        if (MainActivity.dbVatTu.checkExitOfCTPN(phieuNhap.getMaPhieuNhap(), vatTuPhieuNhap.getMaVT())) {
                            MainActivity.dbVatTu.suaChiTietPhieuNhap(phieuNhap.getMaPhieuNhap(), vatTuPhieuNhap);
                        } else {
                            MainActivity.dbVatTu.themChiTietPhieuNhap(phieuNhap.getMaPhieuNhap(), vatTuPhieuNhap);
                        }
                    }
                }
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }
        });
        btnXuatPDFChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
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
                canvas.drawText("THÔNG TIN NHẬP KHO", myPageInfo1.getPageWidth() / 2, 30, myPaint);

                myPaint.setTextSize(8.0f);
                myPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Mã kho:   " + phieuNhap.getMaKho(), myPageInfo1.getPageWidth() / 2 - 100, 50, myPaint);
                canvas.drawText("Tên kho:   " + tvTenKhoPhieuNhapChiTiet.getText(), myPageInfo1.getPageWidth() / 2, 50, myPaint);
                canvas.drawText("Số phiếu nhập:   " + tvMaPhieuChiTiet.getText(), myPageInfo1.getPageWidth() / 2 - 100, 70, myPaint);
                canvas.drawText("Ngày nhập phiếu:   " + tvNgayNhapPhieuChiTiet.getText(), myPageInfo1.getPageWidth() / 2 - 100, 90, myPaint);
                canvas.drawLine(60, 51, 100, 51, myPaint);
                canvas.drawLine(160, 51, 230, 51, myPaint);
                canvas.drawLine(80, 71, 230, 71, myPaint);
                canvas.drawLine(90, 91, 130, 91, myPaint);

                myPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("DANH SÁCH VẬT TƯ", myPageInfo1.getPageWidth() / 2, 105, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);

                myPaint.setTextSize(6.0f);
                int xStart = 20, xStop = 230, yStart = 115, yStop = 125;
                canvas.drawLine(xStart, yStart, xStop, yStart, myPaint);
                canvas.drawText("Mã VT", xStart + 2, yStart + 8, myPaint);
                canvas.drawText("Tên Vật Tư", xStart + 60, yStart + 8, myPaint);
                canvas.drawText("Đơn vị", xStart + 155, yStart + 8, myPaint);
                canvas.drawText("Số lượng", xStart + 184, yStart + 8, myPaint);
                canvas.drawLine(xStart, yStart, xStart, yStop, myPaint);
                canvas.drawLine(xStart + 30, yStart, xStart + 30, yStop, myPaint);
                canvas.drawLine(xStart + 150, yStart, xStart + 150, yStop, myPaint);
                canvas.drawLine(xStart + 180, yStart, xStart + 180, yStop, myPaint);
                canvas.drawLine(xStart + 210, yStart, xStart + 210, yStop, myPaint);
                canvas.drawLine(xStart, yStop, xStop, yStop, myPaint);

                for (VatTuPhieuNhap vTPN : vatTuPhieuNhaps) {
                    yStart = yStart + 10;
                    yStop = yStop + 10;
                    canvas.drawText(vTPN.getMaVT().toString(), xStart + 2, yStart + 8, myPaint);
                    canvas.drawText(vTPN.getTenVT().toString(), xStart + 60, yStart + 8, myPaint);
                    canvas.drawText(vTPN.getdV().toString(), xStart + 155, yStart + 8, myPaint);
                    canvas.drawText(vTPN.getsL().toString(), xStart + 184, yStart + 8, myPaint);
                    canvas.drawLine(xStart, yStart, xStart, yStop, myPaint);
                    canvas.drawLine(xStart + 30, yStart, xStart + 30, yStop, myPaint);
                    canvas.drawLine(xStart + 150, yStart, xStart + 150, yStop, myPaint);
                    canvas.drawLine(xStart + 180, yStart, xStart + 180, yStop, myPaint);
                    canvas.drawLine(xStart + 210, yStart, xStart + 210, yStop, myPaint);
                    canvas.drawLine(xStart, yStop, xStop, yStop, myPaint);
                }


                myPdfDocument.finishPage(myPage1);


                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + tvMaPhieuChiTiet.getText() + ".pdf");
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    myPdfDocument.writeTo(new FileOutputStream(file));
                    Toast toast = Toast.makeText(ActivityChiTietPhieuNhap.this, "Tao PDF thanh cong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(ActivityChiTietPhieuNhap.this, "Tao PDF  khong thanh cong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                myPdfDocument.close();
            }
        });

    }

    private void dialogXoa() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_xoa);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //tắt click ngoài là thoát
        dialog.setCanceledOnTouchOutside(false);

        Button btnCo = dialog.findViewById(R.id.btnCo);
        Button btnKhong = dialog.findViewById(R.id.btnKhong);

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                showXoaKhongThanhCong(MainActivity.dbVatTu.deletePhieuNhap(tvMaPhieuChiTiet.getText().toString()));

            }
        });

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void showXoaKhongThanhCong(int check) {
        System.out.println("\n\n\n" + check + "\n\n\n\n");
        if (check != 1) {
            Toast.makeText(this, "Phiếu nhập đã có trong phiếu nhập\n Không thể xoá!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
        }
    }

    public void removeVatTuTrongChonVT(ArrayList<VatTuPhieuNhap> vatTuPhieuNhaps) {
        for (VatTuPhieuNhap vatTuPhieuNhap : vatTuPhieuNhaps) {
            for (int i = 0; i < vatTus.size(); i++) {
                if (vatTuPhieuNhap.getMaVT().equals(vatTus.get(i).getMaVatTu())) {
                    System.out.println("\n\n\n\n\n\n\n\n Mã VTPN: " + vatTuPhieuNhap.getMaVT() + "\n\n\n\n\n Mã VT: " + vatTus.get(i).getMaVatTu());
                    vatTus.remove(vatTus.get(i));
                }
            }
        }
    }

    public void layDL() {
        phieuNhap = (PhieuNhap) getIntent().getSerializableExtra("chitietphieunhap");
        tvTenKhoPhieuNhapChiTiet.setText(MainActivity.dbVatTu.getTenKhotrongChiTietPhieuNhap(phieuNhap.getMaKho()));
        tvMaPhieuChiTiet.setText(phieuNhap.getMaPhieuNhap());
        tvNgayNhapPhieuChiTiet.setText(phieuNhap.getNgayNhapPhieu());
        vatTuPhieuNhaps = MainActivity.dbVatTu.getChiTietPhieuNhap(phieuNhap.getMaPhieuNhap());
        vatTus = MainActivity.dbVatTu.getAllVatTu();
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
        btnThemVTChiTietPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setControl() {
        tvNgayNhapPhieuChiTiet = findViewById(R.id.tvNgayNhapPhieuChiTiet);
        btnThemVTChiTietPhieuNhap = findViewById(R.id.btnThemVTChiTietPhieuNhap);
        btnXoaChiTietPhieuNhap = findViewById(R.id.btnXoaChiTietPhieuNhap);
        tvMaPhieuChiTiet = findViewById(R.id.tvMaPhieuChiTiet);
        tvTenKhoPhieuNhapChiTiet = findViewById(R.id.tvTenKhoPhieuNhapChiTiet);
        imgThoatPhieuNhapChiTiet = findViewById(R.id.imgThoatPhieuNhapChiTiet);
        lvDSVatTuChiTietPhieu = findViewById(R.id.lvDSVatTuChiTietPhieu);
        btnLuuPhieuChiTiet = findViewById(R.id.btnLuuPhieuChiTiet);
        btnXuatPDFChiTietPhieuNhap = findViewById(R.id.btnXuatPDFChiTietPhieuNhap);
    }

    public void layDLVT(Intent data) {
        VatTuPhieuNhap vatTuPhieuNhap = (VatTuPhieuNhap) data.getSerializableExtra("vat_tu_phieu_nhap");
        vatTuPhieuNhaps.add(vatTuPhieuNhap);
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
    }

    private void dialogChonVT() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chon_vat_tu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //tắt click ngoài là thoát'

        dialog.setCanceledOnTouchOutside(false);
        lvChonVatTu = dialog.findViewById(R.id.lvChonVatTu);
        btnThemVTPN = dialog.findViewById(R.id.btnThemVTPN);
        imgThoatChonVT = dialog.findViewById(R.id.imgThoatChonVTTrongPhieu);
        chonVatTuAdapter = new ChonVatTuChiTietPhieuAdapter(
                dialog.getContext(),
                R.layout.item_vat_tu_check_box,
                vatTus);
        lvChonVatTu.setAdapter(chonVatTuAdapter);
        lvChonVatTu.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChonVatTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(dialog.getContext(), "Bấm dô ròi nè", Toast.LENGTH_LONG);
            }
        });
        imgThoatChonVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnThemVTPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                themVaoVTPN(viTriCB);
                xoaVTDaChon(viTriCB);
                viTriCB.clear();
            }
        });
    }

    public void themVaoVTPN(ArrayList<VatTu> viTri) {
        for (VatTu vt : viTri) {
            VatTuPhieuNhap vatTuPhieuNhap = new VatTuPhieuNhap(vt.getMaVatTu(), vt.getTenVatTu(), "-", "0");
            vatTuPhieuNhaps.add(vatTuPhieuNhap);
        }
        vatTuPhieuNhapAdapter = new VatTuPhieuNhapAdapter(this, R.layout.item_vat_tu_them_phieu, vatTuPhieuNhaps);
        lvDSVatTuChiTietPhieu.setAdapter(vatTuPhieuNhapAdapter);
    }

    public void xoaVTDaChon(ArrayList<VatTu> viTri) {
        for (VatTu vt : viTri) {
            vatTus.remove(vt);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6) {
            if (resultCode == 1) {
                layDLVT(data);
            }
        }
    }
}
package com.holo2k.giuakynhom15.model;

import java.io.Serializable;
import java.util.Date;


public class PhieuNhap implements Serializable {
    private String maPhieuNhap;
    private String maKho;
    private Date ngayNhapPhieu;

    public PhieuNhap() {

    }

    public PhieuNhap(String maPhieuNhap, String maKho, Date ngayNhapPhieu) {
        this.maPhieuNhap = maPhieuNhap;
        this.maKho = maKho;
        this.ngayNhapPhieu = ngayNhapPhieu;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public Date getNgayNhapPhieu() {
        return ngayNhapPhieu;
    }

    public void setNgayNhapPhieu(Date ngayNhapPhieu) {
        this.ngayNhapPhieu = ngayNhapPhieu;
    }
}

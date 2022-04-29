package com.holo2k.giuakynhom15.model;

import java.io.Serializable;
import java.util.Date;


public class PhieuNhap implements Serializable {
    private String maPhieuNhap;
    private int maKho;
    private String ngayNhapPhieu;

    public PhieuNhap() {

    }

    public PhieuNhap(String maPhieuNhap, int maKho, String ngayNhapPhieu) {
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

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public String getNgayNhapPhieu() {
        return ngayNhapPhieu;
    }

    public void setNgayNhapPhieu(String ngayNhapPhieu) {
        this.ngayNhapPhieu = ngayNhapPhieu;
    }
}

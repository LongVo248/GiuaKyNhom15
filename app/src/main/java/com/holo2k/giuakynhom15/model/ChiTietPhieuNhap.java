package com.holo2k.giuakynhom15.model;

import java.io.Serializable;

public class ChiTietPhieuNhap implements Serializable {
    private String maPhieuNhap;
    private String maVatTu;
    private String dvt;
    private String soLuong;

    public ChiTietPhieuNhap(String maPhieuNhap, String maVatTu, String dvt, String soLuong) {
        this.maPhieuNhap = maPhieuNhap;
        this.maVatTu = maVatTu;
        this.dvt = dvt;
        this.soLuong = soLuong;
    }

    public ChiTietPhieuNhap() {
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}
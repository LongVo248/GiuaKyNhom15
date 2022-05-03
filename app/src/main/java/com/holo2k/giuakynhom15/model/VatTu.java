package com.holo2k.giuakynhom15.model;

import android.net.Uri;

import java.io.Serializable;

public class VatTu implements Serializable {
    private String maVatTu, tenVatTu, xuatXu;
    private Uri uri;
    private byte[] hinhAnh;

    public VatTu() {
    }

    public VatTu(String maVatTu, String tenVatTu, String xuatXu) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
    }

    public VatTu(String maVatTu, String tenVatTu, String xuatXu, byte[] hinhAnh) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
        this.hinhAnh = hinhAnh;
    }

    public VatTu(String maVatTu, String tenVatTu, String xuatXu, Uri uri) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public String getTenVatTu() {
        return tenVatTu;
    }

    public void setTenVatTu(String tenVatTu) {
        this.tenVatTu = tenVatTu;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}

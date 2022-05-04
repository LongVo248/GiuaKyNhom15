package com.holo2k.giuakynhom15.model;

import android.net.Uri;

import java.io.Serializable;

public class VatTu implements Serializable {
    private String maVatTu, tenVatTu, xuatXu;
    private String uri;

    public VatTu() {
    }

    public VatTu(String maVatTu, String tenVatTu, String xuatXu) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
    }

    public VatTu(String maVatTu, String tenVatTu, String xuatXu, String uri) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
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


}

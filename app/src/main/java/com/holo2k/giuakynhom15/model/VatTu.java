package com.holo2k.giuakynhom15.model;

import java.io.Serializable;

public class VatTu implements Serializable {
   private String maVatTu, tenVatTu, xuatXu;

    public VatTu() {
    }

    public VatTu(String tenVatTu, String xuatXu) {
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
    }

    public VatTu(String maVatTu, String tenVatTu, String xuatXu) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.xuatXu = xuatXu;
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

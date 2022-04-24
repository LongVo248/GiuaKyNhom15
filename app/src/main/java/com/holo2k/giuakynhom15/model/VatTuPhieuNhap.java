package com.holo2k.giuakynhom15.model;

import java.io.Serializable;

public class VatTuPhieuNhap  implements Serializable {
    String maVT, tenVT, dV, sL;

    public VatTuPhieuNhap(String maVT, String tenVT, String dV, String sL) {
        this.maVT = maVT;
        this.tenVT = tenVT;
        this.dV = dV;
        this.sL = sL;
    }

    public VatTuPhieuNhap() {
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getTenVT() {
        return tenVT;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    public String getdV() {
        return dV;
    }

    public void setdV(String dV) {
        this.dV = dV;
    }

    public String getsL() {
        return sL;
    }

    public void setsL(String sL) {
        this.sL = sL;
    }
}


package com.holo2k.giuakynhom15.model;

import java.io.Serializable;

public class Kho implements Serializable {
    private int  maKho;
    private String tenKho;

    public Kho() {
    }

    public Kho(int maKho, String tenKho) {
        this.maKho = maKho;
        this.tenKho = tenKho;
    }

    public Kho(String tenKho) {
        this.tenKho = tenKho;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }
}

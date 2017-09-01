package com.vkr.ksenija_i.IN_OUT;

public class Item {
    private String fio;
    private String date;
    private String time;
    private String vhod;

    public Item(String fio, String date, String time, String vhod) {
        this.fio = fio;
        this.date = date;
        this.time = time;
        this.vhod = vhod;
    }

    public String getFio() {
        return fio;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getVhod() {
        return vhod;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVhod(String vhod) {
        this.vhod = vhod;
    }
}

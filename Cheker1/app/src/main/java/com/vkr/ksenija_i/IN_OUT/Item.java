package com.vkr.ksenija_i.IN_OUT;

 class Item {
    private String fio;
    private String date;
    private String time;
    private String vhod;

     Item(String fio, String date, String time, String vhod) {
        this.fio = fio;
        this.date = date;
        this.time = time;
        this.vhod = vhod;
    }

    String getFio() {
        return fio;
    }

    String getDate() {
        return date;
    }

    String getTime() {
        return time;
    }

    String getVhod() {
        return vhod;
    }

    void setFio(String fio) {
        this.fio = fio;
    }

    void setDate(String date) {
        this.date = date;
    }

    void setTime(String time) {
        this.time = time;
    }

    void setVhod(String vhod) {
        this.vhod = vhod;
    }
}

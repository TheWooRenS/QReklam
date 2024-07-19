package com.example.qreklam_final;

public class My_Models {
    String email, name;
    int puan;

    public My_Models(String email, String name, int puan) {
        this.email = email;
        this.name = name;
        this.puan = puan;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

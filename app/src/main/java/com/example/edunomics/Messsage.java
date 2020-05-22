package com.example.edunomics;

public class Messsage {
    String msg;
    String email;

    public Messsage() {
    }

    public Messsage(String msg, String email) {
        this.msg = msg;
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

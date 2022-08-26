package com.example.demo.exception;

import java.util.Date;

public class CommonErrorResponse {
    private Date date;
    private String msg;

    public CommonErrorResponse(String msg) {
        this.msg = msg;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

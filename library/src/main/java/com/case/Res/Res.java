package com.jxl.Res;

import lombok.Data;

@Data
public class Res {
    private  int code;
    private  String message;
    private Object data;

    public static Res res (int code, String message, Object data) {
        Res res = new Res();
        res.setCode(code);
        res.setData(data);
        res.setMessage(message);
        return res;
    }
    public static Res okRes(String message, Object data) {
        return Res.res(200, message,data);
    }
    public static Res noRes(String message, Object data) {
        return Res.res(400, message,data);
    }
}

package com.ad.constants;

public enum ErrorEnum {
    INVALID_CREDENTIAL(10001,"Incorrect Credentials!!"),
    NOT_FOUND(10002,"Resource Not found"),
    BAD_REQUEST(10003,"Request is not correct");
    int code;
    String msg;
    ErrorEnum(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

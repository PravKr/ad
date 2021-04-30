package com.ad.core;

import com.ad.constants.ErrorEnum;

public class SMException extends RuntimeException {
    ErrorEnum errorEnum;
    public SMException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.errorEnum=errorEnum;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }
}

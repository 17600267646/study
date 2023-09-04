package com.person.study.code;

import cn.hutool.core.date.DatePattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}

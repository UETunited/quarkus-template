package com.uu.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private String errorCode;
    private String message;
    private T data;

    public static <T> CommonResponse success(T data) {
        return CommonResponse.builder().message("SUCCESS").data(data).build();
    }
}

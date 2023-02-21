package com.macro.mall.tiny.common.api;

/**
 * 封装API的错误码
 *
 * @author lwp
 * @date 2023/2/21 19:20
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}

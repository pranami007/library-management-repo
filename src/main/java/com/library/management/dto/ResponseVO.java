package com.library.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties
public class ResponseVO<T> {

    private Integer code;
    private String message;
    private T result;
    private List<Map<String, String>> errors;

    public static <T> ResponseVO<T> create(Integer code, String message, T data) {
        ResponseVO<T> vo = new ResponseVO<>();
        vo.code = code;
        vo.message = message;
        vo.result = data;
        vo.errors = Collections.emptyList();
        return vo;
    }

    public static <T> ResponseVO<T> create(Integer code, T data) {
        ResponseVO<T> vo = new ResponseVO<>();
        vo.code = code;
        vo.message = StringUtils.EMPTY;
        vo.result = data;
        vo.errors = Collections.emptyList();
        return vo;
    }

    public static ResponseVO<Void> create(Integer code, String message) {
        ResponseVO<Void> vo = new ResponseVO<>();
        vo.code = code;
        vo.message = message;
        vo.errors = Collections.emptyList();
        return vo;
    }

    public static ResponseVO<Void> create(Integer code, String message, List<Map<String, String>> errors) {
        ResponseVO<Void> vo = new ResponseVO<>();
        vo.code = code;
        vo.message = message;
        vo.errors = errors;
        return vo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }

}

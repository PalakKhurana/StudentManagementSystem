package com.studentmanagement.dao;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ResponseCode<T> {
    private Integer statusCode;
    private String message;
    private  T data;

    public ResponseCode(int statusCode,String message,T data){
        this.statusCode=statusCode;
        this.message=message;
        this.data=data;
    }
    public static<T> ResponseCode<T> success(String message,T data){
        return new ResponseCode<>(200,message,data);
    }
    public static<T> ResponseCode<T> error(int statusCode,String message){
        return new ResponseCode<>(statusCode,message,null);
    }
    public static<T> ResponseCode<T> notFound(int statusCode,String message){
        return new ResponseCode<>(404
                ,message,null);
    }
}




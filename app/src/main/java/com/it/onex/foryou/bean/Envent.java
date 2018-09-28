package com.it.onex.foryou.bean;

/**
 * Created by OnexZgj on 2018/9/28.
 * 事件传递Bean
 */

public class Envent {
    private int code;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Envent(int code, Object data) {
        this.code = code;
        this.data = data;
    }
}

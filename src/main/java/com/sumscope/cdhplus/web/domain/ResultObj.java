package com.sumscope.cdhplus.web.domain;

/**
 * Created by wenshuai.li on 2016/10/17.
 */
import java.io.Serializable;
import java.util.List;

public class ResultObj implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -9154388667003336179L;

    private boolean status;

    private String msg;


    public ResultObj() {

    }

    public ResultObj(String cmd, boolean status) {
        this.status = status;
    }

    public ResultObj(String cmd, boolean status, String msg, String id, Object obj) {
        this.status = status;
        this.msg = msg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

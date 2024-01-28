package org.example.gateway.core.executor.result;

public class SessionResult {

    private String code;
    private String info;
    private Object data;

    protected SessionResult(String code, String info, Object data) {
        this.code = code;
        this.info = info;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public Object getData() {
        return data;
    }

    public static SessionResult buildSuccess(Object data) {
        return new SessionResult("0000", "success", data);
    }

    public static SessionResult buildError(Object data) {
        return new SessionResult("0001", "error", data);
    }
}

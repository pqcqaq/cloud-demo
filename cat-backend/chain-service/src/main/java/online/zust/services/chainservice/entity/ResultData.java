package online.zust.services.chainservice.entity;

import lombok.Data;

/**
 * @author qcqcqc
 */
@Data
public class ResultData<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> ResultData<T> success(int code, String msg, T data) {
        ResultData<T> result = new ResultData<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ResultData<T> success() {
        return success(200, null, null);
    }

    public static <T> ResultData<T> success(String msg) {
        return success(200, msg, null);
    }

    public static <T> ResultData<T> success(T date) {
        return success(200, "success", date);
    }

    public static <T> ResultData<T> success(String msg, T data) {
        return success(200, msg, data);
    }

    public static <T> ResultData<T> error(int code, String msg, T data) {
        ResultData<T> result = new ResultData<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ResultData<T> error(int code) {
        return error(code, null, null);
    }

    public static <T> ResultData<T> error(int code, String msg) {
        return error(code, msg, null);
    }

    public static <T> ResultData<T> error() {
        return error(404, null, null);
    }
}

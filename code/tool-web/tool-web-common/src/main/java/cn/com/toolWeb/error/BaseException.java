package cn.com.toolWeb.error;

import lombok.Data;

/**
 * 基础异常类
 */
@Data
public class BaseException extends RuntimeException {
    /** 异常码 */
    private Integer code;
    /** 异常信息 */
    private String msg;
    /** 参数 */
    private Object[] params;

    /**
     * 构造方法
     *
     * @param errorCode 异常码
     */
    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorDesc());
        this.code = errorCode.getErrorCode();
        this.msg = errorCode.getErrorDesc();
    }

    /**
     * 构造方法
     *
     * @param errorCode 异常码
     */
    public BaseException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getErrorCode();
        this.msg = msg;
    }

    /**
     * 构造方法
     *
     * @param code 异常码
     * @param msg 异常信息
     */
    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造方法
     *
     * @param code 异常码
     * @param msg 异常信息
     * @param params 参数
     */
    public BaseException(Integer code, String msg, Object[] params) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.params = params;
    }
}

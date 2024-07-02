package cn.com.toolWeb.error;

/**
 * 基础异常类
 */
public class ExceptionUtil {

    public static void cast(ErrorCode errorCode) {
        throw new BaseException(errorCode);
    }

    public static void cast(ErrorCode errorCode, String message) {
        throw new BaseException(errorCode, message);
    }

    public static void cast(ErrorCode errorCode, String message, Object[] params) {
        BaseException exception = new BaseException(errorCode.getErrorCode(), message, params);
        throw exception;
    }

    /**
     * 参数异常
     */
    public static void illegalParameter() {
        cast(CommonErrorCode.VALID_ERROR);
    }

    /**
     * 参数异常
     */
    public static void illegalParameter(String message) {
        cast(CommonErrorCode.VALID_ERROR, message);
    }

    /**
     * 参数查询不到
     */
    public static void notFound() {
        cast(CommonErrorCode.OBJECT_NOTEXISTS);
    }

    /**
     * 参数查询不到
     */
    public static void notFound(String message) {
        cast(CommonErrorCode.OBJECT_NOTEXISTS, message);
    }

    /**
     * 系统异常
     * @param message
     */
    public static void throwError(String message) {
        cast(CommonErrorCode.SERVER_ERROR, message);
    }
}

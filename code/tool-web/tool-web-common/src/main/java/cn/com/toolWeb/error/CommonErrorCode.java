//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.toolWeb.error;

/**
 * 异常信息枚举
 */
public enum CommonErrorCode implements ErrorCode {
    /** 参数非法 */
    INVALID_PARAM(10003, "参数非法！"),
    /** 对象不存在 */
    OBJECT_NOTEXISTS(10004, "对象不存在！"),
    /** 不符合校验 */
    VALID_ERROR(10005, "不符合校验！"),
    /** 查无数据 */
    NO_DATA_FOUND(10006, "查无数据！"),
    /** 非法操作 */
    FORBIDDEN(10007, "非法操作！"),
    /** 操作失败 */
    FAIL(90000, "操作失败！"),
    /** 系统繁忙，请稍后重试 */
    SERVER_ERROR(99999, "抱歉，系统繁忙，请稍后重试！");

    /** 异常码 */
    Integer errorCode;
    /**  描述 */
    String errorDesc;

    /** 构造方法 */
    private CommonErrorCode(int errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorDesc() {
        return this.errorDesc;
    }
}

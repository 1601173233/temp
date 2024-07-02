package cn.com.toolWeb.error;

/**
 * 异常码枚举
 */
public interface ErrorCode {

  /** 异常码 */
  Integer getErrorCode();

  /** 异常描述 */
  String getErrorDesc();
}

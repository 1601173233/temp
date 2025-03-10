package cn.com.toolWeb.constant;

public enum CodeEnum {
  SUCCESS(0),
  ERROR(1);

  private Integer code;

  CodeEnum(Integer code) {
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}

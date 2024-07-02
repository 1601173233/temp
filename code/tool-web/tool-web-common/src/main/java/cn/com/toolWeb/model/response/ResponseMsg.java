package cn.com.toolWeb.model.response;

import cn.com.toolWeb.constant.CodeEnum;
import cn.com.toolWeb.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

/**
 * 返回值.
 * 兼容swagger显示
 *
 * @author hyj
 * @version 1.0
 * @since  2020/5/15 10:24
 * 分页实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg {
  /** 成功返回值 . */
  static final String SUCCESS_MSG = "success";
  /** 失败返回值 . */
  static final String FAIL_MSG = "fail";

  /** 请求ID */
  protected String requestId;

  /** 编码 */
  protected Integer code;

  /** 消息 */
  protected String msg;

  public static <T> ResponseMsg succeed() {
    return succeed(CodeEnum.SUCCESS.getCode(), "成功");
  }

  public static <T> ResponseMsg succeed(Integer code) {
    return succeed(code);
  }

  public static <T> ResponseMsg succeed(String msg) {
    return succeed(CodeEnum.SUCCESS.getCode(), msg);
  }

  public static <T> ResponseMsg succeed(Integer code, String msg) {
    String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
    return of(traceId, code, msg);
  }

  public static <T> ResponseMsg of(String requestId, Integer code, String msg) {
    return new ResponseMsg(requestId, code, msg);
  }
}

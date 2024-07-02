package cn.com.toolWeb.model.response;

import cn.com.toolWeb.constant.CodeEnum;
import cn.com.toolWeb.constant.CommonConstant;
import cn.com.toolWeb.model.base.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

import java.util.List;

/**
 * 分页返回值.
 * 兼容swagger显示
 *
 * @param <T> 泛型类
 * @author hyj
 * @version 1.0
 * @since  2020/5/15 10:24
 * 分页实体类
 */
@Setter
@Getter
public class PageResponseMsg<T> extends ResponseDataMsg<T>{
  /** 总行数 */
  private long count;

  public PageResponseMsg(String requestId, Integer code, String msg, T data, Long count) {
    super(requestId, code, msg, data);
    this.count = count;
  }

  /**
   * 返回成功.
   *
   * @param <T> 泛型类
   * @param list 列表数据
   * @return 请求应答
   */
  public static <T> PageResponseMsg succeed(List<T> list) {
    String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
    if (list == null) {
      return PageResponseMsg.plusOf(traceId, CodeEnum.SUCCESS.getCode(), SUCCESS_MSG, list, 0);
    } else {
      return PageResponseMsg.plusOf(traceId, CodeEnum.SUCCESS.getCode(), SUCCESS_MSG, list, list.size());
    }
  }

  /**
   * 返回成功.
   *
   * @param <T> 泛型类
   * @param page 分页返回结果
   * @return 请求应答
   */
  public static <T> PageResponseMsg succeed(IPage<T> page) {
    String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
    return plusPageOf(traceId, CodeEnum.SUCCESS.getCode(), SUCCESS_MSG, page.getRecords(), page.getTotal());
  }

  /**
   * 返回成功.
   *
   * @param <T> 泛型类
   * @param page 分页返回结果
   * @return 请求应答
   */
  public static <T> PageResponseMsg succeed(PageResult<T> page) {
    String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
    return plusPageOf(traceId, CodeEnum.SUCCESS.getCode(), SUCCESS_MSG, page.getData(), page.getCount());
  }

  /**
   * 构造返回值.
   *
   * @param <T> 泛型类
   * @param requestId 请求ID
   * @param code 返回码
   * @param msg 返回信息
   * @param data 返回数据
   * @param count 列表数据总数
   * @return 请求应答
   */
  public static <T> PageResponseMsg plusPageOf(String requestId, Integer code, String msg, T data, Long count) {
    return new PageResponseMsg<>(requestId, code, msg, data, count);
  }

  /**
   * 构造返回值.
   *
   * @param <T> 泛型类
   * @param requestId 请求ID
   * @param code 返回码
   * @param msg 返回信息
   * @param data 返回数据
   * @param count 列表数据总数
   * @return 请求应答
   */
  public static <T> PageResponseMsg plusOf(String requestId, Integer code, String msg, T data, Integer count) {
    return plusPageOf(requestId, code, msg, data, count.longValue());
  }
}

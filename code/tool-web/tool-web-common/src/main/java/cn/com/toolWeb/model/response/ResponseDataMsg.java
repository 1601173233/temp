package cn.com.toolWeb.model.response;

import cn.com.toolWeb.constant.CodeEnum;
import cn.com.toolWeb.constant.CommonConstant;
import lombok.Data;
import org.slf4j.MDC;

/**
 * 带参实体类.
 * 兼容swagger显示
 *
 * @param <T> 泛型类
 * @author zrhong
 * @version 1.0
 * @since  2020/5/15 10:14
 * 带参实体类
 */
@Data
public class ResponseDataMsg<T> extends ResponseMsg {
    protected T data;

    public ResponseDataMsg(String requestId, Integer code, String msg, T data) {
        super(requestId, code, msg);
        this.data = data;
    }

    /**
     * 返回成功.
     *
     * @param data      返回数据
     * @return 请求应答
     */
    public static ResponseDataMsg<String> succeed(String data) {
        String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
        return dataOf(traceId, CodeEnum.SUCCESS.getCode(), SUCCESS_MSG, data);
    }

    /**
     * 返回成功.
     *
     * @param <T> 泛型类
     * @param data      返回数据
     * @return 请求应答
     */
    public static <T> ResponseDataMsg<T> succeed(T data) {
        String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
        return dataOf(traceId, CodeEnum.SUCCESS.getCode(), SUCCESS_MSG, data);
    }

    /**
     * 返回成功.

     * @param <T> 泛型类
     * @param code 返回码
     * @param data 返回数据
     * @return 请求应答
     */
    public static <T> ResponseDataMsg<T> succeed(Integer code, T data) {
        String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
        return dataOf(traceId, code, SUCCESS_MSG, data);
    }

    /**
     * 返回成功.

     * @param <T> 泛型类
     * @param data 返回数据
     * @return 请求应答
     */
    public static <T> ResponseDataMsg<T> succeed(String msg, T data) {
        String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
        return dataOf(traceId, CodeEnum.SUCCESS.getCode(), msg, data);
    }

    /**
     * 返回成功.

     * @param <T>  泛型类
     * @param code 返回码
     * @param msg  返回信息
     * @param data 返回数据
     * @return 请求应答
     */
    public static <T> ResponseDataMsg<T> succeed(Integer code, String msg, T data) {
        String traceId = MDC.get(CommonConstant.Log.LOG_TRACE_ID);
        return dataOf(traceId, code, msg, data);
    }

    /**
     * 构造返回值.
     *
     * @param <T> 泛型类
     * @param requestId 请求ID
     * @param code 返回码
     * @param msg 返回信息
     * @param data 返回数据
     * @return 请求应答
     */
    public static <T> ResponseDataMsg<T> dataOf(String requestId, Integer code, String msg, T data) {
        return new ResponseDataMsg<>(requestId, code, msg, data);
    }
}

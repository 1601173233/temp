package cn.com.toolWeb.filter;

import cn.com.toolWeb.constant.CommonConstant;
import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 添加日志信息的拦截器
 */
@Order(1)
@WebFilter(filterName="firstFilter", urlPatterns="/*")
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		MDC.put(CommonConstant.Log.LOG_TRACE_ID, IdUtil.fastSimpleUUID());
		chain.doFilter(request, response);
	}
}
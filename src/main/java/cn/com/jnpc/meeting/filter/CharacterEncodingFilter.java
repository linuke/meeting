package cn.com.jnpc.meeting.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 用于设置 HTTP 请求字符编码的过滤器，通过过滤器参数encoding指明使用何种字符编码,用于处理Html Form请求参数的中文问题
 */
public class CharacterEncodingFilter implements Filter
{

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{

		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setContentType("text/html;charset=UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
	}
}

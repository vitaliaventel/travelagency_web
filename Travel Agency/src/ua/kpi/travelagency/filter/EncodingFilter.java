package ua.kpi.travelagency.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class EncodingFilter implements Filter {

	private static Logger logger = Logger.getLogger(EncodingFilter.class.getName());
	private FilterConfig filterConfig;
	private static final String UTF = "UTF-8";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		logger.info("Init EncodingFilter.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(UTF);
		logger.info("Set " + UTF);
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		logger.info("Filter destroy.");
	}

}

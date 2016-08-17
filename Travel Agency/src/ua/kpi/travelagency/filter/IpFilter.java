package ua.kpi.travelagency.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.dao.BlackListDAO;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class IpFilter implements Filter {

	private static Logger logger = Logger.getLogger(IpFilter.class.getName());
	private FilterConfig filterConfig;
	private BlackListDAO blackDAO = DAOFactory.createBlackListDAO();
	
	@Override
	public void destroy() {
		logger.info("Destory IpFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String ip = request.getRemoteAddr();
		HttpServletRequest hreq = (HttpServletRequest)request;
		logger.info("Current user ip - " + ip);
		for(String list : blackDAO.findAllIp()){
			if(list.equals(ip)){
				hreq.getSession().setAttribute("error", Message.getInstance().getProperty(Message.BLACK_LIST));
				String page = Config.getInstance().getProperty(Config.ERROR);
				logger.error("Banned. ");
				request.getRequestDispatcher(page).forward(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		logger.info("Init IpFilter");
	}

}

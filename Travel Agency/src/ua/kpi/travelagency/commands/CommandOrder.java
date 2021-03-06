package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Orders;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.OrdersDAO;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class CommandOrder implements ICommand {

	private static Logger logger = Logger.getLogger(CommandOrder.class.getName());
	private OrdersDAO daoOrders = DAOFactory.createOrdersDAO();
	private static final String LOGIN = "login";
	private static final String ID = "id";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		Orders order = new Orders();
		try {
			String login = (String) request.getSession(false).getAttribute(LOGIN);
			String id = request.getParameter(ID);
			order.setLogin(login);
			order.setTour(Integer.parseInt(id));
			if(daoOrders.create(order)){
			page = Config.getInstance().getProperty(Config.MAIN);
			logger.info("User: order a tour; @" + login);
			}else{
				request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.MISS_ORDER));
				page = Config.getInstance().getProperty(Config.ERROR);
				logger.error("Current tour is not available.");
			}
		} catch (NullPointerException e) {
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}
		return page;
	}

}

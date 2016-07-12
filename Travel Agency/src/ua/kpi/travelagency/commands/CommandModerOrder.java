package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.OrdersDAO;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class CommandModerOrder implements ICommand {

	private static Logger logger = Logger.getLogger(CommandModerOrder.class.getName());
	private OrdersDAO daoOrders = DAOFactory.createOrdersDAO();
	private static final String ID = "ID";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		try {
			String id = request.getParameter(ID);
			daoOrders.delete(Integer.parseInt(id));
			request.getSession(false).setAttribute("ordersList", daoOrders.findAll());
			page = Config.getInstance().getProperty(Config.MODER);
			logger.info("Moderator: order was delete id = " + id);
		} catch (NullPointerException e) {
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}
		return page;
	}

}

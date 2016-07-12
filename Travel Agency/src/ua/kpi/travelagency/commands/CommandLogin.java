package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.manager.*;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.OrdersDAO;
import ua.kpi.travelagency.dao.ToursDAO;
import ua.kpi.travelagency.dao.UsersDAO;

public class CommandLogin implements ICommand {

	private static Logger logger = Logger.getLogger(CommandLogin.class.getName());
	private UsersDAO daoUsers = DAOFactory.createUsersDAO();
	private ToursDAO daoTours = DAOFactory.createToursDAO();
	private OrdersDAO daoOrders = DAOFactory.createOrdersDAO();
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final int USERTYPE = 1;
	private static final int ADMINTYPE = 2;
	private static final int MODERTYPE = 3;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);

		try {
			if (daoUsers.find(login, password)) {
				if (daoUsers.findByLogin(login).getUserType() == MODERTYPE) {
					request.getSession(false).setAttribute("login", login);
					request.getSession(false).setAttribute("ordersList", daoOrders.findAll());
					page = Config.getInstance().getProperty(Config.MODER);
					logger.info("This is moderator.");
				} else if (daoUsers.findByLogin(login).getUserType() == ADMINTYPE) {
					request.getSession(false).setAttribute("login", login);
					request.getSession(false).setAttribute("usersList", daoUsers.findAll());
					page = Config.getInstance().getProperty(Config.ADMIN);
					logger.info("This is administrator");
				} else if (daoUsers.findByLogin(login).getUserType() == USERTYPE) {
					request.getSession(false).setAttribute("login", login);
					request.getSession(false).setAttribute("toursList", daoTours.findAll());
					page = Config.getInstance().getProperty(Config.MAIN);
					logger.info("Correct login/password!");
				}
			} else {
				request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.LOGIN_ERROR));
				page = Config.getInstance().getProperty(Config.ERROR);
				logger.info("Incorrect login/password");
			}
		} catch (NullPointerException e) {
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}
		return page;
	}
}

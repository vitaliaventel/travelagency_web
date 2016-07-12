package ua.kpi.travelagency.commands;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Users;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.ToursDAO;
import ua.kpi.travelagency.dao.UsersDAO;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class CommandRegister implements ICommand {

	private static Logger logger = Logger.getLogger(CommandRegister.class.getName());
	private UsersDAO daoUsers = DAOFactory.createUsersDAO();
	private ToursDAO daoTours = DAOFactory.createToursDAO();
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final int USERTYPE = 1;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		Users user = new Users();
		user.setLogin(login);
		user.setPassword(password);
		user.setUserType(USERTYPE);
		try {
			if (daoUsers.create(user)) {
				request.getSession(false).setAttribute("login", login);
				request.getSession(false).setAttribute("toursList", daoTours.findAll());
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Register new user.");
			} else {
				request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.REGISTER_ERROR));
				page = Config.getInstance().getProperty(Config.ERROR);
				logger.info("Current username is already use.");
			}
		} catch (NullPointerException e) {
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}
		return page;
	}

}

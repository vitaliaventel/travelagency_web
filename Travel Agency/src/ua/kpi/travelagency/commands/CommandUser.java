package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Users;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.UsersDAO;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class CommandUser implements ICommand {

	private static Logger logger = Logger.getLogger(CommandUser.class.getName());
	private UsersDAO daoUsers = DAOFactory.createUsersDAO();
	private static final String ID = "id";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String USERTYPE = "userType";
	private static final String DELETE = "delete";
	private static final String UPDATE = "update";
	private static final String READ = "read";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		Users user = new Users();
		String id = request.getParameter(ID);
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		String userType = request.getParameter(USERTYPE);
		String read = request.getParameter(READ);
		String update = request.getParameter(UPDATE);
		String delete = request.getParameter(DELETE);
		
		try {
			if (read != null) {
				user = daoUsers.read(Integer.parseInt(id));
				request.getSession(false).setAttribute("user", user);
				page = Config.getInstance().getProperty(Config.ADMIN);
				logger.info("Admin: read user id = " + id);
			} else if (update != null) {
				user.setLogin(login);
				user.setPassword(password);
				user.setUsersId(Integer.parseInt(id));
				user.setUserType(Integer.parseInt(userType));
				daoUsers.update(user);
				request.getSession(false).setAttribute("user", user);
				request.getSession(false).setAttribute("usersList", daoUsers.findAll());
				page = Config.getInstance().getProperty(Config.ADMIN);
				logger.info("Admin: update user id = " + id);
			} else if (delete != null) {
				daoUsers.delete(Integer.parseInt(id));
				request.getSession(false).setAttribute("user", user);
				request.getSession(false).setAttribute("usersList", daoUsers.findAll());
				page = Config.getInstance().getProperty(Config.ADMIN);
				logger.info("Admin: delete user id = " + id);
			}
		} catch (NullPointerException e) {
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}

		return page;
	}

}

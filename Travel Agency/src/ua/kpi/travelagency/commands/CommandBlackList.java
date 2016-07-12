package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.dao.BlackListDAO;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.manager.Config;


public class CommandBlackList implements ICommand {
	
	private static Logger logger = Logger.getLogger(CommandTour.class.getName());
	private static final String SEARCH = "search";
	private static final String ADD = "add";
	private static final String DELETE = "delete";
	private BlackListDAO blackDAO = DAOFactory.createBlackListDAO();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		String search = request.getParameter(SEARCH);
		String add = request.getParameter(ADD);
		String delete = request.getParameter(DELETE);
		if (add != null) {
			blackDAO.create(search);
			page = Config.getInstance().getProperty(Config.MODER);
			logger.info("Moderator: add new ip to blackList: ip = " + search);
		} else if (delete != null) {
			int id = Integer.parseInt(search);
			blackDAO.delete(id);
			page = Config.getInstance().getProperty(Config.MODER);
			logger.info("Moderator: delete from black list ipAdress id = " + id);
		}
		
		return page;
	}

}

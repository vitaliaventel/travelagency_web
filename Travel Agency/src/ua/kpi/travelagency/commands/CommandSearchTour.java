package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.ToursDAO;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class CommandSearchTour implements ICommand {

	private static Logger logger = Logger.getLogger(CommandSearchTour.class.getName());
	private static final String SEARCH = "search";
	private static final String MENU = "menu";
	private static final String HOT = "HOT";
	private ToursDAO daoTours = DAOFactory.createToursDAO();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		String menu = request.getParameter(MENU);
		String search = request.getParameter(SEARCH);
		try{
			if (menu.equals("first")) {
				request.getSession(false).setAttribute("toursList", daoTours.findAll());
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Show all command.");
			} else if (menu.equals("second")) {
				request.getSession(false).setAttribute("toursList", daoTours.findByCountry(search));
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Search by country!");
			} else if (menu.equals("third")) {
				request.getSession(false).setAttribute("toursList", daoTours.findByLessPrice(Double.parseDouble(search)));
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Search by lower price.");
			} else if (menu.equals("fourth")) {
				request.getSession(false).setAttribute("toursList", daoTours.findByGreaterPrice(Double.parseDouble(search)));
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Search by greater price.");
			} else if (menu.equals("fifth")) {
				request.getSession(false).setAttribute("toursList", daoTours.findBySale());
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Search by sale.");
			} else if (menu.equals("sixth")) {
				request.getSession(false).setAttribute("toursList", daoTours.findByStatus(HOT));
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Search by HOT");
			} else if (menu.equals("seventh")) {
				request.getSession(false).setAttribute("toursList", daoTours.findByTourtype(search));
				page = Config.getInstance().getProperty(Config.MAIN);
				logger.info("Search by tour type.");
			}
		}catch(NullPointerException e){
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}
		
		return page;
	}

}

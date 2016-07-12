package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Tours;
import ua.kpi.travelagency.dao.DAOFactory;
import ua.kpi.travelagency.dao.ToursDAO;
import ua.kpi.travelagency.manager.Config;
import ua.kpi.travelagency.manager.Message;

public class CommandTour implements ICommand {

	private static Logger logger = Logger.getLogger(CommandTour.class.getName());
	private ToursDAO daoTours = DAOFactory.createToursDAO();
	private static final String ID = "id";
	private static final String COUNTRY = "country";
	private static final String PRICE = "price";
	private static final String SALE = "sale";
	private static final String STATUS = "status";
	private static final String TOURTYPE = "tourtype";
	private static final String ADD = "add";
	private static final String DELETE = "delete";
	private static final String UPDATE = "update";
	private static final String READ = "read";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		String page = null;
		Tours tour = new Tours();
		String id = request.getParameter(ID);
		String country = request.getParameter(COUNTRY);
		String price = request.getParameter(PRICE);
		String sale = request.getParameter(SALE);
		String status = request.getParameter(STATUS);
		String tourType = request.getParameter(TOURTYPE);
		String add = request.getParameter(ADD);
		String read = request.getParameter(READ);
		String update = request.getParameter(UPDATE);
		String delete = request.getParameter(DELETE);
		
		try {
			if (add != null) {
				tour.setCountry(country);
				tour.setPrice(Double.parseDouble(price));
				tour.setSale(Integer.parseInt(sale));
				tour.setStatus(status);
				tour.setTourType(Integer.parseInt(tourType));
				daoTours.create(tour);
				request.getSession(false).setAttribute("tour", tour);
				page = Config.getInstance().getProperty(Config.MODER);
				logger.info("Moderator: add new tour.");
			} else if (read != null) {
				tour = daoTours.read(Integer.parseInt(id));
				request.getSession(false).setAttribute("tour", tour);
				page = Config.getInstance().getProperty(Config.MODER);
				logger.info("Moderator: read tour " + id);
			} else if (update != null) {
				tour.setIdTours(Integer.parseInt(id));
				tour.setCountry(country);
				tour.setPrice(Double.parseDouble(price));
				tour.setSale(Integer.parseInt(sale));
				tour.setStatus(status);
				tour.setTourType(Integer.parseInt(tourType));
				daoTours.update(tour);
				request.getSession(false).setAttribute("tour", tour);
				page = Config.getInstance().getProperty(Config.MODER);
				logger.info("Moderator: update tour " + id);
			} else if (delete != null) {
				daoTours.delete(Integer.parseInt(id));
				page = Config.getInstance().getProperty(Config.MODER);
				logger.info("Moderator: delete tour " + id);
			}
		} catch (NullPointerException e) {
			request.getSession().setAttribute("error", Message.getInstance().getProperty(Message.SESSION_END));
			page = Config.getInstance().getProperty(Config.ERROR);
			logger.error("Session ended ", e);
		}

		return page;
	}

}

package ua.kpi.travelagency.dao;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.connection.Database;

public class DAOFactory {
	
	private static Logger logger = Logger.getLogger(DAOFactory.class.getName());
	
	public static OrdersDAO createOrdersDAO() {
		logger.info("Creating new instance of OrdersDAO");
		return new OrdersDAOImpl(Database.getInstance());
	}
	
	public static ToursDAO createToursDAO(){
		logger.info("Creating new instance of ToursDAO");
		return new ToursDAOImpl(Database.getInstance());
	}
	
	public static UsersDAO createUsersDAO(){
		logger.info("Creating new instance of UsersDAO");
		return new UsersDAOImpl(Database.getInstance());
	}
	
	public static TourtypeDAO createTourtypeDAO(){
		logger.info("Creating new instance of TourtypeDAO");
		return new TourtypeDAOImpl(Database.getInstance());
	}
	
	public static UsertypeDAO createUsertypeDAO(){
		logger.info("Creating new instance of UsertypeDAO");
		return new UsertypeDAOImpl(Database.getInstance());
	}
	
	public static BlackListDAO createBlackListDAO(){
		logger.info("Creating new instance of BlackListDAO");
		return new BlackListDAOImpl(Database.getInstance());
	}
}

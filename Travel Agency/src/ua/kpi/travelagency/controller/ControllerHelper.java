package ua.kpi.travelagency.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.commands.CommandBlackList;
import ua.kpi.travelagency.commands.CommandLogin;
import ua.kpi.travelagency.commands.CommandLogout;
import ua.kpi.travelagency.commands.CommandMissing;
import ua.kpi.travelagency.commands.CommandModerOrder;
import ua.kpi.travelagency.commands.CommandOrder;
import ua.kpi.travelagency.commands.CommandRegister;
import ua.kpi.travelagency.commands.CommandSearchTour;
import ua.kpi.travelagency.commands.CommandToReg;
import ua.kpi.travelagency.commands.CommandTour;
import ua.kpi.travelagency.commands.CommandUser;
import ua.kpi.travelagency.commands.ICommand;


public class ControllerHelper {

	private static Logger logger = Logger.getLogger(ControllerHelper.class.getName());
	private static ControllerHelper instance = null;
	private HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

	private ControllerHelper() {
		commands.put("login", new CommandLogin());
		commands.put("register", new CommandRegister());
		commands.put("toreg", new CommandToReg());
		commands.put("searchForm", new CommandSearchTour());
		commands.put("tour", new CommandTour());
		commands.put("orderForm", new CommandOrder());
		commands.put("blackList", new CommandBlackList());
		commands.put("moderOrder", new CommandModerOrder());
		commands.put("userForm", new CommandUser());
		commands.put("logout", new CommandLogout());
	}

	public ICommand getCommand(HttpServletRequest request) {
		logger.info("Try to get_Command.");
		ICommand command = commands.get(request.getParameter("command"));
		if (command == null) {
			logger.warn("Command is missing!");
			command = new CommandMissing();
		}
		return command;
	}

	public static ControllerHelper getInstance() {
		if (instance == null) {
			instance = new ControllerHelper();
		}
		return instance;
	}
}

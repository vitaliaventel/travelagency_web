package ua.kpi.travelagency.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kpi.travelagency.manager.Config;

public class CommandToReg implements ICommand{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		return Config.getInstance().getProperty(Config.REGISTER);
	}

}

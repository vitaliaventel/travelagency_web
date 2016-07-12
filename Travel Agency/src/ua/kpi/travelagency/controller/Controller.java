package ua.kpi.travelagency.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.manager.*;
import ua.kpi.travelagency.commands.ICommand;

/**
 * Servlet implementation class Controller
 */
@WebServlet(name = "Controller", urlPatterns = { "/Controller" })
public class Controller extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(Controller.class.getName());
	private ControllerHelper controllerHelper = ControllerHelper.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = null;
		try {
			ICommand command = controllerHelper.getCommand(request);
			page = command.execute(request, response);
		} catch (ServletException e) {
			logger.error(Message.getInstance().getProperty(Message.SERVLET_EXECPTION),e);
			request.setAttribute("messageError", Message.getInstance().getProperty(Message.SERVLET_EXECPTION));

		} catch (IOException e) {
			logger.error(Message.getInstance().getProperty(Message.IO_EXCEPTION),e);
			request.setAttribute("messageError", Message.getInstance().getProperty(Message.IO_EXCEPTION));

		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		logger.info("Forward to " + page);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}

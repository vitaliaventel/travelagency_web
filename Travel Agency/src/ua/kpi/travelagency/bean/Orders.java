package ua.kpi.travelagency.bean;

import java.io.Serializable;

public class Orders implements Serializable {

	private int idOrders;
	private int tour;
	private String login;

	public Orders() {

	}

	public int getIdOrders() {
		return idOrders;
	}

	public void setIdOrders(int idOrders) {
		this.idOrders = idOrders;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("ua.kpi.jdbc.bean.Orders: ");
		res.append("idOrders=").append(idOrders);
		res.append(", tour=").append(tour);
		res.append(", login=").append(login);
		return res.toString();
	}
}

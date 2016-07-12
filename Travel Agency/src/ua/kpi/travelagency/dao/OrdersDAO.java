package ua.kpi.travelagency.dao;

import java.util.ArrayList;
import ua.kpi.travelagency.bean.Orders;

public interface OrdersDAO {

	public boolean create(Orders orders);

	public Orders read(int id);

	public boolean update(Orders orders);

	public boolean delete(int id);

	public ArrayList<Orders> findAll();

	public ArrayList<Orders> findByLogin(String login);

	public Orders findByIdtour(int id);

}

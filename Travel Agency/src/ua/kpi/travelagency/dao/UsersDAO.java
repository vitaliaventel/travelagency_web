package ua.kpi.travelagency.dao;

import ua.kpi.travelagency.bean.Users;
import java.util.ArrayList;

public interface UsersDAO {

	public boolean create(Users users);

	public Users read(int id);

	public boolean update(Users users);

	public boolean delete(int id);

	public boolean find(String login, String password);

	public ArrayList<Users> findAll();

	public Users findByLogin(String login);

	public ArrayList<Users> findByUsertype(int userType);

}

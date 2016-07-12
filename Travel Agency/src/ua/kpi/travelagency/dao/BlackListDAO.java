package ua.kpi.travelagency.dao;

import java.util.ArrayList;

public interface BlackListDAO {

	public boolean create(String ip);
	
	public boolean delete(int id);
	
	public ArrayList<String> findAllIp();
	
}

package ua.kpi.travelagency.dao;

import java.util.ArrayList;
import ua.kpi.travelagency.bean.Tours;

public interface ToursDAO {
	public boolean create(Tours tours);

	public Tours read(int id);

	public boolean update(Tours tours);

	public boolean delete(int id);

	public ArrayList<Tours> findAll();

	public ArrayList<Tours> findByCountry(String country);

	public ArrayList<Tours> findByLessPrice(double price);

	public ArrayList<Tours> findByGreaterPrice(double price);

	public ArrayList<Tours> findBySale();

	public ArrayList<Tours> findByStatus(String status);

	public ArrayList<Tours> findByTourtype(String tourtype);
}

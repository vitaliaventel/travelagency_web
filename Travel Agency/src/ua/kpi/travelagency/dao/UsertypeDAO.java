package ua.kpi.travelagency.dao;

import java.util.ArrayList;
import ua.kpi.travelagency.bean.Usertype;

public interface UsertypeDAO {

	public boolean create(Usertype type);

	public Usertype read(int id);

	public boolean update(Usertype type);

	public boolean delete(int id);

	public ArrayList<Usertype> findAll();

	public Usertype findById(int id);
}

package ua.kpi.travelagency.dao;

import java.util.ArrayList;
import ua.kpi.travelagency.bean.Tourtype;

public interface TourtypeDAO {

	public boolean create(Tourtype type);

	public Tourtype read(int id);

	public boolean update(Tourtype type);

	public boolean delete(int id);

	public ArrayList<Tourtype> findAll();

	public Tourtype findById(int id);
}

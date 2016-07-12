package ua.kpi.travelagency.bean;

import java.io.Serializable;

public class Tourtype implements Serializable{

	private int idTourType;
	private String name;
	
	public Tourtype(){
		
	}

	public int getIdTourType() {
		return idTourType;
	}

	public void setIdTourType(int idTourType) {
		this.idTourType = idTourType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("ua.kpi.jdbc.bean.Tourtype: ");
		res.append("idTourType=").append(idTourType);
		res.append(", name=").append(name);
		return res.toString();
	}
}

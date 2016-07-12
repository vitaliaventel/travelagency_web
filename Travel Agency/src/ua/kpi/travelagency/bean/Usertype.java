package ua.kpi.travelagency.bean;

import java.io.Serializable;

public class Usertype implements Serializable {

	private int idUserType;
	private String name;
	
	public Usertype(){
		
	}

	public int getIdUserType() {
		return idUserType;
	}

	public void setIdTourType(int idUserType) {
		this.idUserType = idUserType;
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
		res.append("ua.kpi.jdbc.bean.Usertype: ");
		res.append("idUserType=").append(idUserType);
		res.append(", name=").append(name);
		return res.toString();
	}
}

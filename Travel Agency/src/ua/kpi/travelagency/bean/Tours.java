package ua.kpi.travelagency.bean;

import java.io.Serializable;

public class Tours implements Serializable {

	private int idTours;
	private int tourType;
	private String country;
	private double price;
	private int sale;
	private String status;
	private String tourTypeName;
	private String image;

	public Tours() {

	}

	public int getIdTours() {
		return idTours;
	}

	public void setIdTours(int idTours) {
		this.idTours = idTours;
	}

	public int getTourType() {
		return tourType;
	}

	public void setTourType(int tourType) {
		this.tourType = tourType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTourTypeName() {
		return tourTypeName;
	}

	public void setTourTypeName(String tourTypeName) {
		this.tourTypeName = tourTypeName;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage(String image){
		this.image = image;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("ua.kpi.jdbc.bean.Tours: ");
		res.append("idTours=").append(idTours);
		res.append(", tourType=").append(tourType);
		res.append(", country=").append(country);
		res.append(", price=").append(price);
		res.append(", sale=").append(sale);
		res.append(", status=").append(status);
		res.append(", nameType=").append(tourTypeName);
		res.append(", image=").append(image);
		return res.toString();
	}
}

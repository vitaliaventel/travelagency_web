package ua.kpi.travelagency.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Tours;
import ua.kpi.travelagency.connection.Database;
import ua.kpi.travelagency.dao.ToursDAO;

public class ToursDAOImpl implements ToursDAO {

	private static Logger logger = Logger.getLogger(ToursDAOImpl.class.getName());
	private final String sqlCreate = "INSERT INTO travelagency.tours(country,price,sale,status,tourtype,image) VALUES(?,?,?,?,?,?)";
	private final String sqlRead = "SELECT * FROM travelagency.tours WHERE idTours = ";
	private final String sqlUpdate = "UPDATE travelagency.tours SET country=?, price=?, sale=?, status=?, tourtype=?, image=? WHERE idTours=?";
	private final String sqlDelete = "DELETE FROM travelagency.tours WHERE idTours=";
	private Database db;

	public ToursDAOImpl(Database db) {
		this.db = db;
	}

	@Override
	public boolean create(Tours tours) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlCreate)) {
			ps.setString(1, tours.getCountry());
			ps.setDouble(2, tours.getPrice());
			ps.setInt(3, tours.getSale());
			ps.setString(4, tours.getStatus());
			ps.setInt(5, tours.getTourType());
			ps.setString(6, tours.getImage());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems create() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("New tour was created!" + tours);
		return true;

	}

	@Override
	public Tours read(int id) {
		Tours t = new Tours();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(sqlRead + id)) {
			while (rs.next()) {
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setImage(rs.getString("image"));
			}
		} catch (SQLException e) {
			logger.error("DB problems read() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Read tours with id = " + id);
		return t;
	}

	@Override
	public boolean update(Tours tours) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
			ps.setString(1, tours.getCountry());
			ps.setDouble(2, tours.getPrice());
			ps.setInt(3, tours.getSale());
			ps.setString(4, tours.getStatus());
			ps.setInt(5, tours.getTourType());
			ps.setString(6, tours.getImage());
			ps.setInt(7, tours.getIdTours());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems update() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Tour " + tours.getIdTours() + " was updated!");
		return true;
	}

	@Override
	public boolean delete(int id) {
		Connection conn = db.getConn();
		try (Statement stmn = conn.createStatement()) {
			stmn.executeUpdate(sqlDelete + id);
		} catch (SQLException e) {
			logger.error("DB problems delete() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Tour " + id + " was deleted!");
		return true;
	}

	@Override
	public ArrayList<Tours> findAll() {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType")) {
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findAll() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findAll().");
		return toursList;
	}

	@Override
	public ArrayList<Tours> findByCountry(String country) {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType and country = ?")) {
			ps.setString(1, country);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByCountry() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByCountry().");
		return toursList;
	}

	@Override
	public ArrayList<Tours> findByLessPrice(double price) {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType and price < "
						+ price)) {
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByLessPrice() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}

		logger.info("Successful findByLessPrice().");
		return toursList;
	}

	@Override
	public ArrayList<Tours> findByGreaterPrice(double price) {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType AND price > "
						+ price)) {
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByGreaterPrice() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByGreaterPrice().");
		return toursList;
	}

	@Override
	public ArrayList<Tours> findBySale() {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType and sale > 0")) {
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findBySale() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findBySale().");
		return toursList;
	}

	@Override
	public ArrayList<Tours> findByStatus(String status) {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType and status = ?")) {
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByStatus() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByStatus().");
		return toursList;
	}

	@Override
	public ArrayList<Tours> findByTourtype(String tourtype) {
		ArrayList<Tours> toursList = new ArrayList<>();
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(
				"SELECT idTours,country,price,sale,status,tourtype,name,image FROM travelagency.tours,travelagency.tourtype where tours.tourType=tourtype.idTourType AND name = ?")) {
			ps.setString(1, tourtype);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tours t = new Tours();
				t.setIdTours(rs.getInt("idTours"));
				t.setCountry(rs.getString("country"));
				t.setPrice(rs.getDouble("price"));
				t.setSale(rs.getInt("sale"));
				t.setStatus(rs.getString("status"));
				t.setTourType(rs.getInt("tourType"));
				t.setTourTypeName(rs.getString("name"));
				t.setImage(rs.getString("image"));
				toursList.add(t);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByTourtype() ", e);
			return null;
		}finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByTourtype().");
		return toursList;
	}

}

package ua.kpi.travelagency.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Usertype;
import ua.kpi.travelagency.connection.Database;

public class UsertypeDAOImpl implements UsertypeDAO {

	private static Logger logger = Logger.getLogger(UsertypeDAOImpl.class.getName());
	private final String sqlCreate = "INSERT INTO travelagency.usertype(name) VALUES(?)";
	private final String sqlRead = "SELECT * FROM travelagency.usertype WHERE idUserType = ";
	private final String sqlUpdate = "UPDATE travelagency.usertype SET name=? WHERE idUserType=?";
	private final String sqlDelete = "DELETE FROM travelagency.usertype WHERE idUserType=";
	private Database db;

	public UsertypeDAOImpl(Database db) {
		this.db = db;
	}

	@Override
	public boolean create(Usertype type) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlCreate)) {
			ps.setString(1, type.getName());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems create() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("New usertype was created!" + type);
		return true;
	}

	@Override
	public Usertype read(int id) {
		Usertype ut = new Usertype();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(sqlRead + id)) {
			while (rs.next()) {
				ut.setIdTourType(rs.getInt("idUserType"));
				ut.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error("DB problems read() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Read usertype id = " + id);
		return ut;
	}

	@Override
	public boolean update(Usertype type) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
			ps.setString(1, type.getName());
			ps.setInt(2, type.getIdUserType());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problem update() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Usertype " + type.getIdUserType() + " was updated!");
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
		logger.info("Usertype " + id + " was deleted!");
		return true;
	}

	@Override
	public ArrayList<Usertype> findAll() {
		ArrayList<Usertype> typesList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM travelagency.usertype")) {
			while (rs.next()) {
				Usertype tt = new Usertype();
				tt.setIdTourType(rs.getInt("idUserType"));
				tt.setName(rs.getString("name"));
				typesList.add(tt);
			}
		} catch (Exception e) {
			logger.error("Something wrong findAll() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findAll().");
		return typesList;

	}

	@Override
	public Usertype findById(int id) {
		Usertype ut = new Usertype();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement()
				.executeQuery("SELECT * FROM travelagency.usertype WHERE idUserType=" + id)) {
			while (rs.next()) {
				ut.setIdTourType(rs.getInt("idUserType"));
				ut.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			logger.error("Something wrong findById() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findById().");
		return ut;
	}

}

package ua.kpi.travelagency.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Tourtype;
import ua.kpi.travelagency.connection.Database;
import ua.kpi.travelagency.dao.TourtypeDAO;

public class TourtypeDAOImpl implements TourtypeDAO {

	private static Logger logger = Logger.getLogger(TourtypeDAOImpl.class.getName());
	private final String sqlCreate = "INSERT INTO travelagency.tourtype(name) VALUES(?)";
	private final String sqlRead = "SELECT * FROM travelagency.tourtype WHERE idTourType = ";
	private final String sqlUpdate = "UPDATE travelagency.tourtype SET name=? WHERE idTourType=?";
	private final String sqlDelete = "DELETE FROM travelagency.tourtype WHERE idTourType=";
	private Database db;

	public TourtypeDAOImpl(Database db) {
		this.db = db;
	}

	@Override
	public boolean create(Tourtype type) {
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
		logger.info("New tourtype was created!" + type);
		return true;
	}

	@Override
	public Tourtype read(int id) {
		Tourtype tt = new Tourtype();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery(sqlRead + id)) {
			while (rs.next()) {
				tt.setIdTourType(rs.getInt("idTourType"));
				tt.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error("DB problems read() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Read tourtype with id = " + id);
		return tt;
	}

	@Override
	public boolean update(Tourtype type) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
			ps.setString(1, type.getName());
			ps.setInt(2, type.getIdTourType());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems update() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Tourtype " + type.getIdTourType() + " was updated!");
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
		logger.info("Tourtype " + id + " was deleted!");
		return true;
	}

	@Override
	public ArrayList<Tourtype> findAll() {
		ArrayList<Tourtype> typesList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM travelagency.tourtype")) {
			while (rs.next()) {
				Tourtype tt = new Tourtype();
				tt.setIdTourType(rs.getInt("idTourType"));
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
	public Tourtype findById(int id) {
		Tourtype tt = new Tourtype();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement()
				.executeQuery("SELECT * FROM travelagency.tourtype WHERE idTourType=" + id)) {
			while (rs.next()) {
				tt.setIdTourType(rs.getInt("idTourType"));
				tt.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			logger.error("Something wrong findById() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findById().");
		return tt;
	}

}

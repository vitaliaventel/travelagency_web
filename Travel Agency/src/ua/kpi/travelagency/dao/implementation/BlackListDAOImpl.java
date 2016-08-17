package ua.kpi.travelagency.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.connection.Database;
import ua.kpi.travelagency.dao.BlackListDAO;

public class BlackListDAOImpl implements BlackListDAO {

	private static Logger logger = Logger.getLogger(BlackListDAOImpl.class.getName());
	private final String sqlCreate = "INSERT INTO travelagency.blacklist(ip) VALUES(?)";
	private final String sqlDelete = "DELETE FROM travelagency.blacklist WHERE id=";
	private Database db;

	public BlackListDAOImpl(Database db) {
		this.db = db;
	}

	@Override
	public boolean create(String ip) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlCreate)) {
			ps.setString(1, ip);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems create()", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("New value of blacklist was created!" + ip);
		return true;
	}

	@Override
	public boolean delete(int id) {
		Connection conn = db.getConn();
		try (Statement stmn = conn.createStatement()) {
			stmn.executeUpdate(sqlDelete + id);
		} catch (SQLException e) {
			logger.error("DB problems delete()", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("IPADRESS id = " + id + " was deleted!");
		return true;
	}

	@Override
	public ArrayList<String> findAllIp() {
		ArrayList<String> list = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement().executeQuery("SELECT ip FROM travelagency.blacklist")) {
			while (rs.next()) {
				list.add(rs.getString("ip"));
			}
		} catch (Exception e) {
			logger.error("Something wrong findAllIp() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findAllIp().");
		return list;
	}

}

package ua.kpi.travelagency.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Users;
import ua.kpi.travelagency.connection.Database;

public class UsersDAOImpl implements UsersDAO {

	private static Logger logger = Logger.getLogger(UsersDAOImpl.class.getName());

	private final String sqlCreate = "INSERT INTO travelagency.users(login,password,userType) VALUES(?,?,?)";
	private final String sqlRead = "SELECT * FROM travelagency.users WHERE idUsers = ";
	private final String sqlUpdate = "UPDATE travelagency.users SET login=?, password=?, userType=? WHERE idUsers=?";
	private final String sqlDelete = "DELETE FROM travelagency.users WHERE idUsers=";
	private Database db;

	public UsersDAOImpl(Database db) {
		this.db = db;
	}

	@Override
	public boolean create(Users users) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlCreate)) {
			ps.setString(1, users.getLogin());
			ps.setString(2, users.getPassword());
			ps.setInt(3, users.getUserType());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems create()", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("New user was created" + users);
		return true;
	}

	@Override
	public Users read(int id) {
		Connection conn = db.getConn();
		Users u = new Users();
		try (ResultSet rs = conn.createStatement().executeQuery(sqlRead + id)) {
			while (rs.next()) {
				u.setUsersId(rs.getInt("idUsers"));
				u.setLogin(rs.getString("login"));
				u.setPassword(rs.getString("password"));
				u.setUserType(rs.getInt("userType"));
			}
		} catch (SQLException e) {
			logger.error("DB problems read() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Read user with id = " + id);
		return u;
	}

	@Override
	public boolean update(Users users) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
			ps.setString(1, users.getLogin());
			ps.setString(2, users.getPassword());
			ps.setInt(3, users.getUserType());
			ps.setInt(4, users.getUsersId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problem update() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("User " + users.getUsersId() + " was updated!");
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
		logger.info("User " + id + " was deleted!");
		return true;
	}

	@Override
	public ArrayList<Users> findAll() {
		ArrayList<Users> usersList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement()
				.executeQuery("SELECT IDUSERS, LOGIN, USERTYPE, NAME FROM travelagency.users, travelagency.usertype where users.userType=usertype.idUserType")) {
			while (rs.next()) {
				Users u = new Users();
				u.setUsersId(rs.getInt("idUsers"));
				u.setLogin(rs.getString("login"));
				u.setUserType(rs.getInt("userType"));
				u.setTypeName(rs.getString("name"));
				usersList.add(u);
			}
		} catch (Exception e) {
			logger.error("Something wrong findAll() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findAll().");
		return usersList;
	}

	@Override
	public Users findByLogin(String login) {
		Users u = new Users();
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn
				.prepareStatement("SELECT idUsers,login,userType FROM travelagency.users WHERE login = ?")) {
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u.setUsersId(rs.getInt("idUsers"));
				u.setLogin(rs.getString("login"));
				u.setUserType(rs.getInt("userType"));
			}
		} catch (Exception e) {
			logger.error("Something wrong findByLogin() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByLogin().");
		return u;
	}

	@Override
	public ArrayList<Users> findByUsertype(int userType) {
		ArrayList<Users> usersList = new ArrayList<>();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement()
				.executeQuery("SELECT IDUSERS, LOGIN, USERTYPE FROM travelagency.users WHERE userType = " + userType)) {
			while (rs.next()) {
				Users u = new Users();
				u.setUsersId(rs.getInt("idUsers"));
				u.setLogin(rs.getString("login"));
				u.setUserType(rs.getInt("userType"));
				usersList.add(u);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByUsertype() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByUsertype().");
		return usersList;
	}

	@Override
	public boolean find(String login, String password) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM travelagency.users WHERE login = ? AND password = ?")) {
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			logger.info("Successful find().");
			return rs.next();
		} catch (SQLException e) {
			logger.error("DB problem find() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
	}
}

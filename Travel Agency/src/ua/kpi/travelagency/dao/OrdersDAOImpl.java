package ua.kpi.travelagency.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ua.kpi.travelagency.bean.Orders;
import ua.kpi.travelagency.connection.Database;

public class OrdersDAOImpl implements OrdersDAO {

	private static Logger logger = Logger.getLogger(OrdersDAOImpl.class.getName());
	private final String sqlCreate = "INSERT INTO travelagency.orders(tour,login) VALUES(?,?)";
	private final String sqlRead = "SELECT * FROM travelagency.orders WHERE idOrders = ";
	private final String sqlUpdate = "UPDATE travelagency.orders SET tour=?, login=? WHERE idOrders=?";
	private final String sqlDelete = "DELETE FROM travelagency.orders WHERE idOrders=";
	private Database db;

	public OrdersDAOImpl(Database db) {
		this.db = db;
	}

	@Override
	public boolean create(Orders orders) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlCreate)) {
			ps.setInt(1, orders.getTour());
			ps.setString(2, orders.getLogin());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems create()", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("New order was created!" + orders);
		return true;
	}

	@Override
	public Orders read(int id) {
		Connection conn = db.getConn();
		Orders o = new Orders();
		try (ResultSet rs = conn.createStatement().executeQuery(sqlRead + id)) {
			while (rs.next()) {
				o.setIdOrders(rs.getInt("idOrders"));
				o.setLogin(rs.getString("login"));
				o.setTour(rs.getInt("tour"));
			}
		} catch (SQLException e) {
			logger.error("DB problems read() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Read order with id = " + id);
		return o;
	}

	@Override
	public boolean update(Orders orders) {
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
			ps.setInt(1, orders.getTour());
			ps.setString(2, orders.getLogin());
			ps.setInt(3, orders.getIdOrders());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("DB problems update() ", e);
			return false;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Order " + orders.getIdOrders() + " was updated!");
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
		logger.info("Order " + id + " was deleted!");
		return true;
	}

	@Override
	public ArrayList<Orders> findAll() {
		Connection conn = db.getConn();
		ArrayList<Orders> ordersList = new ArrayList<>();
		try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM travelagency.orders")) {
			while (rs.next()) {
				Orders o = new Orders();
				o.setIdOrders(rs.getInt("idOrders"));
				o.setLogin(rs.getString("login"));
				o.setTour(rs.getInt("tour"));
				ordersList.add(o);
			}
		} catch (Exception e) {
			logger.error("Something wrong findAll() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findAll().");
		return ordersList;
	}

	@Override
	public ArrayList<Orders> findByLogin(String login) {
		ArrayList<Orders> ordersList = new ArrayList<>();
		Connection conn = db.getConn();
		try (PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM travelagency.orders WHERE login = ?")) {
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Orders o = new Orders();
				o.setIdOrders(rs.getInt("idOrders"));
				o.setLogin(rs.getString("login"));
				o.setTour(rs.getInt("tour"));
				ordersList.add(o);
			}
		} catch (Exception e) {
			logger.error("Something wrong findByLogin() ", e);
			return null;
		} finally {
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByLogin().");
		return ordersList;
	}

	@Override
	public Orders findByIdtour(int id) {
		Orders o = new Orders();
		Connection conn = db.getConn();
		try (ResultSet rs = conn.createStatement()
				.executeQuery("SELECT * FROM travelagency.orders WHERE tour = " + id)) {
			while (rs.next()) {
				o.setIdOrders(rs.getInt("idOrders"));
				o.setLogin(rs.getString("login"));
				o.setTour(rs.getInt("tour"));
			}
		} catch (Exception e) {
			logger.error("Something wrong findByIdtour() ", e);
			return null;
		}finally{
			db.returnConnectionToPool(conn);
		}
		logger.info("Successful findByIdtour().");
		return o;
	}

}

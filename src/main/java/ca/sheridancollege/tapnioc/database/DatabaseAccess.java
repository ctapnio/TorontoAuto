package ca.sheridancollege.tapnioc.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.tapnioc.beans.Car;
import ca.sheridancollege.tapnioc.beans.Manufacturer;
import ca.sheridancollege.tapnioc.beans.User;
@Repository
public class DatabaseAccess {
	List<Car> carList = new CopyOnWriteArrayList<Car>();
	@Autowired
	@Lazy
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findUserAccount(String email) {//fetching account according to email
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where email=:email";
		parameters.addValue("email", email);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}

	public List<String> getRolesById(Long userId) {//fetching roles according to userId
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName " 
				+ "FROM user_role, sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId " 
				+ "AND userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters); for (Map<String, Object> row :rows) {
		roles.add((String)row.get("roleName"));
		}
		return roles; 
	}
	
	public void insertCar() {
		String query = "INSERT INTO car(manufacturerID, model, year, color, price) VALUES ('00001', 'Toyota', '2007', 'red', '1.99')";
		int rowsAffected = jdbc.update(query, new HashMap<String, Object>());
		if (rowsAffected > 0)
			System.out.println("Inserted car into database");
	}

	public void insertCar(int manufacturerID, String model, Long year, String color, Double price) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();// SqlParameterSource
		String query = "INSERT INTO car(manufacturerID, model, year, color, price)VALUES(:manufacturerID, :model, :year, :color, :price)";
		namedParameters.addValue("manufacturerID", manufacturerID);
		namedParameters.addValue("model", model);
		namedParameters.addValue("year", year);
		namedParameters.addValue("color", color);
		namedParameters.addValue("price", price);

		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {
			System.out.println("Inserted column into database");
		}

	}

	public List<Car> getCars() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM car";
		// this returns result sets
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Car>(Car.class));
		// this runs SELECT statements
	}
	
	public List<Manufacturer> getManufacturers() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM manufacturer";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Manufacturer>(Manufacturer.class));
	}

	public void deleteCar(int carID) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM car WHERE carID = :carID";
		namedParameters.addValue("carID", carID);
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {
			System.out.println("Deleted car from database");
		}
	}

	public List<Car> getCarByCarID(int carID) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM car WHERE carID = :carID";
		namedParameters.addValue("carID", carID);
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Car>(Car.class));
	}
	
	public void updateCar(int carID, String model, Double price) {
		System.out.println(model+price);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "UPDATE Car set model = :model, price = :price where carID = :carID";
		
		namedParameters.addValue("carID", carID);
		namedParameters.addValue("model", model);
		namedParameters.addValue("price", price);
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {
			System.out.println("Updated database");
		}
	}
	
	public void addUser(String email, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO sec_user " + "(email, encryptedPassword,enabled)"
				+ " values (:email, :encryptedPassword,1)";
		parameters.addValue("email", email);
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password)); // encrypting password
		jdbc.update(query, parameters);
	}
	
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = " INSERT INTO user_role (userId, roleId) " + "VALUES (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}
	
	
}

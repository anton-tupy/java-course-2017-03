package com.jcourse.kladov;

import java.sql.*;
import org.apache.log4j.Logger;

public class MainDB {
	public static final Logger log = Logger.getLogger(MainDB.class);

	public static void main(String[] args) throws SQLException {
		final String cmd = args[0];

		switch (cmd){
			case "select-by-id":
				selectById(Integer.valueOf(args[1]));
				break;
			case "select-by-post":
				selectByPost(args[1]);
				break;

			case "select-all":
				selectAll();
				break;
			case "create":
				create(args[1]);
				break;
		}
	}

	private static void create(String message) throws SQLException {
		try (Connection connection = createConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into POSTS (POST, POST_DATE) values (?, ?)");
			preparedStatement.setString(1, message);
			preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
			preparedStatement.executeUpdate();

			Integer id = null;
			final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				log.info(String.format("Item id = " + generatedKeys.getInt(1)));
			}

			log.info("created");
		}
	}

	private static void selectAll() throws SQLException {
		try (Connection connection = createConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from POSTS");
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				log.info(String.format("id: %d, post: %s, date: %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3)));
			}

			log.info("done");
		}
	}

	private static void selectById(int id) throws SQLException {
		try (Connection connection = createConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from POSTS WHERE id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				log.info(String.format("id: %d, post: %s, date: %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3)));
			}

			log.info("done");
		}
	}


	private static void selectByPost(String post) throws SQLException {
		try (Connection connection = createConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from POSTS WHERE POST like '%'||?||'%'");
			preparedStatement.setString(1, post);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				log.info(String.format("id: %d, post: %s, date: %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3)));
			}

			log.info("done");
		}
	}

	private static Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:h2:file:~/testdb");
	}
}

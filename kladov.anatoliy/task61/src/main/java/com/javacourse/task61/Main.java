package com.javacourse.task61;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

public class Main {
	public static void main(String[] args) throws IOException, SQLException {
		/*if (args.length == 0)
			System.out.println("Please provide command");
		String command = args[0];
		HibernateWorker worker = null;
		List<GuestBookEntity> list;
		try {
			worker = new HibernateWorker();
			if (command.compareToIgnoreCase("add") == 0)
				worker.addRecord(args[1]);
			if (command.compareToIgnoreCase("list") == 0) {
				list = worker.getRecords();
				for (GuestBookEntity record: list) {
					System.out.println(record.getId() + " " + record.getDate() + " " + record.getMsg());
				}
			}
		} finally {
			worker.close();
		}*/

		final String cmd = args[0];
		switch(cmd) {
			case "select-all":
				selectAll();
				break;
			case "create":
				create(args[1]);
				break;
			case "searchId":
				searchId(args[1]);
				break;
			case "searchStr":
				searchStr(args[1]);
				break;
			default:
				assert(true);
				log.error("Undefined command");
		}
	}

	private static void selectAll() throws SQLException {
		try (Connection connection = createConnection()) {
			final PreparedStatement statement = connection.prepareStatement("SELECT ID, DATE, MSG FROM PUBLIC.GUEST_BOOK_TABLE");
			final ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				log.info(String.format("id=%s, date=%s, msg=%s", resultSet.getInt(1), resultSet.getDate(2), resultSet.getString(3)));
			}
		}
	}

	private static void create(String msg) throws SQLException {
		try(Connection connection = createConnection()) {
			final PreparedStatement statement = connection.prepareStatement("INSERT INTO PUBLIC.GUEST_BOOK_TABLE (DATE, MSG) VALUES(?, ?)");
			statement.setDate(1, new Date(new java.util.Date().getTime()));
			statement.setString(2, msg);
			statement.executeUpdate();
			log.info("Created");
		}
	}
	private static void searchId(String id) throws SQLException {
		try (Connection connection = createConnection()) {
			final String request = String.format("SELECT ID, DATE, MSG FROM PUBLIC.GUEST_BOOK_TABLE WHERE ID=%s", id);
			final PreparedStatement statement = connection.prepareStatement(request);
			final ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				log.info(String.format("id=%s, date=%s, msg=%s", resultSet.getInt(1), resultSet.getDate(2), resultSet.getString(3)));
			}
		}
	}

	private static void searchStr(String str) throws SQLException {
		try (Connection connection = createConnection()) {
			final String request = String.format("SELECT ID, DATE, MSG FROM PUBLIC.GUEST_BOOK_TABLE WHERE MSG LIKE '%s%s%s'", "%", str, "%");
			final PreparedStatement statement = connection.prepareStatement(request);
			final ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				log.info(String.format("id=%s, date=%s, msg=%s", resultSet.getInt(1), resultSet.getDate(2), resultSet.getString(3)));
			}
		}
	}

	private static Connection createConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:h2:file:C:\\src\\db_guest_book", "sa","");
	}

	private static final Logger log = Logger.getLogger(Main.class);
}
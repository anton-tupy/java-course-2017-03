package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

@Log4j
public class JDBCSerializer implements MetricSerializer {
	Connection connection;

	public static Connection getConnection(String connectString) throws SQLException {
		return DriverManager.getConnection(connectString);
	}

	JDBCSerializer(String connectionString) throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver"); // ensure that this driver is supported
		connection = getConnection(connectionString);
	}

	@Override
	public void serialize(Metric m) {
		log.info("Writing stats for " + m.getName() + " into db table");

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute("DROP TABLE IF EXISTS " + m.getName() + ";");

			boolean firstRow = true;
			for (Iterator<Metric.Row> rowIter = m.getIterator() ; rowIter.hasNext(); ) {
				Metric.Row row = rowIter.next();

				String create = "CREATE TABLE " + m.getName() + "(";
				String insert = "INSERT INTO " + m.getName() + " (";

				boolean firstCol = true;
				for (Metric.Column c : row.cols) {
					create = create + (firstCol ? "" : ",") + getColCreationStatement(c);
					insert = insert + (firstCol ? "" : ",") + c.title;
					firstCol = false;
				}

				create += ");";
				insert += ") values (";

				firstCol = true;
				for (Metric.Column c : row.cols) {
					insert = insert + (firstCol ? "" : ",") + "'" + c.value.toString() + "'";
					firstCol = false;
				}
				insert += ");";

				// create table
				if (firstRow)
					statement.execute(create);

				statement.execute(insert);
				firstRow = false;
			}
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private String getColCreationStatement(Metric.Column c) throws IOException {
		if (c.type == Integer.class) {
			return String.format("%s integer", c.title);
		} else if (c.type == String.class) {
			return String.format("%s varchar(100)", c.title);
		} else if (c.type == Double.class) {
			return String.format("%s float", c.title);
		}
		throw new IOException("Data type is not supported");
	}
}

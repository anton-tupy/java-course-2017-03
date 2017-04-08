package com.jcourse.kladov;

import java.io.IOException;
import java.util.Iterator;

public interface Metric {
	void countOn(Document doc) throws IOException;
	String getName();

	Iterator<Row> getIterator();

	class Column {
		Class type;
		String title;
		Object value;

		Column(Class type, String title, Object value) {
			this.type = type;
			this.title = title;
			this.value = value;
		}
	}

	class Row {
		Column[] cols;
		Object entity;

		Row(Column[] cols, Object entity) {
			this.cols = cols;
			this.entity = entity;
		}
	}
}

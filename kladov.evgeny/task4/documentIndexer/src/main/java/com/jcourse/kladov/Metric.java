package com.jcourse.kladov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public interface Metric {
	void countOn(Document doc) throws IOException;
	void serialize(MetricSerializer s) throws IOException;
	String getName();

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

		Row(Column[] cols) {
			this.cols = cols;
		}
	}

	Iterator<Row> getIterator();
}

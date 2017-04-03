package com.jcourse.kladov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public interface Metric {
	void countOn(Document doc) throws IOException;
	void serialize(MetricSerializer s) throws IOException;
	String getName();

	interface Column {
		Class type();
		String title();
		Object value();
	}

	interface Row {
		Column[] getCols();
	}

	Iterator<Row> getIterator();
}

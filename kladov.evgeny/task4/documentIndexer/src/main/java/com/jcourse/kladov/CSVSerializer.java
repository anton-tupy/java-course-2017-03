package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

@Log4j
public class CSVSerializer implements MetricSerializer {
	@Override
	public void serialize(Metric m) {
		try {
			PrintStream printStream = new PrintStream(m.getName() + ".csv", "UTF-8");

			boolean firstRow = true;
			for (Iterator<Metric.Row> iterator = m.getIterator(); iterator.hasNext(); ) {
				Metric.Row row = iterator.next();

				if (firstRow) {
					Arrays.asList(row.getCols()).forEach((c)->printStream.printf("%12s\t", c.title()));
					firstRow = false;
				}
				printStream.println();
				Arrays.asList(row.getCols()).forEach((c)->printStream.printf("%12s\t", c.value().toString()));
			}
			printStream.close();
		} catch (FileNotFoundException e) {
			log.warn("Cannot open file for serialization metric " + m.getName() + ".csv", e);
		} catch (IOException e) {
			log.warn("Problem while serialization metric " + m.getName() + ".csv", e);
		}
	}
}

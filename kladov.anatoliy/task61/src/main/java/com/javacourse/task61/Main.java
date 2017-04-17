package com.javacourse.task61;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
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
		}
	}
}
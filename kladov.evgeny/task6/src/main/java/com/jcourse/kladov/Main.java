package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

@Log4j
public class Main {

	public static void main(String[] args) {
		GuestBookController controller = new GuestBookController();

		if (args[0].equals("add")) {
			controller.addRecord(args[1]);
		} else if (args[0].equals("list")) {
			controller.getRecords().forEach(r -> System.out.println(r.getPost_message() + " at " + r.getPost_date().toString()));
		} else
			System.out.println("Unknown command");

		controller.close();
	}
}

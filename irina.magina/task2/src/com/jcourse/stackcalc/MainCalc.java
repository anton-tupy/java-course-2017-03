package com.jcourse.stackcalc;

import java.io.IOException;

public class MainCalc {
	public static void main(String[] args) throws IOException {
		String path = "";

		try {
			path = args[0];
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Missed parameter path");
			System.exit(0);
		}
		StackCalc stackCalc = new StackCalc(path);
		stackCalc.execute();
	}
}

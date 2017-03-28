package com.jcourse.stackcalc;

import java.io.IOException;

public class MainCalc {
	public static void main(String[] args) throws IOException {
		String path = args[0];
		StackCalc stackCalc = new StackCalc(path);
		stackCalc.execute();
	}
}

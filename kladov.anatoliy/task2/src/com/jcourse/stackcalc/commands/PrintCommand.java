package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public class PrintCommand implements Command {
	@Override
	public String getName() {
		return "PRINT";
	}

	@Override
	public void execute(String arg, Stack stack, Map<String, Double> environment) {
		System.out.println(stack.peek());
	}
}

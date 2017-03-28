package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 27.03.2017.
 */
public class DivisionCommand implements Command {
	@Override
	public String getName() {
		return "/";
	}

	@Override
	public void execute(String arg, Stack stack, Map<String, Double> environment) {
		if (stack.getSize() < 2)
			throw new RuntimeException("Stack is too small for division");

		Double denominator = stack.pop();
		Double numerator = stack.pop();
		if (denominator == 0)
			throw new RuntimeException("Division by zero error");

		Double res = numerator / denominator;
		stack.push(res);
	}
}

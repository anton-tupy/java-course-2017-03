package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 27.03.2017.
 */
public class AdditionCommand implements Command {
	@Override
	public String getName() {
		return "+";
	}

	@Override
	public void execute(String arg, Stack stack, Map<String, Double> environment) {
		if (stack.getSize() < 2)
			throw new RuntimeException("Stack is too small for addition");

		Double res = stack.pop() + stack.pop();
		stack.push(res);
	}
}

package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 27.03.2017.
 */
public class PopCommand implements Command {

	@Override
	public String getName() {
		return "POP";
	}

	@Override
	public void execute(String arg, Stack stack, Map<String, Double> environment) {
		if (stack.isEmpty())
			throw new RuntimeException("Stack is empty");
		stack.pop();
	}
}

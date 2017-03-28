package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public class PushCommand implements Command {

	@Override
	public String getName() {
		return "PUSH";
	}

	@Override
	public void execute(String arg, Stack stack, Map<String, Double> environment) {
		Double value = environment.get(arg);
		if (value == null) {
			Double argValue = Double.valueOf(arg);
			stack.push(argValue);
		}
		else {
			stack.push(value);
		}
	}
}

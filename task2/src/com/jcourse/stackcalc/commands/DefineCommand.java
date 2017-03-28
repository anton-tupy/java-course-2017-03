package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 27.03.2017.
 */
public class DefineCommand implements Command {

	@Override
	public String getName() {
		return "DEFINE";
	}

	@Override
	public void execute(String arg, Stack stack, Map<String, Double> environment) {
		String var, value;
		int spaceIndex = arg.indexOf(' ');

		if (spaceIndex > 0) {
			var = arg.substring(0, spaceIndex);
			value = arg.substring(spaceIndex + 1);
		} else {
			throw new RuntimeException("Define command syntax error");
		}

		Double varValue = Double.valueOf(value);
		environment.put(var, varValue);
	}
}
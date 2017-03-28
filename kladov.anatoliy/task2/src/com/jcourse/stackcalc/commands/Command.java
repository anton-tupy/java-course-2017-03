package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public interface Command {
	String getName();
	void execute(String arg, Stack stack, Map<String, Double> environment);
}

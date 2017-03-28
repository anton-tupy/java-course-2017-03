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
	public void execute(String arg, Stack stack, Map<String, Double> environment)
	{
	//	environment.get((String)"aaa");
		System.out.println(stack.peek());
	}
}

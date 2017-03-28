package com.jcourse.stackcalc;

import com.jcourse.stackcalc.commands.Command;
import com.jcourse.stackcalc.commands.DefineCommand;

import java.util.HashMap;
import java.util.Map;
import java.lang.Double;

/**
 * Created by Anatoliy on 27.03.2017.
 */

public class UnitTest {
	// x*x + 3*x - 4 = 0
	// x1 = 1; x2 = -4;

	public static void main(String[] args) {
		StackCalc stackCalc = new StackCalc("");
		Map<String, Double> environment = new HashMap<>();
		Stack stack = new Stack();
		Map<String, Command> cmds = stackCalc.getCommands();
		String a = "1";
		String b = "-3";
		String c = "-4";

		cmds.get("PUSH").execute(b, stack, environment);

		cmds.get("PUSH").execute(b, stack, environment);
		cmds.get("PUSH").execute(b, stack, environment);
		cmds.get("*").execute("", stack, environment);

		cmds.get("PUSH").execute(a, stack, environment);
		cmds.get("PUSH").execute(c, stack, environment);
		cmds.get("PUSH").execute("4", stack, environment);
		cmds.get("*").execute("", stack, environment);
		cmds.get("*").execute("", stack, environment);

		cmds.get("-").execute("", stack, environment);

		cmds.get("SQRT").execute("", stack, environment);

		cmds.get("+").execute("", stack, environment);

		cmds.get("PUSH").execute("2", stack, environment);
		cmds.get("PUSH").execute(a, stack, environment);
		cmds.get("*").execute("", stack, environment);

		cmds.get("/").execute("", stack, environment);

		Double res1 = stack.pop();
		if (!res1.equals(1.0)) {
			System.out.println(res1);
			System.out.println("Unit test FAIL");
			return;
		}


		cmds.get("PUSH").execute(b, stack, environment);

		cmds.get("PUSH").execute(b, stack, environment);
		cmds.get("PUSH").execute(b, stack, environment);
		cmds.get("*").execute("", stack, environment);

		cmds.get("PUSH").execute(a, stack, environment);
		cmds.get("PUSH").execute(c, stack, environment);
		cmds.get("PUSH").execute("4", stack, environment);
		cmds.get("*").execute("", stack, environment);
		cmds.get("*").execute("", stack, environment);

		cmds.get("-").execute("", stack, environment);

		cmds.get("SQRT").execute("", stack, environment);

		cmds.get("-").execute("", stack, environment);

		cmds.get("PUSH").execute("2", stack, environment);
		cmds.get("PUSH").execute(a, stack, environment);
		cmds.get("*").execute("", stack, environment);

		cmds.get("/").execute("", stack, environment);

		Double res2 = stack.pop();
		if (!res2.equals(-4.0)) {
			System.out.println(res2);
			System.out.println("Unit test FAIL");
			return;
		}

		System.out.println("Unit test COMPLETED");
	}
}

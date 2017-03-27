package com.jcourse.kladov;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StackCalcTest {
	@org.junit.jupiter.api.Test
	void evaluate() {
		assertEquals(4.0, evaluateDouble("PUSH 4 PRINT"));
		assertEquals(2.0, evaluateDouble("DEFINE a 4 PUSH a SQRT PRINT"));
		final int a = 1, b = 2, c = 1;
		assertEquals(x1(a, b, c), evaluateDouble(x1Prog(a, b, c)));
		assertEquals(x2(a, b, c), evaluateDouble(x2Prog(a, b, c)));
	}

	String x1Prog(int a, int b, int c) {
		return String.format("PUSH %d PUSH %d PUSH %d * PUSH 4 PUSH %d * PUSH %d * - SQRT - PUSH 2 PUSH %d * / PRINT", b, b, b, a, c, a);
	}

	String x2Prog(int a, int b, int c) {
		return String.format("PUSH %d PUSH %d PUSH %d * PUSH 4 PUSH %d * PUSH %d * - SQRT + - PUSH 2 PUSH %d * / PRINT", b, b, b, a, c, a);
	}

	double x1(double a, double b, double c) {
		return (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
	}

	double x2(double a, double b, double c) {
		return (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
	}

	double evaluateDouble(String arg) {
		return Double.valueOf(evaluateStr(arg));
	}

	String evaluateStr(String arg) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BufferedReader reader = new BufferedReader(new StringReader(arg));
		new StackCalc(reader, new PrintStream(stream)).evaluate();
		return stream.toString();
	}
}
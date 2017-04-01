package com.jcourse.kladov;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StackCalcTest {
	@org.junit.jupiter.api.Test
	void evaluate() {
		System.out.println(evaluateStr("PUSH 10 LABEL for PRINT DEC JNZ for"));
		System.out.println(evaluateStr("GOTO L2 PUSH 10 PRINT LABEL L2 PUSH 20 PRINT"));
		assertEquals(0.0, evaluateDouble("PUSH 1 LOG PRINT"));
		assertEquals(2.0, evaluateDouble("PUSH 2 EXP LOG PRINT"));
		assertEquals(4.0, evaluateDouble("PUSH 4 PRINT"));
		assertEquals(2.0, evaluateDouble("DEFINE a 4 PUSH a SQRT PRINT"));
		final int a = 1, b = 2, c = 1;
		assertEquals(x1(a, b, c), evaluateDouble(x1Prog(a, b, c)));
		assertEquals(x2(a, b, c), evaluateDouble(x2Prog(a, b, c)));
	}

	String x1Prog(int a, int b, int c) {
		return String.format("PUSH %d PUSH %d PUSH %d MUL PUSH 4 PUSH %d MUL PUSH %d MUL SUB SQRT SUB PUSH 2 PUSH %d MUL DIV PRINT", b, b, b, a, c, a);
	}

	String x2Prog(int a, int b, int c) {
		return String.format("PUSH %d PUSH %d PUSH %d MUL PUSH 4 PUSH %d MUL PUSH %d MUL SUB SQRT ADD SUB PUSH 2 PUSH %d MUL DIV PRINT", b, b, b, a, c, a);
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
		new StackCalc(reader, new PrintStream(stream), null).evaluate();
		return stream.toString();
	}
}
package com.jcourse.stackcalc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public class Stack {
	private List<Double> values = new ArrayList<>();

	public void push(double value) {
		values.add(value);
	}

	public double pop() {
		if (values.isEmpty()) {
			throw new RuntimeException("Stack is empty");
		}
		/*
		Double result = values.get(values.size() - 1);
		values.remove(values.size() - 1);
		return result;
		*/
		return values.remove(values.size() - 1);
	}

	public double peek() {
		if (values.isEmpty()) {
			throw new RuntimeException("Stack is empty");
		}
		return values.get(values.size() - 1);
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}
}

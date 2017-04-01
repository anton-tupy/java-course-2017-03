package com.jcourse.kladov;

import java.util.HashMap;
import java.util.Map;

class Context {
	private int instructionPoiter = 0;
	private Map<String, Integer> labels = new HashMap<>();
	private Map<String, Double> vars = new HashMap<>();

	public void setVar(String name, Double val) {
		vars.put(name, val);
	}

	public Double getVar(String name) {
		return vars.get(name);
	}

	public void setLabel(String name) {
		labels.put(name, instructionPoiter);
	}

	public void gotoLabel(String name) {
		instructionPoiter = labels.get(name);
	}

	public int getInstructionPointer() {
		return instructionPoiter;
	}

	public void moveToNextInstruction() {
		instructionPoiter += 1;
	}

	public String toString() {
		final String[] result = {new String()};
		vars.forEach((k, v) -> result[0] += String.format("%s=%f,", k, v.doubleValue()));
		return result[0];
	}
}

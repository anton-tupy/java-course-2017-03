package com.jcourse.kladov;

import java.util.HashMap;
import java.util.Map;

class Context {
	private Map<String, Double> vars = new HashMap<>();

	public void setVar(String name, Double val) {
		vars.put(name, val);
	}

	public Double getVar(String name) {
		return vars.get(name);
	}
}

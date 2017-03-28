package com.jcourse.cars;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public abstract class Car extends Vehicle {

	private int peopleCount = 3;
	protected int width = 5;
	String name = "Бугатти";

	public Car() {
	}

	public Car(int width) {
		this.width = width;
	}
}

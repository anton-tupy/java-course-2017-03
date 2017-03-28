package com.jcourse.cars;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public class RedCar extends Car {

	public RedCar() {
	}

	public RedCar(int width) {
		super(width);
	}

	private int peopleCount = 3;

	@Override
	public void drive() {
		System.out.printf("Car with width %s drive %s people\n", width, peopleCount);
	}

	public class Engine {
		public void describe() {
			System.out.printf("Engine of car with people %s\n", peopleCount);
		}
	}

	public static class Wheel {
		public void describe() {
			System.out.printf("Whee of car\n");
		}
	}

}

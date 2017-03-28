package com.jcourse.cars;

/**
 * Created by Anatoliy on 25.03.2017.
 */
public interface Driveable {
	void drive();

	default void driveTwice() {
		drive();
		drive();
	}
}

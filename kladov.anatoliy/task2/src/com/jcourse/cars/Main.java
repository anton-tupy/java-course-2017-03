package com.jcourse.cars;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        Driveable driveable = new RedCar(10);
        driveable.driveTwice();

        RedCar redCar = new RedCar();
        out.println("Name = " + redCar.name);

        Object obj = new Object();

        RedCar.Engine engine = redCar.new Engine();
        engine.describe();

        RedCar.Wheel wheel = new RedCar.Wheel();
        wheel.describe();

        Driveable anonymDriveable = new Driveable() {
            @Override
            public void drive() {
                out.println("Drive anonymousely");
            }
        };
        anonymDriveable.drive();

        Driveable anon2 = () -> {
            out.println("Drive lambda!");
        };
        anon2.drive();

        out.println("driveable instanceof Car = " + (driveable instanceof Car));

        Car car2 = (Car) driveable;
        car2.drive();

        BlueCar blueCar = new BlueCar();
        //BlueCar blueCar2 = (BlueCar) (Car) redCar;
    }
}

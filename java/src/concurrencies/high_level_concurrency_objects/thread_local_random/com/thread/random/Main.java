package com.thread.random;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Main {
	static String[] makeList = { "Honda", "Toyota", "Nissan" };
	static String[] hondaModels = { "Accord", "Civic", "Fit" };
	static String[] toyotaModels = { "Camry", "Corolla", "Helix" };
	static String[] nissanModels = { "Altima", "Maxima", "Sentra" };

	static String[] transmissions = { "CVT", "Auto", "Manual" };
	static String[] engines = { "2.5 L4", "1.6 Turbo", "3.7 V6", "2.0 Twin Turbo" };
	static String[] driveType = { "AWD", "FWD", "RWD" };
	static String[] trims = { "Sport", "Premium", "Grand Touring" };
	static String[] colors = { "Metallic Black", "Midnight Purple", "Ceramic White", "Silver Grey", "Lunar Blue",
			"Bourbon Red", "Gunmetal Grey", "Java Brown" };
	static String[] bodyTypes = { "Sedan", "SUV", "Coupe" };

	public static void main(String[] args) {
		System.out.println("Hello World!");
		ArrayList<Car> carList = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			Car nCar = new Car(vinGen());
			nCar.setYear("201" + Integer.toString(ThreadLocalRandom.current().nextInt(0, 9)));
			int makeNum = ThreadLocalRandom.current().nextInt(0, 2);
			nCar.setMake(makeList[makeNum]);
			if (makeNum == 0) {
				System.out.println("0");
				nCar.setModel(hondaModels[ThreadLocalRandom.current().nextInt(0, 2)]);

			} else if (makeNum == 1) {
				System.out.println("1");
				nCar.setModel(toyotaModels[ThreadLocalRandom.current().nextInt(0, 2)]);

			} else if (makeNum == 2) {
				System.out.println("2");
				nCar.setModel(nissanModels[ThreadLocalRandom.current().nextInt(0, 2)]);
			}
			nCar.setTransmission(transmissions[ThreadLocalRandom.current().nextInt(0, 2)]);
			nCar.setBodyType(bodyTypes[ThreadLocalRandom.current().nextInt(0, 2)]);
			nCar.setMileage(Integer.toString(ThreadLocalRandom.current().nextInt(5678, 99999)));
			nCar.setColor(colors[ThreadLocalRandom.current().nextInt(0, colors.length)]);
			nCar.setTrim(trims[ThreadLocalRandom.current().nextInt(0, 2)]);
			nCar.setEngine(engines[ThreadLocalRandom.current().nextInt(0, 2)]);
			nCar.setPrice(genPrice(nCar.getYear(), nCar.getMileage()));
			nCar.setDriveType(driveType[ThreadLocalRandom.current().nextInt(0, 2)]);

			carList.add(nCar);
		}

		for (Car c : carList) {
			System.out.println(c.toString());
		}

	}

	static String genPrice(String year, String mileage) {
		int diff = (Integer.parseInt(year) - 1998) / 4;
		int mile = (100000 - Integer.parseInt(mileage)) / 10;
		int price = mile * diff;
		if (price < 5000)
			price += ThreadLocalRandom.current().nextInt(19, 280) * 3;
		return "$" + Integer.toString(price);
	}

	static String vinGen() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
}

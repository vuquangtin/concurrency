package synchronizers.phaser.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TestRacePhaser {
	public static final int CARS_COUNT = 4;

	public static void main(String[] args) {
		System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
		RacePhaser race = new RacePhaser(new RoadPhaser(60),
				new TunnelPhaser(), new RoadPhaser(40));
		CarPhaser[] cars = new CarPhaser[CARS_COUNT];

		for (int i = 0; i < cars.length; i++) {
			cars[i] = new CarPhaser(race, 20 + (int) (Math.random() * 10));
		}
		for (int i = 0; i < cars.length; i++) {
			new Thread(cars[i]).start();
		}
		race.phaser.awaitAdvance(0);
		//
		race.phaser.awaitAdvance(1);
		System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

		// System.out.println(
		// " main: ArrivedParties:" + race.phaser.getArrivedParties() +
		// " UnarrivedParties:" + race.phaser.getUnarrivedParties() +
		// " RegisteredParties:" + race.phaser.getRegisteredParties() +
		// " Phase:" + race.phaser.getPhase());
		race.phaser.awaitAdvance(2);
		System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

	}
}

class CarPhaser implements Runnable {
	private static int CARS_COUNT;

	static {
		CARS_COUNT = 0;
	}

	private RacePhaser race;
	private int speed;
	private String name;

	public String getName() {
		return name;
	}

	public int getSpeed() {
		return speed;
	}

	public CarPhaser(RacePhaser race, int speed) {
		this.race = race;
		this.speed = speed;
		CARS_COUNT++;
		this.name = "Участник #" + CARS_COUNT;
		race.phaser.register();
	}

	@Override
	public void run() {
		System.out.println(this.name + " готовится");
		try {
			Thread.sleep(500 + (int) (Math.random() * 800));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.name + " готов");
		race.phaser.arriveAndAwaitAdvance(); // 0
		// System.out.println(
		// " run: ArrivedParties:" + race.phaser.getArrivedParties() +
		// " UnarrivedParties:" + race.phaser.getUnarrivedParties() +
		// " RegisteredParties:" + race.phaser.getRegisteredParties() +
		// " Phase:" + race.phaser.getPhase());
		race.phaser.arriveAndAwaitAdvance(); // 1
		for (int i = 0; i < race.getStages().size(); i++) {
			race.getStages().get(i).go(this);
		}

		// System.out.println(
		// " run: ArrivedParties:" + race.phaser.getArrivedParties() +
		// " UnarrivedParties:" + race.phaser.getUnarrivedParties() +
		// " RegisteredParties:" + race.phaser.getRegisteredParties() +
		// " Phase:" + race.phaser.getPhase());

		if (race.phaser.getUnarrivedParties() == 4) {
			System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> " + this.name + " WIN!!");

		} else {
			race.phaser.arriveAndAwaitAdvance(); // 2
		}
		race.phaser.arriveAndDeregister(); // 3

	}
}

abstract class StagePhaser {
	protected int length;
	protected String description;

	public String getDescription() {
		return description;
	}

	public abstract void go(CarPhaser c);
}

class RoadPhaser extends StagePhaser {
	public RoadPhaser(int length) {
		this.length = length;
		this.description = "Дорога " + length + " метров";
	}

	@Override
	public void go(CarPhaser c) {
		try {
			System.out.println(c.getName() + " начал этап: " + description);
			Thread.sleep(length / c.getSpeed() * 1000);
			System.out.println(c.getName() + " закончил этап: " + description);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TunnelPhaser extends StagePhaser {

	Semaphore semaphoreTunnel;

	public TunnelPhaser() {
		this.length = 80;
		this.description = "Тоннель " + length + " метров";
		semaphoreTunnel = new Semaphore(TestRacePhaser.CARS_COUNT / 2);
	}

	@Override
	public void go(CarPhaser c) {
		try {
			try {
				System.out.println(c.getName() + " готовится к этапу(ждет): "
						+ description);
				semaphoreTunnel.acquire();
				System.out.println(c.getName() + " начал этап: " + description);
				Thread.sleep(length / c.getSpeed() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(c.getName() + " закончил этап: "
						+ description);
				semaphoreTunnel.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class RacePhaser {
	private ArrayList<StagePhaser> stages;

	Phaser phaser;

	public ArrayList<StagePhaser> getStages() {
		return stages;
	}

	public RacePhaser(StagePhaser... stages) {
		this.stages = new ArrayList<>(Arrays.asList(stages));
		this.phaser = new Phaser();
	}
}
package com.thread.random;

import java.awt.*;
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
public class Snake {
	private static void resp() {
		System.out.println("Play again?");
		String s = new java.util.Scanner(System.in).next();
		if (s.equals("yes")) {
			snake();
		}
	}

	private static void snake() {
		int originx = 1;
		int boundx = 60;
		int originy = 1;
		int boundy = 15;
		int rand1x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand1y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point playerPosition = new java.awt.Point(rand1x, rand1y);
		int rand2x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand2y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point snakePosition = new java.awt.Point(rand2x, rand2y);
		int rand2xs = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand2ys = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point snake2Position = new java.awt.Point(rand2xs, rand2ys);
		int rand3x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand3y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point goldPosition = new java.awt.Point(rand3x, rand3y);
		int rand5x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand5y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point gold2Position = new java.awt.Point(rand5x, rand5y);
		int rand4x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand4y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point doorPosition = new java.awt.Point(rand4x, rand4y);
		int randxh = ThreadLocalRandom.current().nextInt(originx, boundx);
		int randyh = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point holePosition = new java.awt.Point(randxh, randyh);
		int randxh1 = ThreadLocalRandom.current().nextInt(originx, boundx);
		int randyh1 = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point hole2Position = new java.awt.Point(randxh1, randyh1);
		Point[] arr = { snakePosition, snake2Position };
		java.awt.Point d = new java.awt.Point(-1, -1);

		int money = 0;

		while (true) {
			// game field with different objects

			for (int y = 0; y < boundy; y++) {
				for (int x = 0; x < boundx; x++) {
					java.awt.Point p = new java.awt.Point(x, y);
					if (playerPosition.equals(p)
							&& (!playerPosition.equals(snake2Position) && !playerPosition.equals(snakePosition)))
						System.out.print('P');
					else if (snakePosition.equals(p) && !snakePosition.equals(holePosition)
							&& !snakePosition.equals(hole2Position) && !snakePosition.equals(snake2Position))
						System.out.print('S');
					else if (goldPosition.equals(p) || gold2Position.equals(p))
						System.out.print('$');
					else if (doorPosition.equals(p))
						System.out.print('#');
					else if (holePosition.equals(p))
						System.out.print('O');
					else if (snake2Position.equals(p) && !snake2Position.equals(holePosition)
							&& !snake2Position.equals(hole2Position) && !snake2Position.equals(snakePosition))
						System.out.print('8');
					else if (hole2Position.equals(p))
						System.out.print('O');
					else
						System.out.print('.');
				}
				System.out.println();
			}

			// check if snake or player is in the hole, or if the player has
			// been eaten, or has won

			if (money == 2 && playerPosition.equals(doorPosition)) {
				System.out.println("You won and unlocked the secret level!!");
				//schlange2();
				break;
			}
			if (playerPosition.equals(snakePosition) || playerPosition.equals(snake2Position)) {
				System.out.println("The snake got ya!");
				resp();
				break;
			}
			if (snake2Position.equals(snakePosition) && !snake2Position.equals(d)) {
				System.out.println("They have eaten themself! Very clever from you! You unlocked the secret level!!");
				//schlange2();
				break;
			}
			if (playerPosition.equals(holePosition) || playerPosition.equals(hole2Position)) {
				System.out.println("You've fallen into the hole!");
				resp();
				break;
			}
			if (snakePosition.equals(holePosition) || snakePosition.equals(hole2Position)) {
				snakePosition = d;
				if (snake2Position.equals(d) && snakePosition.equals(d)) {
					System.out.println("Both snakes dead, very good! You win!");
					resp();
					break;
				}
				System.out.println("First snake dead, one more to go!");

			}
			if (snake2Position.equals(holePosition) || snake2Position.equals(hole2Position)) {
				snake2Position = d;
				if (snakePosition.equals(d) && snake2Position.equals(d)) {
					System.out.println("Both snakes dead, very good! You win!");
					resp();
					break;
				}
				System.out.println("First snake dead, one more to go!");
			}
			if (playerPosition.equals(goldPosition)) {
				money += 1;
				goldPosition.setLocation(-1, -1);
			}
			if (playerPosition.equals(gold2Position)) {
				money += 1;
				gold2Position.setLocation(-1, -1);
			}

			// console input changes the position of the player

			switch (new java.util.Scanner(System.in).next()) {
			case "i":
				playerPosition.y = Math.max(0, playerPosition.y - 1);
				break; // moves one position up
			case "w":
				playerPosition.y = Math.max(0, playerPosition.y - 2);
				break;// moves two position up
			case "k":
				playerPosition.y = Math.min(boundy - 1, playerPosition.y + 1);
				break; // moves one position down
			case "s":
				playerPosition.y = Math.min(boundy - 1, playerPosition.y + 2);
				break; // moves two position down
			case "j":
				playerPosition.x = Math.max(0, playerPosition.x - 1);
				break; // moves one position left
			case "a":
				playerPosition.x = Math.max(0, playerPosition.x - 2);
				break; // moves two position left
			case "l":
				playerPosition.x = Math.min(boundx - 1, playerPosition.x + 1);
				break; // moves one position right
			case "d":
				playerPosition.x = Math.min(boundx - 1, playerPosition.x + 2);
				break; // moves two position right
			// any other input will cause the player to not change his position
			}

			// snake movement

			for (Point m : arr) {
				if (playerPosition.x < m.x && m.x > -1)
					m.x--;
				else if (playerPosition.x > m.x && m.x > -1)
					m.x++;
				if (playerPosition.y < m.y && m.y > -1)
					m.y--;
				else if (playerPosition.y > m.y && m.y > -1)
					m.y++;
			}
		} // end while
	}

	private static void snake2() {
		int originx = 1;
		int boundx = 20;
		int originy = 1;
		int boundy = 7;
		int rand1x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand1y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point playerPosition = new java.awt.Point(rand1x, rand1y);
		int rand2x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand2y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point snakePosition = new java.awt.Point(rand2x, rand2y);
		int rand2xs = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand2ys = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point snake2Position = new java.awt.Point(rand2xs, rand2ys);
		int rand3x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand3y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point goldPosition = new java.awt.Point(rand3x, rand3y);
		int rand5x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand5y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point gold2Position = new java.awt.Point(rand5x, rand5y);
		int rand4x = ThreadLocalRandom.current().nextInt(originx, boundx);
		int rand4y = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point doorPosition = new java.awt.Point(rand4x, rand4y);
		int randxh = ThreadLocalRandom.current().nextInt(originx, boundx);
		int randyh = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point holePosition = new java.awt.Point(randxh, randyh);
		int randxh1 = ThreadLocalRandom.current().nextInt(originx, boundx);
		int randyh1 = ThreadLocalRandom.current().nextInt(originy, boundy);
		java.awt.Point hole2Position = new java.awt.Point(randxh1, randyh1);
		Point[] arr = { snakePosition, snake2Position };
		java.awt.Point d = new java.awt.Point(-1, -1);

		int money = 0;

		while (true) {
			// game field with different objects

			for (int y = 0; y < boundy; y++) {
				for (int x = 0; x < boundx; x++) {
					java.awt.Point p = new java.awt.Point(x, y);
					if (playerPosition.equals(p)
							&& (!playerPosition.equals(snake2Position) && !playerPosition.equals(snakePosition)))
						System.out.print('P');
					else if (snakePosition.equals(p) && !snakePosition.equals(holePosition)
							&& !snakePosition.equals(hole2Position) && !snakePosition.equals(snake2Position))
						System.out.print('S');
					else if (goldPosition.equals(p) || gold2Position.equals(p))
						System.out.print('$');
					else if (doorPosition.equals(p))
						System.out.print('#');
					else if (holePosition.equals(p))
						System.out.print('O');
					else if (snake2Position.equals(p) && !snake2Position.equals(holePosition)
							&& !snake2Position.equals(hole2Position) && !snake2Position.equals(snakePosition))
						System.out.print('8');
					else if (hole2Position.equals(p))
						System.out.print('O');
					else
						System.out.print('.');
				}
				System.out.println();
			}

			// check if snake or player is in the hole, or if the player has
			// been eaten, or has won

			if (money == 2 && playerPosition.equals(doorPosition)) {
				System.out.println("You won the secret level!");
				resp();
				break;
			}
			if (playerPosition.equals(snakePosition) || playerPosition.equals(snake2Position)) {
				System.out.println("The snake got ya!");
				resp();
				break;
			}
			if (snake2Position.equals(snakePosition) && !snake2Position.equals(d)) {
				System.out.println("They have eaten themself! Very clever from you!You won the secret level!");
				resp();
				break;
			}
			if (playerPosition.equals(holePosition) || playerPosition.equals(hole2Position)) {
				System.out.println("You've fallen into the hole!");
				resp();
				break;
			}
			if (snakePosition.equals(holePosition) || snakePosition.equals(hole2Position)) {
				snakePosition = d;
				if (snake2Position.equals(d) && snakePosition.equals(d)) {
					System.out.println("Both snakes dead, very good! You won the secret level!");
					resp();
					break;
				}
				System.out.println("First snake dead, one more to go!");

			}
			if (snake2Position.equals(holePosition) || snake2Position.equals(hole2Position)) {
				snake2Position = d;
				if (snakePosition.equals(d) && snake2Position.equals(d)) {
					System.out.println("Both snakes dead, very good! You won the secret level!");
					resp();
					break;
				}
				System.out.println("First snake dead, one more to go!");
			}
			if (playerPosition.equals(goldPosition)) {
				money += 1;
				goldPosition.setLocation(-1, -1);
			}
			if (playerPosition.equals(gold2Position)) {
				money += 1;
				gold2Position.setLocation(-1, -1);
			}

			// console input changes the position of the player

			switch (new java.util.Scanner(System.in).next()) {
			case "w":
				playerPosition.y = Math.max(0, playerPosition.y - 1);
				break; // moves one position up
			case "s":
				playerPosition.y = Math.min(boundy - 1, playerPosition.y + 1);
				break; // moves one position down
			case "a":
				playerPosition.x = Math.max(0, playerPosition.x - 1);
				break; // moves one position left
			case "d":
				playerPosition.x = Math.min(boundx - 1, playerPosition.x + 1);
				break; // moves one position right
			// any other input will cause the player to not change his position
			}

			// snake movement

			for (Point m : arr) {
				if (playerPosition.x < m.x && m.x > -1)
					m.x--;
				else if (playerPosition.x > m.x && m.x > -1)
					m.x++;
				if (playerPosition.y < m.y && m.y > -1)
					m.y--;
				else if (playerPosition.y > m.y && m.y > -1)
					m.y++;
			}
		} // end while
	}

	public static void main(String[] args) {
		snake();
	}
}

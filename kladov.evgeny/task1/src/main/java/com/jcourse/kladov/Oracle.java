package com.jcourse.kladov;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

@Log
public class Oracle implements Runnable {
	public String toString() {
		return "Oracle game";
	}

	public void run() {
		OracleUI ui = new OracleUI();
		ui.startGame(new OracleImpl());
	}

	class OracleImpl {

		private final static int MAX_NUMBER_VALUE = 100;
		private final static int MAX_NUMBER_OF_TRIES = 8;
		@Getter
		private int number;
		@Getter
		private int triesCounter;

		/**
		 * Reset number of tries and generate new number
		 */
		public void start() {
			triesCounter = MAX_NUMBER_OF_TRIES;
			number = new Random(System.currentTimeMillis()).nextInt() % MAX_NUMBER_VALUE;
		}

		/**
		 * Check guess and return -1 if less, +1 if greater, 0 otherwise
		 */
		public int checkGuess(Integer value) {
			triesCounter--;
			int diff = value - number;

			if (diff < 0)
				return -1;
			else if (diff > 0)
				return +1;

			return 0;
		}

		public boolean checkLooseCondition() {
			return triesCounter == 0;
		}

		public int getMaxNumberValue() {
			return MAX_NUMBER_VALUE;
		}
	}

	class OracleUI {
		public void startGame(OracleImpl impl) {
			System.out.printf("Starting new round, random number is [0..%d) \n", impl.getMaxNumberValue());

			impl.start();

			while (!impl.checkLooseCondition()) {
				System.out.printf("Make a guess, %d tries left\n", impl.getTriesCounter());
				Integer n;

				try {
					n = Integer.valueOf(new BufferedReader(new InputStreamReader(System.in)).readLine());
				} catch (java.io.IOException e) {
					log.warning("IOExcetion on reading/writing with io streams: " + e.toString());
					return;
				}
				Integer result = impl.checkGuess(n);

				if (result.equals(0)) {
					System.out.println("You win!");
					return;
				} else if (result < 0) {
					System.out.println("Number is greater");
				} else
					System.out.println("Number is less");
			}

			System.out.printf("You loose, number is %d\n", impl.getNumber());
		}
	}
}
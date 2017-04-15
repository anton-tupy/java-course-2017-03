package com.jcourse.kladov;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Log
public class Echo implements Runnable {
	public void run() {
		if (System.console() == null)
			echoFromSystemIn();
		echoFromConsole();
	}

	@Override
	public String toString() {
		return "Echo taks";
	}

	private void echoFromSystemIn() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				System.out.println(reader.readLine());
			} catch (java.io.IOException e) {
				log.warning("IOExcetion on reading/writing with io streams: " + e.toString());
			}
		}
	}

	private void echoFromConsole() {
		while (true)
			System.out.println(System.console().readLine());
	}
}

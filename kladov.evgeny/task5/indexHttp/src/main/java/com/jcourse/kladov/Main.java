package com.jcourse.kladov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Please provide port and folder path for indexing.");
			return;
		}

		final int port = Integer.valueOf(args[0]);
		final String path = args[1];

		ServerSocket s = new ServerSocket(port);

		while(true){
			Socket clientS = s.accept();
			new Thread(new IndexHttpClient(path, clientS)).start();
		}
	}
}

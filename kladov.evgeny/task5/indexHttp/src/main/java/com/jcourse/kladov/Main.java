package com.jcourse.kladov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static void main(String[] args) throws IOException {
		int port = 8080;
		String auth = "";
		String root = "";
		String provider = "<not set>";

		for (int arg = 0; arg < args.length; ) {
			String curArg = args[arg];
			String nextArg = arg + 1 < args.length ? args[arg + 1] : null;

			if (curArg.equals("-port")) {
				port = Integer.valueOf(nextArg);
				arg += 2;
			} else if (curArg.equals("-root")) {
				root = nextArg;
				arg += 2;
			} else if (curArg.equals("-auth")) {
				auth = nextArg;
				arg += 2;
			} else if (curArg.equals("-provider")) {
				provider = nextArg;
				arg += 2;
			} else {
				System.out.println("Unknown argument " + curArg + ", skipped..");
				arg += 1;
			}
		}

		DataProvider dataProvider = null;

		if (provider.equals("fs"))
			dataProvider = new FileSystemDataProvider(root);
		else if (provider.equals("dropbox"))
			dataProvider = new DropboxDataProvider(auth);
		else
			System.out.println("Unsupported data provider: " + provider);

		ServerSocket s = new ServerSocket(port);

		while(true){
			Socket clientS = s.accept();
			new Thread(new IndexHttpClient(dataProvider, clientS)).start();
		}
	}
}

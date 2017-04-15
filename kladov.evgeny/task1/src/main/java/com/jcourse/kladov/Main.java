import com.jcourse.kladov.Echo;
import com.jcourse.kladov.Oracle;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Log
public class Main {
	public static void main(String... args) {
		log.info("Main has been started");
		log.info(Integer.toString(args.length) + " args provided");
		log.info("File encoding is " + System.getProperty("file.encoding"));

		Runnable tasks[] = {
				new Echo(), new Oracle()
		};
		System.out.println("Available commands:");

		for (int i = 0, n = tasks.length; i < n; ++i)
			System.out.printf("[%d] %s\n", i, tasks[i].toString());

		Integer selection = 0;
		System.out.println("Select task number: ");

		try {
			selection = Integer.valueOf(new BufferedReader(new InputStreamReader(System.in)).readLine());
		} catch (java.io.IOException e) {
			log.warning("IOExcetion on reading/writing with io streams: " + e.toString());
		}

		if (selection < tasks.length) {
			System.out.printf("Starting %s...\n", tasks[selection].toString());
			tasks[selection].run();
		} else
			System.out.println("Invalid selected value");
	}
}
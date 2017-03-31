package com.jcourse.kladov;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class StackCalcCommandFactory implements CommandFactory {
	private boolean debug = false;
	private Map<String, Command> commands = new HashMap<>();

	StackCalcCommandFactory(String fileName) {
		Properties cmdList = new Properties();

		try {
			InputStream in = StackCalc.class.getResourceAsStream(fileName);
			cmdList.load(in);
			in.close();
		} catch (IOException e) {
			System.err.printf("IOException: %s", e.toString());
		}
		for (String key : cmdList.stringPropertyNames()) {
			// check for reserved keywords
			if (key.equals("DEBUG")) {
				debug = cmdList.getProperty("DEBUG").equals("ON");
				continue;
			}

			String cmdClassName = cmdList.getProperty(key);
			Object cmdInstance = null;

			try {
				Class cmdClass = StackCalc.class.getClassLoader().loadClass(cmdClassName);
				try {
					cmdInstance = cmdClass.newInstance();
				} catch (InstantiationException e) {
					System.err.printf("Cannot create instance of class %s\n", cmdClassName);
				} catch (IllegalAccessException e) {
					System.err.printf("Constructor of class %s is not accessible\n", cmdClassName);
				}
			} catch (ClassNotFoundException e) {
				System.err.printf("Class %s not found", cmdClassName);
			}

			if (cmdInstance != null && cmdInstance instanceof Command)
				registerCommand(key, ((Command) cmdInstance));
			else
				System.err.printf("Command %s not registered", cmdClassName);
		}
	}

	private void registerCommand(String name, Command cmd) {
		commands.put(name, cmd);
	}

	@Override
	public Command getCommandByName(String name) {
		return commands.get(name);
	}

	public Set<String> getCommandNames() {
		return commands.keySet();
	}

	public boolean isDebugMode() {
		return debug;
	}
}

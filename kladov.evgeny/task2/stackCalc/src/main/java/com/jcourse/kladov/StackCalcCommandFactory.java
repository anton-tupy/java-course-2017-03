package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Log4j
public class StackCalcCommandFactory implements CommandFactory {
	private boolean debug = false;
	private Map<String, Command> commands = new HashMap<>();

	StackCalcCommandFactory(String fileName) {
		Properties cmdList = new Properties();

		try {
			InputStream in = StackCalcCommandFactory.class.getClassLoader().getResourceAsStream(fileName);
			cmdList.load(in);
			in.close();
		} catch (IOException e) {
			log.error(e);
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
					log.error(String.format("Cannot create instance of class %s\n", cmdClassName));
				} catch (IllegalAccessException e) {
					log.error(String.format("Constructor of class %s is not accessible\n", cmdClassName));
				}
			} catch (ClassNotFoundException e) {
				log.error(String.format("Class %s not found", cmdClassName));
			}

			if (cmdInstance != null && cmdInstance instanceof Command)
				registerCommand(key, ((Command) cmdInstance));
			else
				log.error(String.format("Command %s not registered", cmdClassName));
		}
	}

	private void registerCommand(String name, Command cmd) {
		commands.put(name, cmd);
	}

	@Override
	public Command getCommandByName(String name) {
		try {
			return commands.get(name).getClass().newInstance();
		} catch (InstantiationException e) {
			log.error(String.format("Cannot create instance of class %s\n", name));
		} catch (IllegalAccessException e) {
			log.error(String.format("Constructor of class %s is not accessible\n", name));
		}
		return null;
	}

	public Set<String> getCommandNames() {
		return commands.keySet();
	}

	public boolean isDebugMode() {
		return debug;
	}
}

package com.jcourse.stackcalc;

import com.jcourse.stackcalc.commands.Command;
import com.jcourse.stackcalc.commands.CommandArgument;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Anatoliy on 01.04.2017.
 */

public class CommandFactory {
	private final Properties properties;
	private final Stack stack;
	private final Map<String, Double> environment;

	public CommandFactory(Properties prop, Stack stack, Map<String, Double> environment) {
		this.properties = prop;
		this.stack = stack;
		this.environment = environment;
	}

	public Command createCommand(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		String className = properties.getProperty(name);
		try {
			Class<?> CommandClass = Class.forName(className);
			Object commandObject = CommandClass.newInstance();
			Object proxyCommand = createProxy(commandObject, stack, environment);
			Field[] fields = CommandClass.getDeclaredFields();
			for (Field field : fields) {
				In inAnnotation = field.getAnnotation(In.class);
				if (inAnnotation != null) {
					field.setAccessible(true);
					CommandArgument arg = inAnnotation.arg();
					switch (arg) {
						case STACK:
							field.set(commandObject, stack);
							break;
						case CONTEXT:
							field.set(commandObject, environment);
							break;
					}
				}
			}
			return (Command) proxyCommand ;
		} catch(ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			throw new RuntimeException(e);
		}
	}

	private static String printEnv(Map<String, Double> environment) {
		if (environment.size() == 0)
			return "Environment is clear";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Environment is:\n");
		for (Map.Entry<String, Double> entry : environment.entrySet()) {
			stringBuilder.append(String.format ("key %s : val %s\n", entry.getKey(), entry.getValue()));
		}
		return stringBuilder.toString();
	}

	private static String printArgs(Object[] args) {
		if (args.length == 0)
			return "no arguments";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Arguments is :\n");
		for (int i = 0; i < args.length; ++i)
			stringBuilder.append(args[i].toString());
		return stringBuilder.toString();
	}

	// В этом метод происходи создание dynamic proxy (его класс создается в run time), реализующего интерфейс Command
	private static Command createProxy(final Object instance, Stack stack, Map<String, Double> environment) {
		return (Command) Proxy.newProxyInstance(CommandFactory.class.getClassLoader(), new Class[]{Command.class}, new InvocationHandler() {
			// Этот метод вызывается при любом вызове любого метода созданного класса
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.printf("\nCalled method: %s on Class %s\n", method.getName(), instance.toString());
				System.out.printf("BEFORE %s\n", stack.print());
				System.out.printf("%s\n", printEnv(environment));
				System.out.printf("%s\n", printArgs(args));
				Object result = method.invoke(instance, args);
				System.out.printf("AFTER = %s\n", stack.print());
				return result;
			}
		});
	}
}

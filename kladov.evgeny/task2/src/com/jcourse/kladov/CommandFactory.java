package com.jcourse.kladov;

public interface CommandFactory {
	Command getCommandByName(String name);
}

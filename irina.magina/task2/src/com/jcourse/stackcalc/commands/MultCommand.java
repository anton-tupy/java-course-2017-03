package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Игорь on 28.03.2017.
 */
public class MultCommand implements Command {
    @Override
    public String getName() {
        return "*";
    }

    @Override
    public void execute(String arg, Stack stack, Map<String, Double> environment) {
        Double a = stack.pop();
        Double b = stack.pop();
        stack.push(a*b);
    }
}

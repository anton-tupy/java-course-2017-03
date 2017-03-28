package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;

/**
 * Created by Игорь on 28.03.2017.
 */
public class SqrtCommand implements Command {
    @Override
    public String getName() {
        return "SQRT";
    }

    @Override
    public void execute(String arg, Stack stack, Map<String, Double> environment) throws RuntimeException {
        Double a = stack.pop();
        if (a < 0) {throw new RuntimeException("SQRT " + a + " <0! ");}
        stack.push(Math.sqrt(a));
    }
}


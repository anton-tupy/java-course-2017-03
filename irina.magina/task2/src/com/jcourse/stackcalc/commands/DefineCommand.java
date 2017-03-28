package com.jcourse.stackcalc.commands;

import com.jcourse.stackcalc.Stack;

import java.util.Map;



/**
 * Created by Игорь on 27.03.2017.
 */
public class DefineCommand implements Command{

    @Override
    public String getName() {
        return "DEFINE";
    }

    @Override
    public void execute(String arg, Stack stack, Map<String, Double> environment) {
        //System.out.println(environment.);

    }
}

package com.jcourse.stackcalc;

import com.jcourse.stackcalc.commands.CommandArgument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Anatoliy on 01.04.2017.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface In {
	CommandArgument arg();
}

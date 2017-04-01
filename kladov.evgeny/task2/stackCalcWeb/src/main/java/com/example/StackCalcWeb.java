package com.example;

import com.jcourse.kladov.StackCalc;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Map;

@Controller
@SpringBootApplication
@Log4j
public class StackCalcWeb {
	ByteArrayOutputStream debugStream = new ByteArrayOutputStream();
	ByteArrayOutputStream printStream = new ByteArrayOutputStream();
	StackCalc calc;

	public static void main(String[] args) {
		SpringApplication.run(StackCalcWeb.class, args);
	}

	@RequestMapping(value = "/evaluate", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> evaluate(@RequestParam("data") String data) {
		try {
			if (data == null || data.equals("")) {
				return Ajax.successResponse(new String("Nothing to evaluate"));
			}

			printStream.reset();
			calc = new StackCalc(new BufferedReader(new StringReader(data)), new PrintStream(printStream), new PrintStream(debugStream));
			calc.evaluate();
			String result = String.format("Evaluate '%s' expression, result is: '%s'", data, printStream.toString());

			return Ajax.successResponse(result, debugStream.toString());
		} catch (Exception e) {
			log.error("Evaluate error", e);
			return Ajax.errorResponse(e.toString());
		}
	}

	@RequestMapping(value = "/step", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> step() {
		try {
			printStream.reset();
			debugStream.reset();
			calc.step();
			String result = String.format("Step result is: '%s'", printStream.toString());

			return Ajax.successResponse(result, debugStream.toString());
		} catch (Exception e) {
			log.error("Step error", e);
			return Ajax.errorResponse(e.toString());
		}
	}

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> start(@RequestParam("data") String data) {
		try {
			if (data == null || data.equals("")) {
				return Ajax.successResponse(new String("Nothing to evaluate"));
			}
			printStream.reset();
			debugStream.reset();
			calc = new StackCalc(new BufferedReader(new StringReader(data)), new PrintStream(printStream), new PrintStream(debugStream));
			calc.start();
			String result = String.format("Start evaluating '%s' expression", data);

			return Ajax.successResponse(result);
		} catch (Exception e) {
			log.error("Start error", e);
			return Ajax.errorResponse(e.getStackTrace().toString());
		}
	}
}

package com.jcourse.kladov;

public interface ExceptionGenerator {
	void generateNullPointerException();
	void generateClassCastException();
	void generateNumberFormatException();
	void generateStackOverflowError();
	void generateOutOfMemoryError();
	void generateMyException(String message) throws MyException;
}
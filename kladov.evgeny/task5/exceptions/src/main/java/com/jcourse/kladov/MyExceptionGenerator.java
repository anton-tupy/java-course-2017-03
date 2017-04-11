package com.jcourse.kladov;

public class MyExceptionGenerator implements ExceptionGenerator {
	@Override
	public void generateNullPointerException() {
		Object obj = null;
		obj.toString();
	}

	@Override
	public void generateClassCastException() {
		((MyException)(Object)this).toString();
	}

	@Override
	public void generateNumberFormatException() {
		Integer.parseInt("A");
	}

	@Override
	public void generateStackOverflowError() {
		generateStackOverflowError();
	}

	@Override
	public void generateOutOfMemoryError() {
		while (true) {
			int [] a = new int[Integer.MAX_VALUE];
		}
	}

	@Override
	public void generateMyException(String message) throws MyException {
		throw new MyException(message);
	}

	public static void main(String[] args) {
		MyExceptionGenerator exceptionGenerator = new MyExceptionGenerator();

		try {
			exceptionGenerator.generateNullPointerException();
		} catch (NullPointerException e) {
			System.out.println("Caught NullPointerException");
			e.printStackTrace();
		}

		try {
			exceptionGenerator.generateClassCastException();
		} catch (ClassCastException e) {
			System.out.println("Caught NullPointerException");
			e.printStackTrace();
		}

		try {
			exceptionGenerator.generateNumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("Caught NumberFormatException");
			e.printStackTrace();
		}

		try {
			exceptionGenerator.generateStackOverflowError();
		} catch (StackOverflowError e) {
			System.out.println("Caught StackOverflowError");
			e.printStackTrace();
		}

		try {
			exceptionGenerator.generateOutOfMemoryError();
		} catch (OutOfMemoryError e) {
			System.out.println("Caught OutOfMemory");
			e.printStackTrace();
		}

		try {
			exceptionGenerator.generateMyException("This is MyException");
		} catch (MyException e) {
			System.out.println("Caught MyException");
			e.printStackTrace();
		}
	}
}

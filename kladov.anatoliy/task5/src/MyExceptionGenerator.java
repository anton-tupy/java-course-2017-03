/**
 * Created by Anatoliy on 09.04.2017.
 */
public class MyExceptionGenerator implements ExceptionGenerator {
	@Override
	public void generateNullPointerException() {
		Object obj = null;
		obj.hashCode();
	}

	@Override
	public void generateClassCastException() {
		Object exceptionObj = new MyException("classCastException");
		System.out.println((MyExceptionGenerator)exceptionObj);
	}

	@Override
	public void generateNumberFormatException() {
		String str = "notanumber";
		int n = Integer.parseInt(str);
	}

	@Override
	public void generateStackOverflowError() {
		while (true)
			generateStackOverflowError();
	}

	@Override
	public void generateOutOfMemoryError() {
		int[] array = new int[Integer.MAX_VALUE];
	}

	@Override
	public void generateMyException(String message) throws MyException {
		throw new MyException(message);
	}
}

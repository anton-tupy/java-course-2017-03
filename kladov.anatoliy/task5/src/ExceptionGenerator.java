/**
 * Created by Anatoliy on 09.04.2017.
 */
public interface ExceptionGenerator {
	void generateNullPointerException();
	void generateClassCastException();
	void generateNumberFormatException();
	void generateStackOverflowError();
	void generateOutOfMemoryError();
	void generateMyException(String message) throws MyException;
}

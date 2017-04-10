/**
 * Created by Anatoliy on 09.04.2017.
 */

	/*Методы класса должны выбрасывать соответствующие исключения (по имени метода).
		Для первых 5-и методов нельзя использовать директиву throw. Надо написать такой код,
		который будет сам генерировать исключение. В последнем методе использовать throw
		для выбрасывания собственного исключения MyException, который содержит message
		, заданный при вызове метода. Класс MyException	написать самим.
		Добавить метод main(), в котором тестируются все методы класса. Каждый вызов
		оборачивается try/catch, и stack trace исключения выводится на экран с помощью функции
		printStackTrace().

		Работа с файлами. Написать генератор index.html	файла. То есть для заданной директории создать
		HTML файл, который содержит кликабельный список всех директорий и файлов внутри заданной
		директории. При этом:
		•В начале идёт директория .. которая указывает на верх дерева файловой системы.
		•Затем идёт список директорий отсортированный по именам
		•Потом список файлов отсортированный по именам
		•Кроме того показывать информацию о дате модификации и размере (для файлов)
		*/
public class Main {
	public static void main(String[] args) {
		MyExceptionGenerator g = new MyExceptionGenerator();
		try {
			g.generateNullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
			g.generateClassCastException();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		try {
			g.generateNumberFormatException();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			g.generateStackOverflowError();
		} catch (StackOverflowError e) {
			e.printStackTrace();
		}

		try {
			g.generateOutOfMemoryError();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}

		try {
			g.generateMyException("my exception generated");
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
}

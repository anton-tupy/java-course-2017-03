Генерация исключений
Написать класс, реализующий интерфейс:
public interface ExceptionGenerator {
void generateNullPointerException();
void generateClassCastException();
void generateNumberFormatException();
void generateStackOverflowError();
void generateOutOfMemoryError();
void generateMyException(String message) throws MyException;
}

Методы класса должны выбрасывать соответствующие исключения (по имени метода).
Для первых 5-и методов нельзя использовать директиву
throw. Надо написать такой код, который будет сам генерировать исключение.
В последнем методе использовать throw  для выбрасывания собственного исключения MyException,
который содержит message, заданный при вызове метода. Класс MyException написать самим.

Добавить метод main(), в котором тестируются все методы класса. Каждый вызов
оборачивается try/catch, и stack trace исключения выводится на экран с помощью функции printStackTrace().

Работа с файлами
Написать генератор index.html файла. То есть для заданной директории создать HTML
файл, который содержит кликабельный список всех директорий и файлов внутри заданной
директории. При этом:
    • В начале идёт директория .. которая указывает на верх дерева файловой системы.
    • Затем идёт список директорий отсортированный по именам
    • Потом список файлов отсортированный по именам
    • Кроме того показывать информацию о дате модификации и размере (для файлов)

Дополнительно - простейший HTTP сервер
Используя результат  предыдущего задания написать простейший HTTP сервер, который
будет принимать  в качестве аргументов порт и директорию доступ до которой надо дать по HTTP.

Реализовать две команды:
• GET — получить ресурс
• HEAD — получить только заголовки от GET без самого ресурса

Если запрошенная директория содержит файл index.html сервер возвращает его, иначе
использует результат предыдущего задания для создания листинга директории. Реализовать
обработку ошибок:
• Если ресурс отсутствует - возвращаем 404
• Если произошло исключение - 500 и текст ошибки
• Неизвестная команда -501 Not Implemented

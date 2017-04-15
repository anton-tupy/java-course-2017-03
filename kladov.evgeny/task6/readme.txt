Создать мавен проект и подключить зависимость от H2 Db
1. New projects → choose Maven modle
2. На втором шаге:
1. GroupId — имя корнего пакета внутри приложения
2. ArtifactId — имя дистрибутива приложения
3. Выбрать «Create from archetype» «maven-archetype-quickstart»
3. Разрешить Auto import from maven
4. В файле pom.xml в секции <dependencies> добавить зависимость от H2 Db убедиться, что удается подсоединиться к базе данных
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.3.170</version>
</dependency>

Написать консольное приложение для ведения гостевой книги
1. Создать таблицу posts с полями
    ID  - число первичный ключ
    postDate — дата
    postMessage -- текст записи в блоге
2. Написать контроллер класс реализующий интерфейс
public interface GuestBookController{
    void addRecord(String message);
    List<Record> getRecords();  //Record {id, postDate, message}
}
3. Написать консольное приложение с двумя командами add, list
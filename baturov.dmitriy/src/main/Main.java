package main;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Дмитрий on 23.03.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        String s = new String("str");
        Main main = new Main();
        main.myPrint("asd");
        Integer obj1 = new Integer(5);
        Integer obj2 = new Integer(5);
        System.out.println(obj1.equals(obj2));

        String readerStr;

        Console console = System.console();
        if (console == null){
            System.err.println("No console");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            readerStr = br.readLine();
        }
        else {
            System.err.println("Read from console");
            readerStr = console.readLine();
        }
        System.out.println(readerStr);
        System.getProperty("file.encoding");
    }

    public void myPrint(String str){
        System.out.println(str);
        System.out.println(str);
    }
}

package com.jcource.baturov.seminar.first;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Дмитрий on 25.03.2017.
 */
public class UserInterface {

    int num;

    UserInterface() throws IOException {
    }

    public int getNum() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        num = Integer.parseInt(br.readLine());
        return num;
    }

    void Less() {
        System.out.println("заданное число меньше");
        //return "заданное число меньше";
    }

    void More(){
        System.out.println("заданное число больше");
        //return "заданное число больше";
    }

    void Right(){
        System.out.println("угадали!");
        //return "угадали!";
    }

    String HeCouldNot(){
        return "Заданное число = ";
    }
}

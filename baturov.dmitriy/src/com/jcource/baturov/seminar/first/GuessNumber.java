package com.jcource.baturov.seminar.first;

import org.omg.CORBA.SystemException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Дмитрий on 25.03.2017.
 */
public class GuessNumber {

    public static void main(String[] args) throws IOException{
       /* int s = (int) (Math.random() * 100);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num;
        int count = 0;

        while (count < 8){
            num = Integer.parseInt(br.readLine());
            if (s == num) {
                System.out.println("угадали!");
                count = 8;
            } else if (num < s){
                System.out.println("заданное число больше");
                count++;
            } else if (num > s){
                System.out.println("заданное число меньше");
                count++;
            }
            if (count > 7 && s!=num)
            System.err.println("Заданное число = " + s);


        }*/

       GameLogic gl = new GameLogic();
       gl.ProccessingNumber();

    }




}

package com.jcource.baturov.seminar.first;

import java.io.IOException;

/**
 * Created by Дмитрий on 25.03.2017.
 */
public class GameLogic {

    int count = 0, num;

    GameLogic(){
    }

     void ProccessingNumber() throws IOException {
        int s = (int) (Math.random() * 100);
        UserInterface ui = new UserInterface();
        while (count < 8){

            num = ui.getNum();

            if (s == num) {
                ui.Right();
                count = 8;
            } else if (num < s){
                ui.More();
                count++;
            } else if (num > s){
                ui.Less();
                count++;
            }
            if (count > 7 && s != num) {
                System.err.println(ui.HeCouldNot() + s);
                break;
            }

        }
    }


}

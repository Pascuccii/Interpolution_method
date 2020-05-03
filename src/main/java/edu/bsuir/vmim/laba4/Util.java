package edu.bsuir.vmim.laba4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Alesha on 06.10.2016.
 */
public class Util {

    public static int getInt () {
        int value;
        do {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                value = Integer.valueOf(in.readLine());
                break;
            }
            catch (Exception e) {
                System.err.println("Вы ввели неверное число");
            }
        }while (true);
        return value;
    }

    public static double getDouble () {
        double value;
        do {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                value = Double.valueOf(in.readLine());
                break;
            }
            catch (Exception e) {
                System.err.println("Вы ввели неверное число");
            }
        }while (true);
        return value;
    }
}

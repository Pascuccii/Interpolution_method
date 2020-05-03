package edu.bsuir.vmim.laba4;

import edu.bsuir.vmim.laba4.gauss.Algorithm;
import edu.bsuir.vmim.laba4.gauss.LinearSystem;
import edu.bsuir.vmim.laba4.gauss.MyEquation;

/**
 * Created by Alesha on 07.10.2016.
 */
public class Gauss {

    private static final int DEFAULT_EQUATIONS_NUMBER = 3;
    private static final int DEFAULT_VARIABLES_NUMBER = 3;

    public Double[] showGausRes(Double[][] matrix){
        LinearSystem<Double, MyEquation> list = setSystem(matrix);
        printSystem(list);
        int i, j;
        Algorithm<Double, MyEquation> alg = new Algorithm<Double, MyEquation>(list);
        try{
            alg.calculate();
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Double [] x = new Double[matrix.length];
        for(i = list.size() - 1; i >= 0; i--) {
            Double sum = Double.valueOf(0.0f);
            for(j = list.size() - 1; j > i; j--) {
                sum += list.itemAt(i, j) * x[j];
            }
            x[i] = (list.itemAt(i, list.size()) - sum) / list.itemAt(i, j);
        }
        printVector(x);
        return x;
    }


    public static LinearSystem<Double, MyEquation> setSystem(Double[][] hard_code){
        LinearSystem<Double, MyEquation> list = new LinearSystem<Double, MyEquation>();
        int i;
        for (i = 0; i < DEFAULT_EQUATIONS_NUMBER; i++){
            MyEquation eq = new MyEquation();
            eq.create(DEFAULT_VARIABLES_NUMBER + 1, hard_code[i]);
            list.push(eq);
        }
        return list;
    }

    public static void printSystem(LinearSystem<Double, MyEquation> system){
        for (int i = 0; i < system.size(); i++){
            MyEquation temp = system.get(i);
            String s = "";
            for (int j = 0; j < temp.size(); j++){
                s += String.format("%f; %s", system.itemAt(i, j), "\t");
            }
            System.out.println(s);
        }System.out.println("");
    }

    public static void printVector(Double [] x){
        String s = "";
        for (int i = 0; i < x.length; i++){
            s += String.format("x%d = %f; ", i, x[i]);
        }System.out.println(s);
    }

}

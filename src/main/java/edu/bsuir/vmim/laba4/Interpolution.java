package edu.bsuir.vmim.laba4;

import static edu.bsuir.vmim.laba4.Util.getInt;

/**
 * Created by Alesha on 06.10.2016.
 */
public class Interpolution {

    public static void main(String[] args) {
        int size;
        double[][] matrix, P;
        double F[];
        MatrixCalculation mc = new MatrixCalculation();

        System.out.println("Введите размер матрицы:");

        size = getInt();

        matrix = mc.getMatrix(size);

        F = mc.getF(matrix);

        P = mc.getInterpolationEquation(F);

        double[] a = mc.lsolve(P, F);

        double[] ownValue = mc.getOwnValue(a);

        for(int i = 0; i < ownValue.length; i++) {
            System.out.println("Собственне значени №" + (i + 1) + ":" + ownValue[i]);
        }

/*        double[][] ownVectors = mc.getOwnVector(ownValue, matrix);

        for (int i = 0; i < size; i++) {
            System.out.println("Собственный вектор №" + (i + 1) + ":");
            for (int j = 0; j < size; j++)
                System.out.print(ownVectors[i][j] + " ");
            System.out.println(" ");
        }*/
    }
}

package edu.bsuir.vmim.laba4;

import static edu.bsuir.vmim.laba4.Util.getDouble;
import static edu.bsuir.vmim.laba4.Constants.*;
/**
 * Created by Alesha on 12.09.2016.
 */

public class MatrixCalculation {

    public MatrixCalculation() { //конструктор класса - я его не использовал, но можно тут сделать заполнение матрицы и т.п..
    }

    //рекурсивная функция - вычисляет значение определителя. Если на входе определитель 2х2 - просто вычисляем (крест-на-крест), иначе раскладываем на миноры. Для каждого минора вычисляем ЕГО определитель, рекурсивно вызывая ту же функцию..
    public double CalculateMatrix(double[][] matrix){
        double calcResult=0.0;
        if (matrix.length==2){
            calcResult=matrix[0][0]*matrix[1][1]-matrix[1][0]*matrix[0][1];
        }
        else{
            int koeff=1;
            for(int i=0; i<matrix.length; i++){
                if(i%2==1){  //я решил не возводить в степень, а просто поставить условие - это быстрее. Т.к. я раскладываю всегда по первой (читай - "нулевой") строке, то фактически я проверяю на четность значение i+0.
                    koeff=-1;
                }
                else{
                    koeff=1;
                };
                //собственно разложение:
                calcResult += koeff*matrix[0][i]*this.CalculateMatrix(this.GetMinor(matrix,0,i));
            }
        }

        //возвращаем ответ
        return calcResult;
    }

    //функция, к-я возвращает нужный нам минор. На входе - определитель, из к-го надо достать минор и номера строк-столбцов, к-е надо вычеркнуть.
    private double[][] GetMinor(double[][] matrix, int row, int column){
        int minorLength = matrix.length-1;
        double[][] minor = new double[minorLength][minorLength];
        int dI=0;//эти переменные для того, чтобы "пропускать" ненужные нам строку и столбец
        int dJ=0;
        for(int i=0; i<=minorLength; i++){
            dJ=0;
            for(int j=0; j<=minorLength; j++){
                if(i==row){
                    dI=1;
                }
                else{
                    if(j==column){
                        dJ=1;
                    }
                    else{
                        minor[i-dI][j-dJ] = matrix[i][j];
                    }
                }
            }
        }

        return minor;

    }

    public double[][] getMatrix(int size) {
        double[][] matrix = new double[size][size];
        System.out.println("Введите исходную матрицу");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = getDouble();
            }
        }

        return matrix;
    }

    public double[] getF(double[][] matrix) {
        int size = matrix.length;
        double[] F = new double[size];
        double matrixA[][] = new double[size][size];

        for(int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                matrixA[i][j] = matrix[i][j];
            }
        }

        for(int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                matrixA[i][i] = matrix[i][i] - j;
            }
            F[j] = CalculateMatrix(matrixA);
        }
        return F;
    }

    public double[][] getInterpolationEquation (double[] F){
        int size = F.length;
        double[][] P = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                if (j == 0)
                    F[i] -= (Math.pow(-1, size) * Math.pow(i, size - j));
                else
                    P[i][j-1] = -(Math.pow(-1, size) * Math.pow(i, size - j));
            }
        }
        return P;
    }

    public static double[] getOwnValue(double[] koef) {
        double[] result = null;
        if(koef.length == 2) {
            result = new double[2];
            double d = koef[0]*koef[0] + 4*koef[1];
            result[0] = (koef[0] + Math.sqrt(d))/2;
            result[1] = (koef[0] - Math.sqrt(d))/2;
        }
        if(koef.length == 3) {
            result = new double[3];
            double Q = (koef[0]*koef[0] + 3*koef[1])/9;
            double R = (-2*Math.pow(koef[0], 3) - 9*koef[0]*koef[1] - 27*koef[2])/54;
            double t = Math.acos(R/(Math.sqrt(Math.pow(Q, 3))))/3;
            result[0] = -2*Math.sqrt(Q)*Math.cos(t) + (koef[0]/3);
            result[1] = -2*Math.sqrt(Q)*Math.cos(t + (2*Math.PI / 3)) + (koef[0]/3);
            result[2] = -2*Math.sqrt(Q)*Math.cos(t - (2*Math.PI / 3)) + (koef[0]/3);
        }
        return result;
    }

    public static double[] lsolve(double[][] A, double[] b) {
        int N  = b.length;

        for (int p = 0; p < N; p++) {

            int max = p;
            for (int i = p + 1; i < N; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

            if (Math.abs(A[p][p]) <= E) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }

            for (int i = p + 1; i < N; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < N; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        double[] x = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    public double[][] getOwnVector(double[] ownValue, double[][] matrix) {
        int size = matrix.length;
        double[][] ownVector = new double[size][size];

        for (int i = 0; i < ownValue.length; i++) {
            ownVector[i] = getOneVector(ownValue[i], matrix);
        }

        return ownVector;

    }

    protected double[] getOneVector(double ownValue, double[][] matrix) {
        int size = matrix.length;

        double[][] matrixNew = new double[size][size + 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (i == j)
                    matrixNew[i][i] = matrix[i][i] - ownValue;
                else
                    matrixNew[i][j] = matrix[i][j];
            matrixNew[i][size] = 0;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++)
                System.out.print(matrixNew[i][j] + " ");
            System.out.println(" ");
        }

        MatrixCalculation mc = new MatrixCalculation();
        double Result = mc.CalculateMatrix(matrixNew);

        if (Result == 0 && matrixNew.length == 2) {
            return matrixNew[0][0] > 0 && matrixNew[0][1] > 0 ? new double[]{1/Math.sqrt(2), -1/Math.sqrt(2)} : new double[]{1/Math.sqrt(2), 1/Math.sqrt(2)};
        }

        Gauss gauss = new Gauss();

        Double[][] matr = new Double[size][size + 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++)
                matr[i][j] = matrixNew[i][j];
        }

        Double[] res = gauss.showGausRes(matr);

        double[] res2 = new double[size];

        for (int i = 0; i < size; i++) {
            res2[i] = res[i];
        }

        double[] result = new double[size];
        int sum2 = 0;

        for (int i = 0; i < size; i++) {
            if(res[i].toString().substring(0,1).equals("-"))
                res2[i] = -1;
            else res2[i] = 1;
            sum2 += Math.pow(res2[i], 2);
        }

        for (int i = 0; i < size; i++) {
            result[i] = res2[i]/Math.sqrt(sum2);
        }

        return result;
        /*double eps = 0.1;

        double[] previousVariableValues = new double[size];
        for (int i = 0; i < size; i++) {
            previousVariableValues[i] = 0.0;
        }

        while (true) {

            double[] currentVariableValues = new double[size];

            for (int i = 0; i < size; i++) {

                currentVariableValues[i] = matrixNew[i][size];

                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        currentVariableValues[i] -= matrixNew[i][j] * previousVariableValues[j];
                    }
                }

                currentVariableValues[i] /= matrixNew[i][i];
            }

            double error = 0.0001;

            int flag = 0;
            for (int i = 0; i < size; i++) {
                error += Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                if (currentVariableValues[i] != 0) {
                    flag = 1;
                }
            }

            if (error < eps && flag == 1) {
                break;
            }

            previousVariableValues = currentVariableValues;
        }

        double[] result = new double[size];
        int sum2 = 0;

        for (int i = 0; i < size; i++) {
            sum2 += Math.pow(previousVariableValues[i], 2);
        }

        for (int i = 0; i < size; i++) {
            result[i] = previousVariableValues[i]/Math.sqrt(sum2);
        }

        return result;*/
    }
}

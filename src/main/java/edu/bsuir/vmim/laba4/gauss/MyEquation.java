package edu.bsuir.vmim.laba4.gauss;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MyEquation implements Gauss<Double, MyEquation> {
    private List<Double> equation = new ArrayList<Double>();
    public List<Double> getEquation(){
        return equation;
    }
    public void create(int size, Double[] numbers){
        if (size < 2) size = 2;
        this.equation.clear();
        for (int i = 0; i < size; i++){
            this.equation.add(numbers[i]);
        }
    }
    @Override
    public int size(){
        return equation.size();
    }
    @Override
    public void addEquation(MyEquation item){
        ListIterator<Double> i = equation.listIterator();
        ListIterator<Double> j = item.getEquation().listIterator();
        for(; i.hasNext() && j.hasNext();){
            Double a = i.next();
            Double b = j.next();
            i.set(a + b);
        }
    }
    @Override
    public void mul(Double coefficient){
        for(ListIterator<Double> i = equation.listIterator(); i.hasNext();){
            Double next = i.next();
            i.set(next * coefficient);
        }
    }
    @Override
    public Double findCoefficient(Double a, Double b){
        if (a == 0.0f) return Double.valueOf(1.0f);
        return -b/a;
    }
    @Override
    public Double at(int index){
        return equation.get(index);
    }
}

package thirdlab;

import java.util.ArrayList;
import java.util.List;

public class RowColumnMatrix {
    private final List<Double> di;
    private final List<Double> al;
    private final List<Double> au;
    private final List<Integer> ja;
    private final List<Integer> ia;

    private Double rand(Double min, Double max){
        max -= min;
        return (Math.random() * max) + min;
    }

    private int randInteger(Double min, Double max){
        max -= min;
        return (int)((Math.random() * max) + min);
    }

    public RowColumnMatrix(Integer n, Double min, Double max) {
        List<Double> temp_di = new ArrayList<>();
        List<Double> temp_al = new ArrayList<>();
        List<Double> temp_au= new ArrayList<>();
        List<Integer> temp_ja = new ArrayList<>();
        List<Integer> temp_ia = new ArrayList<>();

        for(int i = 0; i < n; i++)
        {
            Double rnd = rand(min, max);
            temp_di.add(rnd);
            rnd = rand(min, max);
            temp_al.add(rnd);
            rnd = rand(min, max);
            temp_au.add(rnd);
            Integer rnd2 = randInteger(min, max);
            temp_ja.add(rnd2);
            rnd2 = randInteger(min, max);
            temp_ia.add(rnd2);
        }
        this.di = temp_di;
        this.al = temp_al;
        this.au = temp_au;
        this.ja = temp_ja;
        this.ia = temp_ia;
    }

    private int size(){
        return di.size();
    }

    private Double getIJ(int i, int j){
        if(i == j) {
            return di.get(i);
        }
        if(i > j)
            return getLowTriangle(i, j);
        else
            return getHighTriangle(i, j);
    }

    private double getLowTriangle(int i, int j) {
        return getFromTriangle(i, j, true);
    }

    private double getHighTriangle(int i, int j) {
        return getFromTriangle(j, i, false);
    }

    private double getFromTriangle(int line, int indInLine, boolean low) {
        Integer realInJa = ia.get(line + 1) - ia.get(line);
        int offset = 0;
        for(;offset < realInJa; offset++){
            if(ja.get(ia.get(line) + offset) == indInLine){
                break;
            }
            else if (ja.get(ia.get(line) + offset) > indInLine){
                return 0;
            }
        }
        if(offset == realInJa)
            return 0;

        if(low){
            return al.get(ia.get(line) + offset);
        } else {
            return au.get(ia.get(line) + offset);
        }
    }
}

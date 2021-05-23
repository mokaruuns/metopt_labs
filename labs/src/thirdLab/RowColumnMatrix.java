package thirdLab;

import java.util.ArrayList;
import java.util.List;

public class RowColumnMatrix implements Function {
    private final List<Double> b;
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
        List<Double> temp_b = new ArrayList<>();
        List<Double> temp_di = new ArrayList<>();
        List<Double> temp_al = new ArrayList<>();
        List<Double> temp_au= new ArrayList<>();
        List<Integer> temp_ja = new ArrayList<>();
        List<Integer> temp_ia = new ArrayList<>();
        for(int i = 0; i < n; i++)
        {
            Double rnd = rand(min, max);
            temp_b.add(rnd);
            rnd = rand(min, max);
            temp_di.add(rnd);
            rnd = rand(min, max);
            temp_al.add(rnd);
            rnd = rand(min, max);
            temp_au.add(rnd);
        }
        for(int i = 0; i < n * 2; i++){
            Integer rnd2 = randInteger(0.0, Double.valueOf(n - 1));
            temp_ja.add(rnd2);
            rnd2 = randInteger(0.0, Double.valueOf(n - 1));
            temp_ia.add(rnd2);
        }
        this.b = temp_b;
        this.di = temp_di;
        this.al = temp_al;
        this.au = temp_au;
        this.ja = temp_ja;
        this.ia = temp_ia;
    }

    public int size(){
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

    public void print() {
        System.out.println(b);
        System.out.println(di);
        System.out.println(al);
        System.out.println(au);
        System.out.println(ja);
        System.out.println(ia);
    }

    private List<Double> subtract(List<Double> vec1, List<Double> vec2){
        List<Double> res = new ArrayList<>();

        for(int i = 0; i < vec1.size(); i++){
            res.add(vec1.get(i) - vec2.get(i));
        }

        return res;
    }

    public List<Double> runGradient(List<Double> x) {
        return subtract(multi(x), b);
    }

    private Double dotProduct(List<Double> vec1, List<Double> vec2) {
        Double res = 0.0;
        for(int i = 0; i < vec1.size(); i++){
            res += vec1.get(i) * vec2.get(i);
        }

        return res;
    }
    public Double run(final List<Double> x) {
        List<Double> a = multi(x);
        Double quad = dotProduct(x, a) / 2;
        Double one = dotProduct(b, x);

        return quad - one;
    }

    public List<Double> multi(List<Double> x){
        List<Double> ans = new ArrayList<>();
        for(int i = 0; i < size(); i++)
            ans.add(0.0);
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++){
                ans.set(i, ans.get(i) + getIJ(i, j) * x.get(j));
            }
        }

        return ans;
    }
}

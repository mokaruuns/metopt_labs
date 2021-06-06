package thirdLab;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class RowColumnMatrix implements Function {
    private final List<Double> b;
    private final List<Double> di;
    private final List<Double> al;
    private final List<Double> au;
    private final List<Integer> ja;
    private final List<Integer> ia;

    public List<Double> multi(List<List<Double>> matrix, double[] vector){
        List<Double> result = new ArrayList<>();
        for(int i = 0; i < matrix.size(); i++) {
            result.add(0.0);
        }
        for(int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.size(); j++){
                result.set(i, result.get(i) + matrix.get(i).get(j) * vector[j]);
            }
        }

        return result;
    }

    public RowColumnMatrix(List<List<Double>> matrix) {
        int size = matrix.size();
        List<Double> temp_b = multi(matrix, DoubleStream.iterate(1.0, x -> x + 1.0).limit(size).toArray());
        List<Double> temp_di = new ArrayList<>();
        List<Double> temp_al = new ArrayList<>();
        List<Double> temp_au = new ArrayList<>();
        List<Integer> temp_ja = new ArrayList<>();
        List<Integer> temp_ia = new ArrayList<>();
        temp_ia.add(0);

//        for (int i = 0; i < size; i++){
//            System.out.println(matrix.get(i));
//        }
        for(int i = 0; i < size; i++){
            temp_di.add(matrix.get(i).get(i));
            temp_ia.add(0);
        }

        temp_ia.set(0, 0);
        temp_ia.set(1, 0);

        for(int i = 1; i < size; i++)
        {
            int ind = 0;
            while(ind < i && matrix.get(i).get(ind) == 0){
                ind++;
            }

            while(ind < i) {
                if(matrix.get(i).get(ind) != 0) {
                    temp_al.add(matrix.get(i).get(ind));
                    temp_au.add(matrix.get(ind).get(i));
                    temp_ja.add(ind);
                }
                ind++;
            }
            temp_ia.set(i + 1, temp_al.size());
        }

        this.di = temp_di;
        this.al = temp_al;
        this.au = temp_au;
        this.b = temp_b;
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
        int realInJa = ia.get(line + 1) - ia.get(line);
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
        double res = 0.0;
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
                Double temp = getIJ(i, j) * x.get(j);
                ans.set(i, ans.get(i) + temp);
            }
        }

        return ans;
    }
}

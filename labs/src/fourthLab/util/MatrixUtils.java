package fourthLab.util;

import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {
    public static Double norm(List<Double> v) {
        Double res = 0.0;
        for(Double aDouble : v){
            res += aDouble * aDouble;
        }

        return Math.sqrt(res);
    }

    public static List<List<Double>> generateI(int size) {
        List<List<Double>> matrix = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < size; j++){
                matrix.get(i).add(0.0);
            }
        }

        for (int i = 0; i < size; i++) {
            matrix.get(i).set(i, 1.0);
        }

        return matrix;
    }

    public static List<Double> mul(List<Double> v, Double x){
        List<Double> res = new ArrayList<>();
        for (Double aDouble : v) {
            res.add(aDouble * x);
        }

        return res;
    }

    public static List<List<Double>> mul(List<List<Double>> matrix, double x) {
        List<List<Double>> res = new ArrayList<>();
        for(int i = 0; i < matrix.size(); i++)
        {
            res.add(mul(matrix.get(i), x));
        }

        return res;
    }

    public static List<Double> mulMatrixOnVector(List<List<Double>> matrix, List<Double> vector) {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++){
            res.add(0.0);
        }

        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < matrix.get(i).size(); j++){
                res.set(i, res.get(i) + vector.get(j) * matrix.get(i).get(j));
            }
        }

        return res;
    }

    public static List<List<Double>> mul(List<List<Double>> matrix1, List<List<Double>> matrix2) {
        List<List<Double>> res = new ArrayList<>();
        for (int i = 0; i < matrix1.size(); i++){
            res.add(new ArrayList<>());
            for(int j = 0; j < matrix2.get(0).size(); j++){
                res.get(i).add(0.0);
            }
        }

        for (int i = 0; i < matrix1.size(); i++) {
            for (int j = 0; j < matrix2.get(0).size(); j++) {
                for (int k = 0; k < matrix2.size(); k++) {
                    res.get(i).set(j, res.get(i).get(j) + matrix1.get(i).get(k) * matrix2.get(k).get(j));
                }
            }
        }

        return res;
    }

    public static List<Double> sub(List<Double> vect1, List<Double> vect2){
        List<Double> res = new ArrayList<>();
        for(int i = 0; i < vect1.size(); i++) {
            res.add(vect1.get(i) - vect2.get(i));
        }

        return res;
    }

    public static List<List<Double>> subMatrix(List<List<Double>> matrix1, List<List<Double>> matrix2){
        List<List<Double>> res = new ArrayList<>();
        for(int i = 0; i < matrix1.size(); i++) {
            res.add(sub(matrix1.get(i), matrix2.get(i)));
        }

        return res;
    }

    public static List<Double> sum(List<Double> vect1, List<Double> vect2){
        List<Double> res = new ArrayList<>();
        for(int i = 0; i < vect1.size(); i++) {
            res.add(vect1.get(i) + vect2.get(i));
        }

        return res;
    }

    public static List<List<Double>> sumMatrix(List<List<Double>> matrix1, List<List<Double>> matrix2){
        List<List<Double>> res = new ArrayList<>();
        for(int i = 0; i < matrix1.size(); i++) {
            res.add(sum(matrix1.get(i), matrix2.get(i)));
        }

        return res;
    }
    public static Double scalar(List<Double> vect1, List<Double> vect2){
        Double res = 0.0;

        for(int i = 0; i < vect1.size(); i++) {
            res += vect1.get(i) * vect2.get(i);
        }

        return res;
    }

    public static List<List<Double>> wrapEach(List<Double> a) {
        List<List<Double>> res = new ArrayList<>();
        for(int i = 0; i < a.size(); i++){
            res.add(new ArrayList<>());
        }
        for(int i = 0; i < a.size(); i++){
            res.get(i).add(a.get(i));
        }

        return res;
    }

    public static List<List<Double>> wrap(List<Double> a) {
        List<List<Double>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for(int i = 0; i < a.size(); i++){
            res.get(0).add(a.get(i));
        }

        return res;
    }

    public static boolean equal(List<List<Double>> matrix1, List<List<Double>> matrix2) {
        if(matrix1.size() != matrix2.size() || matrix1.get(0).size() != matrix2.get(0).size()) {
            return false;
        }

        for(int i = 0; i < matrix1.size(); i++) {
            for (int j = 0; j < matrix1.get(i).size(); j++){
                if(matrix1.get(i).get(j) != matrix2.get(i).get(j)){
                    return false;
                }
            }
        }

        return true;
    }

    public static List<List<Double>> transpose(List<List<Double>> matrix) {
        List<List<Double>> trans = new ArrayList<>();

        for(int i = 0; i < matrix.get(0).size(); i++) {
            trans.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                trans.get(i).add(matrix.get(j).get(i));
            }
        }

        return trans;
    }
}

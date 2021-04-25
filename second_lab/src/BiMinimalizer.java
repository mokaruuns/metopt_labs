import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BiMinimalizer {

    protected int dimensions;
    final private double DELTA = 0.000001;

    protected List<List<Double>> a;
    protected List<Double> b;
    protected double c;

    void checkMatrix(List<List<Double>> function, int dimensions) {
        if (function.size() != dimensions || !function.stream().allMatch(list -> list.size() == dimensions)) {
            throw new IllegalArgumentException("Given matrix should be a square matrix with correct dimension");
        }
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (!function.get(i).get(j).equals(function.get(j).get(i))) {
                    throw new IllegalArgumentException("Given matrix should be symmetric, but " +
                            "a[" + i + "][" + j + "] and a[" + j + "][" + i + "] are not equal: " +
                            function.get(i).get(j) + "!=" + function.get(j).get(i));
                }
            }
        }
    }

    void checkDim(List<Double> list) {
        if (list.size() != dimensions) {
            throw new IllegalArgumentException("Argument list has to have correct dimension: " + dimensions + " instead of " + list.size());
        }
    }

    double apply(List<Double> list) {
        checkDim(list);
        double ans = c;
        for (int i = 0; i < dimensions; i++) {
            ans += list.get(i) * b.get(i);
            for (int j = 0; j < dimensions; j++) {
                ans += list.get(i) * list.get(j) * a.get(i).get(j) / 2;
            }
        }
        return ans;
    }

    protected List<Double> matrixVectorMul(List<List<Double>> matrix, List<Double> vector) {
        return matrix.stream().map(list -> mul(list, vector)).collect(Collectors.toList());
    }

    protected double aMod(List<Double> l) {
        return mul(matrixVectorMul(a, l), l);
    }

    protected double mod(List<Double> l) {
        return Math.sqrt(mul(l, l));
    }

    protected List<Double> mulOnNumber(List<Double> l, double r) {
        return l.stream().map(d -> r * d).collect(Collectors.toList());
    }

    protected double mul(List<Double> l, List<Double> r) {
        checkDim(l); checkDim(r);
        double ans = 0;
        for (int i = 0; i < dimensions; i++) {
            ans += l.get(i) * r.get(i);
        }
        return ans;
    }

    protected List<Double> sum(List<Double> l, List<Double> r) {
        checkDim(l); checkDim(r);
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < dimensions; i++) {
            ans.add(l.get(i) + r.get(i));
        }
        return ans;
    }

    List<Double> countGradient(List<Double> list) {
        return sum(matrixVectorMul(a, list), b);
    }

    private double partDiff(List<Double> list, int i) {
        double ans = b.get(i);
        for (int j = 0; j < dimensions; j++) {
            ans += a.get(i).get(j) * list.get(j);
        }
        return ans;
    }

    BiMinimalizer(List<List<Double>> a, List<Double> b, double c, int dimensions) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.dimensions = dimensions;
    }

    abstract List<Double> minimalize();

    public static void main(String[] args) {
        //4 * x * x + 3 * y * y + 4 * y * x + 5 * x + 6 * y + 9
        List<List<Double>> a = List.of(List.of(8.0, 4.0), List.of(4.0, 6.0));
        List<Double> b = List.of(5.0, 6.0);
        double c = 9;
        Function<List<Double>, Double> unimodal = f -> 2 * f.get(0) * f.get(0) + 3 * f.get(1) * f.get(1) + f.get(0) + 1;
        BiMinimalizer biMinimalizer = new FletcherReeves(a, b, c, 2);
        System.out.println(biMinimalizer.apply(List.of(1.0, 1.0)));
        System.out.println(biMinimalizer.countGradient(List.of(1.0, 1.0)));
        List<Double> x = biMinimalizer.minimalize();
        for (double x_i : x) {
            System.out.println(x_i);
        }
    }
}

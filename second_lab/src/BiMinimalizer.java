import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BiMinimalizer {

    protected int dimensions;
    final private double DELTA = 0.000001;

    protected Function<List<Double>, Double> function;
    protected List<Function<List<Double>, Double>> gradient;

    void checkDim(List<Double> list) {
        if (list.size() != dimensions) {
            throw new IllegalArgumentException("Argument list has to have correct dimension: " + dimensions + " instead of " + list.size());
        }
    }

    double apply(List<Double> list) {
        checkDim(list);
        return function.apply(list);
    }

    List<Double> countGradient(List<Double> list) {
        checkDim(list);
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < dimensions; i++) {
            ans.add(gradient.get(i).apply(list));
        }
        return ans;
    }

    BiMinimalizer(Function<List<Double>, Double> function, int dimensions) {
        this.function = function;
        this.dimensions = dimensions;
        gradient = new ArrayList<>();
        for (int i = 0; i < this.dimensions; i++) {
            int finalI = i;
            gradient.add(list -> {
                List<Double> diffed = new ArrayList<>(list);
                diffed.set(finalI, diffed.get(finalI) + DELTA);
                return (function.apply(diffed) - function.apply(list)) / DELTA;
            });
        }
    }

    abstract double minimalize();

}

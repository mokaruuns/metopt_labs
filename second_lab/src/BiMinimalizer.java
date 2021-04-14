import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BiMinimalizer {

    private final int dimensions;
    final private double DELTA = 0.000001;

    Function<List<Double>, Double> function;
    List<Function<List<Double>, Double>> gradient;

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

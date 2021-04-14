import java.util.List;
import java.util.function.Function;

public class GradientDescentBiMinimalizer extends BiMinimalizer {
    public GradientDescentBiMinimalizer(Function<List<Double>, Double> function, int dimensions) {
        super(function, dimensions);
    }

    @Override
    public double minimalize() {
        return gradientDescent();
    }

    private double gradientDescent() {
        return 0;
    }

}

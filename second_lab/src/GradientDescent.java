import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GradientDescent extends BiMinimalizer {
    public GradientDescent(Function<List<Double>, Double> function, int dimensions) {
        super(function, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return gradientDescent();
    }

    private List<Double> gradientDescent() {
        Double eps = 1e-7;
        List<Double> args1 = new ArrayList<Double>();
        List<Double> startPoint = new ArrayList<Double>();
        for (int i = 0; i < dimensions; i++) {
            startPoint.add(1.0);
            args1.add(1.0);
        }
        boolean stop = false;
        Double lambda = 0.01;
        int iter = 0;
        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            for (int i = 0; i < dimensions; i++) {
                args1.set(i, startPoint.get(i) - grad.get(i) * lambda);
            }
            Double dist = 0.0;
            for (int i = 0; i < args1.size(); i++) {
                dist += Math.pow(args1.get(i) - startPoint.get(i), 2);
            }
            if (dist < eps * eps && Math.abs(function.apply(startPoint) - function.apply(args1)) < eps) {
                stop = true;
            }

            startPoint = new ArrayList(args1);
            iter += 1;

        }
        return startPoint;
    }
}

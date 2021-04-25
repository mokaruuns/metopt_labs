import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.function.Function;

public class SteepestDescent extends BiMinimalizer {
    public SteepestDescent(List<List<Double>> A, List<Double> B, Double C, int dimensions) {
        super(A, B, C, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return steepestDescent();
    }

    private List<Double> steepestDescent() {
        Double eps = 1e-7;
        List<Double> nextPoint;
        List<Double> startPoint = Collections.nCopies(dimensions, 1.0);
        boolean stop = false;
        double lambda = 0.01;
        int iter = 0;
        Minimalizer minimalizer;
        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            List<Double> finalStartPoint = startPoint;
            Function<Double, Double> function = l -> apply(sum(finalStartPoint, mulOnNumber(grad, -l)));
            minimalizer = new GoldenRatioMinimalizer(function, 0, 1);
            lambda = minimalizer.minimalize(0.0001);
            nextPoint = sum(startPoint, mulOnNumber(grad, -lambda));
            double dist = dist(nextPoint, startPoint);
            if (dist < eps * eps && Math.abs(apply(startPoint) - apply(nextPoint)) < eps) {
                stop = true;
            }
            startPoint = new ArrayList<>(nextPoint);
            iter += 1;
        }
        System.out.println(iter);
        return startPoint;
    }
}

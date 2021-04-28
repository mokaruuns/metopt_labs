package secondLab;
import firstLab.GoldenRatioMinimalizer;
import firstLab.Minimalizer;

import java.util.Collections;
import java.util.List;

import java.util.function.Function;

public class SteepestDescent extends BiMinimalizer {
    SteepestDescent(List<List<Double>> A, List<Double> B, Double C, int dimensions) {
        super(A, B, C, dimensions);
    }

    SteepestDescent(Matrix a, NumberVector b, double c, int dimensions) {
        super(a, b, c, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return steepestDescent();
    }

    private List<Double> steepestDescent() {
        Double eps = 1e-7;
        NumberVector nextPoint;
        NumberVector startPoint = new NumberVector(Collections.nCopies(dimensions, 1.0));
        boolean stop = false;
        double lambda = 0.01;
        int iter = 0;
        Minimalizer minimalizer;
        while (!stop) {
            NumberVector grad = countGradient(startPoint);
            NumberVector normalize = grad.normalize();
            System.out.println(startPoint.get(0) + " " + startPoint.get(1));
            NumberVector finalStartPoint = startPoint;
            Function<Double, Double> function = l -> apply(finalStartPoint.addVector(normalize.mulOnNumber(-l)).getVector());
            minimalizer = new GoldenRatioMinimalizer(function, 0, 1);
            lambda = minimalizer.minimalize(0.000001);
            nextPoint = startPoint.addVector(normalize.mulOnNumber(-lambda));
            double dist = dist(nextPoint, startPoint);
            if (dist < eps * eps && Math.abs(apply(startPoint.getVector()) - apply(nextPoint.getVector())) < eps) {
                stop = true;
            }
            startPoint = nextPoint;

            iter += 1;
        }
        System.out.print(iter + " ");
        return startPoint.getVector();
    }
}

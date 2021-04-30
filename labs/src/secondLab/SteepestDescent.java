package secondLab;

import firstLab.*;

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
        double eps = 1e-4;
        NumberVector nextPoint;
        NumberVector startPoint = new NumberVector(Collections.nCopies(dimensions, 0.0));
        boolean stop = false;
        double lambda;
        int iter = 0;
        Minimalizer minimalizer;
        while (!stop) {
            NumberVector grad = countGradient(startPoint);
            double len = mod(grad);
            grad = grad.normalize();
            System.out.println(startPoint.get(0) + " " + startPoint.get(1));
            NumberVector finalStartPoint = startPoint;
            NumberVector finalGrad = grad;
            Function<Double, Double> function = l -> apply(finalStartPoint.addVector(finalGrad.mulOnNumber(-l)).getVector());
            minimalizer = new DichotomyMinimalizer(function, 0, 1);
            lambda = minimalizer.minimalize(0.0000001);
            nextPoint = startPoint.addVector(grad.mulOnNumber(-lambda));
            if (len < eps) {
                stop = true;
            }
            startPoint = nextPoint;

            iter += 1;
        }
        System.out.print(iter + " ");
        return startPoint.getVector();
    }
}
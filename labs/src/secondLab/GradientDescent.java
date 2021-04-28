package secondLab;

import java.util.Collections;
import java.util.List;


public class GradientDescent extends BiMinimalizer {
    GradientDescent(List<List<Double>> A, List<Double> B, Double C, int dimensions) {
        super(A, B, C, dimensions);
    }

    GradientDescent(Matrix a, NumberVector b, double c, int dimensions) {
        super(a, b, c, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return gradientDescent();
    }

    private List<Double> gradientDescent() {
        Double eps = 1e-5;
        NumberVector nextPoint;
        NumberVector startPoint = new NumberVector(Collections.nCopies(dimensions, 0.0));
        boolean stop = false;
        double lambda = 1;
        int iter = 0;
        while (!stop) {
            NumberVector grad = countGradient(startPoint);
            grad = grad.normalize();
            nextPoint = startPoint.addVector(grad.mulOnNumber(-lambda));
            double dist = dist(nextPoint, startPoint);
            if (dist < eps * eps && Math.abs(apply(startPoint.getVector()) - apply(nextPoint.getVector())) < eps) {
                stop = true;
            }
            if (apply(startPoint.getVector()) < apply(nextPoint.getVector())) {
                lambda /= 2;
            }
            //System.out.println(startPoint.get(0) + " " + startPoint.get(1));
            startPoint = nextPoint;
            iter += 1;
        }
        System.out.print(iter + " ");
        return startPoint.getVector();
    }
}
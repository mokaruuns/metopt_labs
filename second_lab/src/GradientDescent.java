import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GradientDescent extends BiMinimalizer {
    public GradientDescent(List<List<Double>> A, List<Double> B, Double C, int dimensions) {
        super(A, B, C, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return gradientDescent();
    }

    private List<Double> gradientDescent() {
        Double eps = 1e-7;
        List<Double> nextPoint;
        List<Double> startPoint = Collections.nCopies(dimensions, 0.0);
        boolean stop = false;
        double lambda = 0.01;
        int iter = 0;
        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            nextPoint = sum(startPoint, mulOnNumber(grad, -lambda));
            double dist = dist(nextPoint, startPoint);
            if (dist < eps * eps && Math.abs(apply(startPoint) - apply(nextPoint)) < eps) {
                stop = true;
            }
            if (apply(startPoint) < apply(nextPoint)) {
                lambda /= 2;
            }
            startPoint = new ArrayList<>(nextPoint);
            iter += 1;
        }
        return startPoint;
    }
}

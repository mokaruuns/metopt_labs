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
        ArrayList<Double> nextPoint = new ArrayList<>(Collections.nCopies(dimensions, 1.0));
        ArrayList<Double> startPoint = new ArrayList<>(Collections.nCopies(dimensions, 1.0));
        boolean stop = false;
        Double lambda = 0.01;
        int iter = 0;
        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            for (int i = 0; i < dimensions; i++) {
                nextPoint.set(i, startPoint.get(i) - grad.get(i) * lambda);
            }
            double dist = 0.0;
            for (int i = 0; i < nextPoint.size(); i++) {
                dist += Math.pow(nextPoint.get(i) - startPoint.get(i), 2);
            }
            if (dist < eps * eps && Math.abs(apply(startPoint) - apply(nextPoint)) < eps) {
                stop = true;
            }

            startPoint = new ArrayList<>(nextPoint);
            iter += 1;

        }
        return startPoint;
    }
}

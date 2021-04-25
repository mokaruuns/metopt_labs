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

//    private List<Double> goldenRatioMethod(double epsilon, List<Double> leftBorder, List<Double> rightBorder) {
//        int amountApplying = 1;
//        final double tau = (Math.sqrt(5) - 1) / 2;
//        List<Double> a = leftBorder;
//        List<Double> b = rightBorder;
//        List<Double> x1 = sum(a, mulOnNumber(sum(b, mulOnNumber(a, -1.0)), (1.0 - tau)));
//        List<Double> x2 = sum(a, mulOnNumber(sum(b, mulOnNumber(a, -1)), tau));
//        double fx1 = apply(x1);
//        double fx2 = apply(x2);
//        double currentEpsilon = dist(b, a);
//        while (currentEpsilon > epsilon * epsilon) {
//            amountApplying++;
//            if (fx1 < fx2) {
//                b = x2;
//                x2 = x1;
//                x1 = sum(a, mulOnNumber(sum(b, mulOnNumber(a, -1.0)), (1.0 - tau)));
//                fx2 = fx1;
//                fx1 = apply(x1);
//            } else {
//                a = x1;
//                x1 = x2;
//                fx1 = fx2;
//                x2 = sum(a, mulOnNumber(sum(b, mulOnNumber(a, -1)), tau));
//                fx2 = apply(x2);
//            }
//            currentEpsilon *= tau;
//        }
//        return mulOnNumber(sum(a, b), 0.5);
//    }

    private List<Double> steepestDescent() {
        Double eps = 1e-7;
        List<Double> nextPoint;
        List<Double> startPoint = Collections.nCopies(dimensions, 0.0);
        boolean stop = false;
        double lambda = 0.01;
        int iter = 0;
        Minimalizer minimalizer;
        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            // x* = apply(x - lambda*grad)
            List<Double> finalStartPoint = startPoint;
            Function<Double, Double> funct = lamb -> apply(sum(finalStartPoint, mulOnNumber(grad, -lamb)));
            minimalizer = new GoldenRatioMinimalizer(funct, 0, 1);
            lambda = minimalizer.minimalize(0.00001);
            nextPoint = sum(startPoint, mulOnNumber(grad, -lambda));
            double dist = dist(nextPoint, startPoint);
            if (dist < eps * eps && Math.abs(apply(startPoint) - apply(nextPoint)) < eps) {
                stop = true;
            }
            startPoint = new ArrayList<>(nextPoint);
            iter += 1;
        }
        return startPoint;
    }
}

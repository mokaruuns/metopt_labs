package firstLab;

import java.util.function.Function;

/**
 * Для переданной унимодальной функции находит точку минимума методом Золотого сечения
 */
public class GoldenRatioMinimalizer extends Minimalizer {
    static int iter = 0;

    public GoldenRatioMinimalizer(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double minimalize(double epsilon) {
        return goldenRatioMethod(epsilon);
    }

    private double goldenRatioMethod(double epsilon) {
        int amountApplying = 1;
        final double tau = (Math.sqrt(5) - 1) / 2;
        double a = leftBorder;
        double b = rightBorder;
        double x1 = a + (1.0 - tau) * (b - a);
        double x2 = a + tau * (b - a);
        double fx1 = function.apply(x1);
        double fx2 = function.apply(x2);
        double currentEpsilon = (b - a) / 2;
        double lastLenght = b - a;
        while (currentEpsilon > epsilon) {
            amountApplying++;
            lastLenght  = printBorders(a, b, lastLenght, x1, fx1, x2, fx2);
            if (fx1 < fx2) {
                b = x2;
                x2 = x1;
                x1 = a + (1.0 - tau) * (b - a);
                fx2 = fx1;
                fx1 = apply(x1);
            } else {
                a = x1;
                x1 = x2;
                fx1 = fx2;
                x2 = a + tau * (b - a);
                fx2 = apply(x2);
            }
            currentEpsilon *= tau;
            iter++;
        }
        printDependence(amountApplying, epsilon);
        return (a + b) / 2;
    }

    public int getIters(){
        return iter;
    }
}

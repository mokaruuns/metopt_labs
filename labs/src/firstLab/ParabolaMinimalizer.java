package firstLab;

import java.util.function.Function;

/**
 * Для переданной унимодальной функции находит точку минимума методом Парабол
 */
public class ParabolaMinimalizer extends Minimalizer {
    public ParabolaMinimalizer(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double minimalize(double epsilon) {
        return parabolaMethod(epsilon);
    }

    private double parabolaMethod(double epsilon) {
        int amountApplying = 2;
        // step 1
        double x1 = leftBorder, x3 = rightBorder;
        double f1 = function.apply(leftBorder), f3 = function.apply(rightBorder);
        DoublePair middlePoint = getPointByCondition(function, leftBorder, rightBorder, epsilon);
        if (middlePoint == null) {
            return Double.NaN;
        }
        double x2 = middlePoint.first, f2 = middlePoint.second;
        double x_min;
        double a1 = (f2 - f1) / (x2 - x1), a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);
        double currentApproximation = (x1 + x2 - a1 / a2) / 2;
        double lastApproximation = currentApproximation + 10 * epsilon;
        double lastLenght = x3 - x1;
        while (true) {
            // step 2
            a1 = (f2 - f1) / (x2 - x1);
            a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
            currentApproximation = (x1 + x2 - a1 / a2) / 2;
            double currentApproximationValue = apply(currentApproximation);
            amountApplying++;
            lastLenght = printBorders(x1, x3, lastLenght, currentApproximation, currentApproximationValue);
            // step 3
            if (Math.abs(currentApproximation - lastApproximation) <= epsilon) {
                x_min = currentApproximation;
                break;
            } else {
                // step 4
                if (x1 < currentApproximation && currentApproximation < x2 && currentApproximationValue >= f2) {
                    x1 = currentApproximation;
                    f1 = currentApproximationValue;
                } else if (x1 < currentApproximation && currentApproximation < x2 && currentApproximationValue < f2) {
                    x3 = x2;
                    f3 = f2;
                    x2 = currentApproximation;
                    f2 = currentApproximationValue;
                } else if (x2 < currentApproximation && currentApproximation < x3 && currentApproximationValue > f2) {
                    x3 = currentApproximation;
                    f3 = currentApproximationValue;
                } else {
                    x1 = x2;
                    f1 = f2;
                    x2 = currentApproximation;
                    f2 = currentApproximationValue;
                }
                lastApproximation = currentApproximation;
            }
        }
        printDependence(amountApplying, epsilon);
        return x_min;
    }

    private DoublePair getPointByCondition(Function<Double, Double> function, double leftBorder, double rightBorder, double epsilon) {
        double f1 = function.apply(leftBorder);
        double f3 = function.apply(rightBorder);
        double x2 = (leftBorder + rightBorder) / 2;
        double f2 = apply(x2);
        if (Math.abs(f2 - f1) < epsilon || Math.abs(f2 - f3) < epsilon) return new DoublePair(x2, f2);
        int maxIter = 20;
        for (double min = f1 < f3 ? leftBorder : rightBorder; Math.abs(x2 - min) >= epsilon && maxIter > 0; maxIter--, x2 = (x2 + min) / 2, f2 = apply(x2))
            if (f1 >= f2 && f2 <= f3)
                return new DoublePair(x2, f2);
        return null;
    }

    public class DoublePair {

        public double first;
        public double second;

        public DoublePair (double first, double second) {
            this.first = first;
            this.second = second;
        }

    }


}

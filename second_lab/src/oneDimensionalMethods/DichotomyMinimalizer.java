package oneDimensionalMethods;

import java.util.function.Function;

/**
 * Для переданной унимодальной функции находит точку минимума методом Дихотомии
 */
public class DichotomyMinimalizer extends Minimalizer {

    public DichotomyMinimalizer(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double minimalize(double epsilon) {
        return dichotomyMethod(epsilon);
    }

    private double dichotomyMethod(double epsilon) {
        double a = leftBorder, b = rightBorder;
        while (b - a > epsilon) {
            double delta = (b - a) / 100;
            double x1 = (a + b - delta) / 2;
            double x2 = (a + b + delta) / 2;
            double fx1 = function.apply(x1);
            double fx2 = function.apply(x2);
            if (fx1 > fx2) {
                a = x1;
            } else {
                b = x2;
            }
        }
        return (a + b) / 2;
    }

}

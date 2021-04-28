package firstLab;

import java.util.function.Function;

/**
 * Для переданной унимодальной функции находит точку минимума методом Фибоначчи
 */
public class FibonacciMinimalizer extends Minimalizer {

    public FibonacciMinimalizer(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double minimalize(double epsilon) {
        return fibonacciMethod(epsilon);
    }

    public double fibonacciMethod(double epsilon) {
        FibonacciResult res = new FibonacciResult(leftBorder, rightBorder, epsilon);
        double a = leftBorder;
        double b = rightBorder;
        double x1 = a + ((double)res.fn / res.fn2) * (b - a);
        double x2 = a + ((double)res.fn1 / res.fn2) * (b - a);
        double fx1 = apply(x1);
        double fx2 = apply(x2);
        int amountApplying = 2;
        double lastLenght = b - a;
        for (long i = res.n; i > 1; i--) {
            lastLenght = printBorders(a, b, lastLenght, x1, fx1, x2, fx2);
            amountApplying++;
            if (fx1 > fx2) {
                a = x1;
                x1 = x2;
                x2 = a + b - x1;
                fx1 = fx2;
                fx2 = apply(x2);
            } else {
                b = x2;
                x2 = x1;
                x1 = a + b - x2;
                fx2 = fx1;
                fx1 = apply(x1);
            }
        }
        printDependence(amountApplying, epsilon);
        return (x1 + x2) / 2;
    }



    private static class FibonacciResult {

        private final double SQRT5 = Math.sqrt(5);
        public long n, fn, fn1, fn2;

        private long fibonacci(long k) {
            double ret = Math.round((Math.pow((1 + SQRT5)/2, k) + Math.pow((1 - SQRT5)/2, k))/SQRT5);
            return (long) ret;
        }

        public FibonacciResult (double a, double b, double epsilon) {
            n = (long) Math.ceil(Math.log(SQRT5 * (b - a) / epsilon) / Math.log((1 + SQRT5) / 2))  - 2;
            fn = fibonacci(n);
            fn1 = fibonacci(n + 1);
            fn2 = fn + fn1;
            final double d = Math.abs(b - a) / epsilon;
            while(d >= fn2) {
                long t = fn1 + fn2;
                fn = fn1;
                fn1 = fn2;
                fn2 = t;
                n++;
            }
        }
    }
}

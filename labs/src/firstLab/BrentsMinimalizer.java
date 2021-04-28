package firstLab;

import java.util.function.Function;

/**
 * Для переданной унимодальной функции находит точку минимума методом Брента
 */
public class BrentsMinimalizer extends Minimalizer {

    public BrentsMinimalizer(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double minimalize(double epsilon) {
        return brentsMethod(epsilon);
    }

    private double brentsMethod(double epsilon) {
        int amountApplying = 1;
        //Step 1
        double a = leftBorder, b = rightBorder;
        double tau = (3 - Math.sqrt(5)) / 2;
        double x = a + (1 - tau) * (b - a);
        double w = x, v = x;
        double fx = function.apply(x);
        double fw = fx, fv = fx;
        double d = b - a;
        double e = d;
        double lastLenght = b - a;
        while (true) {
            //Step 2
            if (Math.abs(x - (a + b) / 2) + (b - a) / 2 <= 2 * epsilon) {
                break;
            }
            double g = e;
            e = d;
            double u;

            //Step 3
            double optinalu = 0;
            boolean has_optinalu = false;

            if (unique(x, w, v) && unique(fx, fw, fv)) {
                double a1 = (fw - fx) / (w - x);
                double a2 = ((fv - fx) / (v - x) - a1) / (v - w);
                double u_curr = (x + w - a1 / a2) / 2;

                if (u_curr >= a + epsilon && u_curr <= b - epsilon && Math.abs(u_curr - x) < g / 2) {
                    optinalu = u_curr;
                    has_optinalu = true;
                }
            }

            if (!has_optinalu) {
                if (x < (a + b) / 2) {
                    optinalu = x + (1 - tau) * (b - x);
                    e = b - x;
                } else {
                    optinalu = a + (1 - tau) * (x - a);
                    e = x - a;
                }
            }

            u = optinalu;
            d = Math.abs(u - x);
            double fu = apply(u);
            lastLenght = printBorders(a, b, lastLenght, u, fu);
            amountApplying++;
            //Step 5
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    b = x;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            }
            //Step 4
            else {
                if (u >= x) {
                    b = u;
                } else {
                    a = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
        }
//        printDependence(amountApplying, epsilon);
        return (a + b) / 2;
    }

    private boolean unique(double a, double b, double c) {
        return a != b && a != c && b != c;
    }
}

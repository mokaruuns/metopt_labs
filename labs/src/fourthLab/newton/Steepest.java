package fourthLab.newton;

import firstLab.DichotomyMinimalizer;
import firstLab.Minimalizer;
import secondLab.NumberVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Steepest extends AbstractNewtonMethod{

    /**
     * Creates abstract Newton method optimizer.
     * <p>
     * If gradient or hessian are not correct method's behaviour is undefined.
     *
     * @param function function to optimize
     * @param gradient function's gradient
     * @param hessian  function's hessian
     * @param n        function's dimension
     */
    public Steepest(Function<List<Double>, Double> function, List<Function<List<Double>, Double>> gradient, List<List<Function<List<Double>, Double>>> hessian, int n) {
        super(function, gradient, hessian, n);
    }

    @Override
    public List<Double> run(List<Double> x0, double eps) {
        lastIterations = new ArrayList<>();
        NumberVector nextPoint;
        NumberVector startPoint = new NumberVector(x0);
        boolean stop = false;
        double lambda;
        int iter = 0;
        Minimalizer minimalizer;
        while (!stop) {
            lastIterations.add(startPoint.getVector());
            NumberVector grad = new NumberVector(countGradient(startPoint.getVector()));
            double len = grad.norma();
            grad = grad.normalize();
            NumberVector finalStartPoint = startPoint;
            NumberVector finalGrad = grad;
            Function<Double, Double> function = l -> apply(finalStartPoint.addVector(finalGrad.mulOnNumber(-l)).getVector());
            minimalizer = new DichotomyMinimalizer(function, 0, 1);
            lambda = minimalizer.minimalize(0.0000001);
            nextPoint = startPoint.addVector(grad.mulOnNumber(-lambda));
            if (grad.mulOnNumber(-lambda).norma() < eps) {
                stop = true;
            }
            startPoint = nextPoint;

            iter += 1;
        }
        lastIterations.add(startPoint.getVector());
        System.out.print(iter + " ");
        return startPoint.getVector();
    }
}

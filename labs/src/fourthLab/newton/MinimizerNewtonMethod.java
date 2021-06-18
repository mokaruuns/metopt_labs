package fourthLab.newton;

import firstLab.BrentsMinimalizer;
import firstLab.GoldenRatioMinimalizer;
import secondLab.NumberVector;
import thirdLab.ProfileMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MinimizerNewtonMethod extends AbstractNewtonMethod {

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
    public MinimizerNewtonMethod(Function<List<Double>, Double> function, List<Function<List<Double>, Double>> gradient, List<List<Function<List<Double>, Double>>> hessian, int n) {
        super(function, gradient, hessian, n);
    }

    @Override
    public List<Double> run(List<Double> x0, double eps) {
        addInfo = new ArrayList<>();
        lastIterations = new ArrayList<>();
        return minimizerNewtonMethod(x0, eps);
    }

    private List<Double> minimizerNewtonMethod(List<Double> x, double eps) {
        NumberVector s;
        do {
            lastIterations.add(x);
            NumberVector gr = new NumberVector(countGradient(x));
            List<List<Double>> hs = countHessian(x);
            NumberVector d = new NumberVector(new ProfileMatrix(hs, this.n, true).swapSolve(gr.mulOnNumber(-1).getVector()));
            NumberVector finalX = new NumberVector(x);
            NumberVector finalD = d;
            GoldenRatioMinimalizer minimalizer = new GoldenRatioMinimalizer(t -> apply(finalX.addVector(finalD.mulOnNumber(t)).getVector()), -1, 1);
            double r = minimalizer.minimalize(1e-7);
            addInfo.add(r);
            minIters += minimalizer.getIters();
            s = d.mulOnNumber(r);
            x = s.addVector(new NumberVector(x)).getVector();
        } while (s.norma() > eps);
        lastIterations.add(x);
        return x;
    }

    public static void main(String[] args) {
        Function<List<Double>, Double> f = l -> Math.sin(l.get(0)) + Math.cos(l.get(1));
        List<Function<List<Double>, Double>> g = List.of(list -> Math.cos(list.get(0)), list -> -Math.sin(list.get(1)));
        List<List<Function<List<Double>, Double>>> h = List.of(List.of(l -> -Math.sin(l.get(0)), l -> 0.0),
                List.of(l -> 0.0, l -> -Math.cos(l.get(1))));
        System.out.println(new MinimizerNewtonMethod(f, g, h, 2).run(List.of(0.0, 0.0), 0.00001));
    }

}

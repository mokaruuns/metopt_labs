package fourthLab.newton;

import firstLab.GoldenRatioMinimalizer;
import secondLab.NumberVector;
import thirdLab.ProfileMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DirectedNewtonMethod extends AbstractNewtonMethod {

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
    public DirectedNewtonMethod(Function<List<Double>, Double> function, List<Function<List<Double>, Double>> gradient, List<List<Function<List<Double>, Double>>> hessian, int n) {
        super(function, gradient, hessian, n);
    }

    @Override
    public List<Double> run(List<Double> x0, double eps) {
        lastIterations = new ArrayList<>();
        addInfo = new ArrayList<>();
        return directedNewtonMethod(x0, eps);
    }

    private List<Double> directedNewtonMethod(List<Double> x, double eps) {
        NumberVector s;
        lastIterations.add(x);
        NumberVector d = new NumberVector(countGradient(x)).mulOnNumber(-1);
        NumberVector finalX1 = new NumberVector(x);
        NumberVector finalD = d;
        GoldenRatioMinimalizer minimalizer =  new GoldenRatioMinimalizer(t -> apply(finalX1.addVector(finalD.mulOnNumber(t)).getVector()), -1, 1);
        double r = minimalizer.minimalize(1e-7);
        addInfo.add(r);
        minIters += minimalizer.getIters();
        s = d.mulOnNumber(r);
        x = s.addVector(new NumberVector(x)).getVector();
        do {
            lastIterations.add(x);
            NumberVector gr = new NumberVector(countGradient(x));
            List<List<Double>> hs = countHessian(x);
            s = new NumberVector(new ProfileMatrix(hs, this.n, true).swapSolve(gr.mulOnNumber(-1).getVector()));
            if (s.mulOnVector(gr) < 0) {
                d = s;
            } else {
                d = gr.mulOnNumber(-1);
            }
            NumberVector finalX2 = new NumberVector(x);
            NumberVector finalD1 = d;
            minimalizer =  new GoldenRatioMinimalizer(t -> apply(finalX2.addVector(finalD1.mulOnNumber(t)).getVector()), -1, 1);
            r = minimalizer.minimalize(1e-7);
            minIters += minimalizer.getIters();
            addInfo.add(r);
            s = d.mulOnNumber(r);
            x = s.addVector(new NumberVector(x)).getVector();
        } while (s.norma() > eps);
        lastIterations.add(x);
        return x;
    }

    public static void main(String[] args) {
        Function<List<Double>, Double> f = l -> Math.pow(l.get(0), 4) + 9 * Math.pow(l.get(1), 4);
        List<Function<List<Double>, Double>> g = List.of(list -> 4 * Math.pow(list.get(0), 3), list -> 36 * Math.pow(list.get(1), 3));
        List<List<Function<List<Double>, Double>>> h = List.of(List.of(l -> 12 * Math.pow(l.get(0), 2), l -> 0.0),
                List.of(l -> 0.0, l -> 108 * Math.pow(l.get(1), 2)));
        DirectedNewtonMethod newtonMethod = new DirectedNewtonMethod(f, g, h, 2);
        System.out.println(newtonMethod.run(List.of(50.0, 79.0), 0.000001));
        for (List<Double> l : newtonMethod.lastIterations) {
            System.out.printf("%e \t%e\n", l.get(0), l.get(1));
        }
    }
}

package fourthLab.newton;

import secondLab.NumberVector;
import thirdLab.ProfileMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.lang.System;


public class ClassicNewtonMethod extends AbstractNewtonMethod {


    /**
     * Creates classic Newton method optimizer.
     * <p>
     * If gradient or hessian are not correct method's behaviour is undefined.
     *
     * @param function function to optimize
     * @param gradient function's gradient
     * @param hessian  function's hessian
     * @param n        function's dimension
     */
    public ClassicNewtonMethod(Function<List<Double>, Double> function, List<Function<List<Double>, Double>> gradient, List<List<Function<List<Double>, Double>>> hessian, int n) {
        super(function, gradient, hessian, n);
    }

    @Override
    public List<Double> run(List<Double> x0, double eps) {
        minIters = 0;
        lastIterations = new ArrayList<>();
        return classicNewtonMethod(x0, eps);
    }

    private List<Double> classicNewtonMethod(List<Double> x, double eps) {
        NumberVector s;
        boolean flag = false;
        do {
            lastIterations.add(x);
            NumberVector gr = new NumberVector(countGradient(x));
            List<List<Double>> hs = countHessian(x);
            if (!flag) {
                System.out.println(hs);
                System.out.println(gr.getVector());
            }
            s = new NumberVector(new ProfileMatrix(hs, this.n, true).swapSolve(gr.mulOnNumber(-1).getVector()));
            if (!flag) {
                System.out.println(s.getVector());
            }
            flag = true;
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
        ClassicNewtonMethod newtonMethod = new ClassicNewtonMethod(f, g, h, 2);
        System.out.println(newtonMethod.run(List.of(2.0, 1.0), 0.00001));
        for (List<Double> l : newtonMethod.lastIterations) {
            System.out.printf("%f \t%f\n", l.get(0), l.get(1));
        }
    }

}

package fourthLab.newton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractNewtonMethod {

    public List<Double> addInfo = new ArrayList<>();
    final protected Function<List<Double>, Double> function;
    final protected List<Function<List<Double>, Double>> gradient;
    final protected List<List<Function<List<Double>, Double>>> hessian;
    public List<List<Double>> lastIterations;
    protected final int n;

    public int getMinIters() {
        return minIters;
    }

    protected int minIters;

    /**
     * Creates abstract Newton method optimizer.
     *
     * If gradient or hessian are not correct method's behaviour is undefined.
     * @param function function to optimize
     * @param gradient function's gradient
     * @param hessian function's hessian
     * @param n function's dimension
     */
    protected AbstractNewtonMethod(Function<List<Double>, Double> function, List<Function<List<Double>, Double>> gradient, List<List<Function<List<Double>, Double>>> hessian, int n) {
        this.n = n;
        Objects.requireNonNull(function);
        Objects.requireNonNull(gradient);
        sizeCheck(gradient, "Given gradient must have " + n + " functions");
        for (Function<List<Double>, Double> fn : gradient) {
            Objects.requireNonNull(fn);
        }
        sizeCheck(hessian, "Given hessian must have " + n + " lists of functions");
        for (List<Function<List<Double>, Double>> fnList : hessian) {
            Objects.requireNonNull(fnList);
            sizeCheck(fnList, "Hessian should be a square matrix");
            for (Function<List<Double>, Double> fn : fnList) {
                Objects.requireNonNull(fn);
            }
        }
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
    }

    private <E> void sizeCheck(List<E> list, String message) {
        if (list.size() != n) {
            throw new IllegalArgumentException(message);
        }
    }

    private List<Double> countFnList(List<Function<List<Double>, Double>> fnList, List<Double> x) {
        return fnList.stream().map(fn -> fn.apply(x)).collect(Collectors.toList());
    }


    public Double apply(List<Double> x) {
        sizeCheck(x, "Given point should have " + n + " parameters");
        return function.apply(x);
    }

    public List<Double> countGradient(List<Double> x) {
        sizeCheck(x, "Given point should have " + n + " parameters");
        return countFnList(gradient, x);
    }

    public List<List<Double>> countHessian(List<Double> x) {
        sizeCheck(x, "Given point should have " + n + " parameters");
        return hessian.stream().map(l -> countFnList(l, x)).collect(Collectors.toList());
    }

    /**
     * Tries to find function's minimum by given initial approximation.
     *
     * @param x0 initial approximation
     * @return function's minimum
     */
    public abstract List<Double> run(List<Double> x0, double eps);
}

package fourthLab.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DoubleMultiFunctionImpl implements DoubleMultiFunction {

    private final Function<List<Double>, Double> function;
    private final List<Function<List<Double>, Double>> gradient;
    private final List<List<Function<List<Double>, Double>>> hessian;

    public DoubleMultiFunctionImpl(Function<List<Double>, Double> function,
                                   List<Function<List<Double>, Double>> gradient,
                                   List<List<Function<List<Double>, Double>>> hessian) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
    }

    private List<Double> countFnList(List<Function<List<Double>, Double>> fnList, List<Double> x) {
        return fnList.stream().map(fn -> fn.apply(x)).collect(Collectors.toList());
    }

    @Override
    public Double apply(List<Double> args) {
        return function.apply(args);
    }

    @Override
    public List<Double> gradient(List<Double> args) {
        return countFnList(gradient, args);
    }

    @Override
    public List<List<Double>> hessian(List<Double> args) {
        return hessian.stream().map(l -> countFnList(l, args)).collect(Collectors.toList());
    }
}

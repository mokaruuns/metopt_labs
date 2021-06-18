package fourthLab.util;

import java.util.List;

public interface DoubleMultiFunction {
    Double apply(List<Double> args);

    List<Double> gradient(List<Double> args);

    List<List<Double>> hessian(List<Double> args);

}

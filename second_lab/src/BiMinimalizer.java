import java.util.function.BiFunction;
import java.util.function.ToDoubleBiFunction;

public abstract class BiMinimalizer {

    final double delta = 0.000001;

    BiFunction<Double, Double, Double> function;
    BiFunction<Double, Double, Double> dFunctionDY;
    BiFunction<Double, Double, Double> dFunctionDX;

    BiMinimalizer(BiFunction<Double, Double, Double> function) {
        this.function = function;
        dFunctionDX = (xDouble, yDouble) -> (function.apply(xDouble + delta, yDouble) - function.apply(xDouble, yDouble)) / delta;
        dFunctionDY = (xDouble, yDouble) -> (function.apply(xDouble, yDouble + delta) - function.apply(xDouble, yDouble)) / delta;
    }

}

package fourthLab.util;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class FunctionUtils {
    private static final double GRADIENT_STEP = 0.00001;

    public static List<Double> diffGradient(DoubleMultiFunction function, List<Double> v){
        List<Double> plusGradient = new ArrayList<>(v);
        List<Double> minusGradient = new ArrayList<>(v);
        List<Double> gradient = new ArrayList<>();

        for(int i = 0; i < v.size(); i++){
            plusGradient.set(i, plusGradient.get(i) + GRADIENT_STEP);
            minusGradient.set(i, minusGradient.get(i) - GRADIENT_STEP);
            gradient.add((function.apply(plusGradient) - function.apply(minusGradient)) / (2 * GRADIENT_STEP));
            plusGradient.set(i, plusGradient.get(i) - GRADIENT_STEP);
            minusGradient.set(i, minusGradient.get(i) + GRADIENT_STEP);
        }

        return gradient;
    }

    public static List<List<Double>> diffHessian(DoubleMultiFunction function, List<Double> x) {
        List<List<Double>> hessian = new ArrayList<>();
        for(int i = 0; i < x.size(); i++) {
            hessian.add(new ArrayList<>());
            for(int j = 0; j < x.size(); j++){
                x.set(i, x.get(i) + GRADIENT_STEP);
                x.set(j, x.get(j) + GRADIENT_STEP);
                Double f0 = function.apply(x);
                x.set(j, x.get(j) - 2 * GRADIENT_STEP);
                Double f1 = function.apply(x);
                x.set(i, x.get(i) - 2 * GRADIENT_STEP);
                Double f2 = function.apply(x);
                x.set(j, x.get(j) + 2 * GRADIENT_STEP);
                Double f3 = function.apply(x);
                x.set(i, x.get(i) + GRADIENT_STEP);
                x.set(j, x.get(j) - GRADIENT_STEP);
                hessian.get(i).add((f0 - f1 + f2 - f3) / (4 * GRADIENT_STEP * GRADIENT_STEP));
            }
        }

        return hessian;
    }
}

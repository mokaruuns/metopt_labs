package fourthLab.util;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.util.ArrayList;
import java.util.List;

public class FunctionUtils {
    private static final double GRADIENT_STEP = 0.00001;

    public static List<Double> gradient(DoubleMultiFunction function, List<Double> v){
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
}

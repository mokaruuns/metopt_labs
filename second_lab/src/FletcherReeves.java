import java.util.Collections;
import java.util.List;

public class FletcherReeves extends BiMinimalizer {

    FletcherReeves(List<List<Double>> a, List<Double> b, double c, int dimensions) {
        super(a, b, c, dimensions);
    }

    @Override
    List<Double> minimalize() {
        return fletcherReevesMethod();
    }


    private List<Double> fletcherReevesMethod() {
        List<Double> xk = Collections.nCopies(dimensions, 1.0);
        List<Double> gradK = countGradient(xk);
        List<Double> pk = mulOnNumber(gradK, -1);
        double alfak = 0;
        double betak = 0;
        for (int i = 0; i < dimensions; i++) {
            List<Double> apk = matrixVectorMul(a, pk);
            alfak = mul(gradK, gradK) / mul(apk, pk);
            xk = sum(xk, mulOnNumber(pk, alfak));
            List<Double> gradK1 = sum(gradK, mulOnNumber(apk, alfak));
            betak = mul(gradK1, gradK1) / mul(gradK, gradK);
            pk = sum(mulOnNumber(gradK1, -1), mulOnNumber(pk, betak));
            gradK = gradK1;
        }
        System.out.println(dimensions);
        return xk;
    }
}

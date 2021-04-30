package secondLab;

import java.util.Collections;
import java.util.List;

public class FletcherReeves extends BiMinimalizer {

    FletcherReeves(List<List<Double>> a, List<Double> b, double c, int dimensions) {
        super(a, b, c, dimensions);
    }

    FletcherReeves(Matrix a, NumberVector b, double c, int dimensions) {
        super(a, b, c, dimensions);
    }

    @Override
    List<Double> minimalize() {
        return fletcherReevesMethod();
    }


    private List<Double> fletcherReevesMethod() {
        double eps = 1e-7;
        NumberVector xk = new NumberVector(Collections.nCopies(dimensions, 0.0));
        NumberVector gradK = countGradient(xk);
        NumberVector pk = gradK.mulOnNumber(-1);
        double alfak = 0;
        double betak = 0;
        int iter = 0;
        for (int i = 0; i < dimensions && mod(gradK) > eps; i++) {
            NumberVector apk = a.mulOnVector(pk);
            alfak = gradK.mulOnVector(gradK) / apk.mulOnVector(pk);
            System.out.println((xk.get(0) + " " + xk.get(1)));
            xk = xk.addVector(pk.mulOnNumber(alfak));
            NumberVector gradK1 = gradK.addVector(apk.mulOnNumber(alfak));//sum(gradK, mulOnNumber(apk, alfak));
            betak = gradK1.mulOnVector(gradK1) / gradK.mulOnVector(gradK);
            pk = gradK1.mulOnNumber(-1).addVector(pk.mulOnNumber(betak));//sum(mulOnNumber(gradK1, -1), mulOnNumber(pk, betak));
            gradK = gradK1;
            iter++;
        }
        System.out.println((xk.get(0) + " " + xk.get(1)));
        System.out.print(iter + " ");
        return xk.getVector();
    }
}

package fourthLab.marquardt;

import fourthLab.util.*;

import java.util.ArrayList;
import java.util.List;

public class ModifiedMarquardtMethod extends MarquardtMethod {
    List<Integer> choleskyApproximations = new ArrayList<>();

    public ModifiedMarquardtMethod(DoubleMultiFunction function, List<Double> x) {
        super(function, x, 0.0);
    }

    @Override
    protected void step() {
        List<Double> gradient = function.gradient(x);
        List<List<Double>> h = function.hessian(x);
        Double newTau = tau * BETA;
        Double value = function.apply(x);
        do {
            newTau /= tau;
            p = new LES(MatrixUtils.sumMatrix(h, MatrixUtils.mul(MatrixUtils.generateI(n), newTau)),
                    MatrixUtils.mul(gradient, -1.0)).solve();
            y = MatrixUtils.sum(x, p);
        } while (function.apply(y) > value);
        updateX();

        List<List<Double>> right = MatrixUtils.sumMatrix(h, MatrixUtils.mul(MatrixUtils.generateI(n), tau));
        List<List<Double>> left = new Cholesky(right).decompose();

        int numCholesky = 0;
        while (!MatrixUtils.equal(right, MatrixUtils.mul(left, MatrixUtils.transpose(left))) && numCholesky < 10) {
            tau = Math.max(1, 2 * tau);
            right = MatrixUtils.sumMatrix(h, MatrixUtils.mul(MatrixUtils.generateI(n), tau));
            left = new Cholesky(right).decompose();
            numCholesky++;
        }

        tau *= BETA;
        allTau.add(tau);
        choleskyApproximations.add(numCholesky);
    }
}

package fourthLab.marquardt;

import fourthLab.util.*;

import java.util.ArrayList;
import java.util.List;

public class MarquardtMethod extends MultiOptimizationMethod {
    static final Double BETA = 1e-4;
    Double tau;
    List<Double> p = new ArrayList<>();
    List<Double> y = new ArrayList<>();
    List<Double> allTau = new ArrayList<>();

    public MarquardtMethod(DoubleMultiFunction function, List<Double> x, Double tau) {
        super(function, x);
        this.tau = tau;
        allTau.add(tau);
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
        } while(function.apply(y) > value);
        updateX();
        tau *= BETA;
        allTau.add(tau);
    }

    @Override
    protected void firstStep() {

    }

    @Override
    protected boolean done() {
        return MatrixUtils.norm(p) < EPS;
    }

    @Override
    protected void updateXInner() {
        x = y;
    }
}

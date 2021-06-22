package fourthLab.quasinewton;

import fourthLab.util.DoubleMultiFunction;
import fourthLab.util.MatrixUtils;
import fourthLab.util.MultiOptimizationMethod;

import java.util.List;

public abstract class AbstractQuasiNewton extends MultiOptimizationMethod {
    List<Double> deltaX;
    List<Double> deltaW;
    List<Double> p;
    List<Double> w;
    List<List<Double>> gMatrix;

    public AbstractQuasiNewton(DoubleMultiFunction function, List<Double> x) {
        super(function, x);
        this.gMatrix = MatrixUtils.generateI(x.size());
    }

    @Override
    public boolean done(){
        return MatrixUtils.norm(deltaX) < EPS;
    }
}

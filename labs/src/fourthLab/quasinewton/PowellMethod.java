package fourthLab.quasinewton;

import firstLab.GoldenRatioMinimalizer;
import fourthLab.util.DoubleMultiFunction;
import fourthLab.util.FunctionUtils;
import fourthLab.util.MatrixUtils;

import java.util.ArrayList;
import java.util.List;

public class PowellMethod extends DavidonFletcherPowellMethod{
    Double alpha;
    List<Double> deltaXdot = new ArrayList<>();
    List<Double> allAlpha = new ArrayList<>();

    public PowellMethod(DoubleMultiFunction function, List<Double> x) {
        super(function, x);
    }

    void updateAlpha() {
        var method = new GoldenRatioMinimalizer(func -> function.apply(MatrixUtils.sum(x, MatrixUtils.mul(p, func))), -10.0, 10.0);
        alpha = method.minimalize(1e-7);
        allAlpha.add(alpha);
    }

    @Override
    protected void firstStep() {
        w = MatrixUtils.mul(function.gradient(x), -1.0);
        p = w;
        deltaW = w;
        updateAlpha();
        updateX();
    }

    @Override
    protected void step() {
        List<Double> prevW = new ArrayList<>(w);
        w = MatrixUtils.mul(function.gradient(x), -1.0);
        deltaW = MatrixUtils.sub(w, prevW);
        List<Double> v = MatrixUtils.mulMatrixOnVector(gMatrix, deltaW);
        updateAlpha();
        updateX();
        updateGradMatrix(v);
        p = MatrixUtils.mulMatrixOnVector(gMatrix, w);
    }

    @Override
    protected void updateXInner() {
        List<Double> prevX = new ArrayList<>(x);
        x = MatrixUtils.sum(x, MatrixUtils.mul(p, alpha));
        deltaX = MatrixUtils.sub(x, prevX);
        deltaXdot = MatrixUtils.sum(deltaX, MatrixUtils.mulMatrixOnVector(gMatrix, deltaW));
    }
}

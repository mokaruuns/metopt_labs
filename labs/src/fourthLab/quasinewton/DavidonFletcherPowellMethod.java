package fourthLab.quasinewton;

import firstLab.GoldenRatioMinimalizer;
import fourthLab.util.DoubleMultiFunction;
import fourthLab.util.FunctionUtils;
import fourthLab.util.MatrixUtils;

import java.util.ArrayList;
import java.util.List;

public class DavidonFletcherPowellMethod extends AbstractQuasiNewton {
    Double alpha;
    List<Double> allAlpha = new ArrayList<>();

    public DavidonFletcherPowellMethod(DoubleMultiFunction function, List<Double> x) {
        super(function, x);
    }

    void updateAlpha() {
        var method = new GoldenRatioMinimalizer(func -> function.apply(MatrixUtils.sum(x, MatrixUtils.mul(p, func))), -10.0, 10.0);
        alpha = method.minimalize(1e-7);
        allAlpha.add(alpha);
    }

    void updateGradMatrix(List<Double> v){
        List<List<Double>> first = MatrixUtils.mul(MatrixUtils.mul(
                    MatrixUtils.wrapEach(deltaX), MatrixUtils.wrap(deltaX)),
                1 / MatrixUtils.scalar(deltaW, deltaX));
        List<List<Double>> second = MatrixUtils.mul(MatrixUtils.mul(
                MatrixUtils.wrapEach(v), MatrixUtils.wrap(v)),
                1 / MatrixUtils.scalar(v, deltaW));

        gMatrix = MatrixUtils.subMatrix(MatrixUtils.subMatrix(gMatrix, first), second);
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
        List<Double> prevW = w;
        w = MatrixUtils.mul(function.gradient(x), -1.0);
        deltaW = MatrixUtils.sub(w, prevW);
        List<Double> v = MatrixUtils.mulMatrixOnVector(gMatrix, deltaW);
        updateGradMatrix(v);
        p = MatrixUtils.mulMatrixOnVector(gMatrix, w);
        updateAlpha();
        updateX();
    }

    @Override
    protected void updateXInner() {
        List<Double> prevX = x;
        x = MatrixUtils.sum(x, MatrixUtils.mul(p, alpha));
        deltaX = MatrixUtils.sub(x, prevX);
    }
}

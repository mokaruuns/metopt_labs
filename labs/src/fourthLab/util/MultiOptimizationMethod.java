package fourthLab.util;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiOptimizationMethod {
    public static final double EPS = 0.000001;
    protected final DoubleMultiFunction function;
    protected List<Double> x;
    protected final int n;
    protected int iteration = 0;
    protected List<List<Double>> allX = new ArrayList<>();

    protected abstract void step();
    protected abstract void firstStep();
    protected abstract boolean done();
    protected abstract void updateXInner();

    protected void updateX() {
        updateXInner();
        allX.add(x);
    }

    public List<Double> optimize() {
        firstStep();
        do {
            step();
            iteration++;
        } while (!done());
        return x;
    }

    public MultiOptimizationMethod(DoubleMultiFunction function, List<Double> x) {
        this.function = function;
        this.x = x;
        this.n = x.size();
    }

    public int getIterations() {
        return iteration;
    }

    public List<List<Double>> points() {
        return allX;
    }

}

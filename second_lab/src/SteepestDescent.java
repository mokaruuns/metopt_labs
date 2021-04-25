import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SteepestDescent extends BiMinimalizer {
    public SteepestDescent(List<List<Double>> A, List<Double> B, Double C, int dimensions) {
        super(A, B, C, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return steepestDescent();
    }

    private List<Double> GoldenRatio(double eps, List<Double> a, List<Double> b) {
        double t1 = 0.381966, t2 = 1 - t1;
        List<Double> x1 = new ArrayList<>();
        x1.add(a.get(0) + (b.get(0) - a.get(0)) * t1);
        x1.add(a.get(1) + (b.get(1) - a.get(1)) * t1);
        List<Double> x2 = new ArrayList<>();
        x2.add(a.get(0) + (b.get(0) - a.get(0)) * t2);
        x2.add(a.get(1) + (b.get(1) - a.get(1)) * t2);

        List<Double> temp1 = new ArrayList<>();
        temp1.add(x1.get(0) - eps);
        temp1.add(x1.get(1) - eps);
        List<Double> temp2 = new ArrayList<>();
        temp2.add(x2.get(0) + eps);
        temp2.add(x2.get(1) + eps);
        double f1 = apply(temp1);
        double f2 = apply(temp2);

        while (Math.sqrt((b.get(0) - a.get(0)) * (b.get(0) - a.get(0)) + (b.get(1) - a.get(1)) * (b.get(1) - a.get(1))) > eps) {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                double temp_x1 = a.get(0) + (b.get(0) - a.get(0)) * t1;
                x1.remove(0);
                x1.add(temp_x1);
                temp_x1 = a.get(1) + (b.get(1) - a.get(1)) * t1;
                x1.remove(1);
                x1.add(temp_x1);
                List<Double> temp = List.copyOf(x1);
                f1 = apply(temp);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                double temp_x2 = a.get(0) + (b.get(0) - a.get(0)) * t2;
                x2.remove(0);
                x2.add(temp_x2);
                temp_x2 = a.get(1) + (b.get(1) - a.get(1)) * t2;
                x2.remove(1);
                x2.add(temp_x2);
                List<Double> temp = List.copyOf(x2);
                f2 = apply(temp);
            }
        }
        List<Double> ans = new ArrayList<>();
        ans.add((a.get(0) + b.get(0)) / 2);
        ans.add((a.get(1) + b.get(1)) / 2);
        return ans;
    }

    private List<Double> steepestDescent() {
        double eps = 1e-7;
        boolean stop = false;
        int iter = 0;
        ArrayList<Double> args1 = new ArrayList<>(Collections.nCopies(dimensions, 1.0));
        ArrayList<Double> startPoint = new ArrayList<>(Collections.nCopies(dimensions, 1.0));

        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            List<Double> minusGrad = new ArrayList<>(List.copyOf(grad));
            for (int i = 0; i < minusGrad.size(); i++) {
                minusGrad.set(i, minusGrad.get(i) * (-1));
            }
            List<Double> point = GoldenRatio(eps, args1, minusGrad);
            List<Double> args2 = List.copyOf(point);
            double dist = 0.0;
            for (int i = 0; i < args1.size(); i++) {
                dist += Math.pow((args2.get(i) - args1.get(i)), 2);
            }

            if (dist < eps * eps && Math.abs(apply(args1) - apply(args2)) < eps) {
                stop = true;
            }

            startPoint = new ArrayList<>(args1);
            iter += 1;
        }

        return startPoint;
    }

}

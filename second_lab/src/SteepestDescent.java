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
        List<Double> x2 = new ArrayList<>();
        for(int i = 0; i < dimensions; i++){
            x1.add(a.get(i) + (b.get(i) - a.get(i)) * t1);
            x2.add(a.get(i) + (b.get(i) - a.get(i)) * t2);
        }

        List<Double> temp1 = new ArrayList<>();
        List<Double> temp2 = new ArrayList<>();
        for(int i = 0; i < dimensions; i++){
            temp1.add(x1.get(i) - eps);
            temp2.add(x2.get(i) + eps);
        }
        double f1 = apply(temp1);
        double f2 = apply(temp2);
        double res = 0;
        for(int i = 0; i < dimensions; i++)
        {
            res += (b.get(i) - a.get(i)) * (b.get(i) - a.get(i));
        }
        while (Math.sqrt(res) > eps) {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                for (int i = 0; i < a.size(); i++){
                    x1.set(i, a.get(i) + (b.get(i) - a.get(i)) * t1);
                }
                f1 = apply(x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                for (int i = 0; i < a.size(); i++){
                    x2.set(i, a.get(i) + (b.get(i) - a.get(i)) * t2);
                }
                f2 = apply(x2);
            }
            res = 0;
            for(int i = 0; i < dimensions; i++)
            {
                res += (b.get(i) - a.get(i)) * (b.get(i) - a.get(i));
            }
        }
        List<Double> ans = new ArrayList<>();
        for(int i = 0; i < dimensions; i++)
            ans.add((a.get(i) + b.get(i)) / 2);
        return ans;
    }

    private List<Double> steepestDescent() {
        double eps = 1e-7;
        boolean stop = false;
        int iter = 0;
        ArrayList<Double> args1 = new ArrayList<>(Collections.nCopies(dimensions, 1.0));

        while (!stop) {
            List<Double> grad = countGradient(args1);
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

            args1 = new ArrayList<>(args2);
            iter += 1;
        }

        return args1;
    }

}
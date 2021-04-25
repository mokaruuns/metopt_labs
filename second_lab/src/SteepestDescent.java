import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SteepestDescent extends BiMinimalizer {
    public SteepestDescent(Function<List<Double>, Double> function, int dimensions) {
        super(function, dimensions);
    }

    @Override
    public List<Double> minimalize() {
        return steepestDescent();
    }

    private List<Double> GoldenRatio(double eps, List<Double> a, List<Double> b) {
        double t1 = 0.381966, t2 = 1 - t1;

        List<Double> x1 = new ArrayList<Double>();
        x1.add(a.get(0) + (b.get(0) - a.get(0)) * t1);
        x1.add(a.get(1) + (b.get(1) - a.get(1))  * t1);
        List<Double> x2 = new ArrayList<Double>();
        x2.add(a.get(0) + (b.get(0) - a.get(0)) * t2);
        x2.add(a.get(1) + (b.get(1) - a.get(1))  * t2);

        List<Double> temp1 = new ArrayList<Double>();
        temp1.add(x1.get(0) - eps);
        temp1.add(x1.get(1) - eps);
        List<Double> temp2 = new ArrayList<Double>();
        temp2.add(x2.get(0) + eps);
        temp2.add(x2.get(1) + eps);
        double f1 = function.apply(temp1);
        double f2 = function.apply(temp2);

        while (Math.sqrt((b.get(0) - a.get(0)) * (b.get(0) - a.get(0)) + (b.get(1) - a.get(1)) * (b.get(1) - a.get(1))) > eps) {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                Double temp_x1 = a.get(0) + (b.get(0) - a.get(0)) * t1;
                x1.remove(0);
                x1.add(temp_x1);
                temp_x1 = a.get(1) + (b.get(1) - a.get(1)) * t1;
                x1.remove(1);
                x1.add(temp_x1);
                List<Double> temp = List.copyOf(x1);
                f1 = function.apply(temp);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                Double temp_x2 = a.get(0) + (b.get(0) - a.get(0)) * t2;
                x2.remove(0);
                x2.add(temp_x2);
                temp_x2 = a.get(1) + (b.get(1) - a.get(1)) * t2;
                x2.remove(1);
                x2.add(temp_x2);
                List<Double> temp = List.copyOf(x2);
                f2 = function.apply(temp);
            }
        }
        List<Double> ans = new ArrayList<Double>();
        ans.add((a.get(0) + b.get(0)) / 2);
        ans.add((a.get(1) + b.get(1)) / 2);
        return ans;
    }

    private List<Double> steepestDescent() {
        Double eps = 1e-7;
        boolean stop = false;
        int iter = 0;
        List<Double> args1 = new ArrayList<Double>();
        List<Double> startPoint = new ArrayList<Double>();
        for (int i = 0; i < dimensions; i++) {
            startPoint.add(1.0);
            args1.add(1.0);
        }

        while (!stop) {
            List<Double> grad = countGradient(startPoint);
            List<Double> minusGrad = List.copyOf(grad);
            for (int i = 0; i < minusGrad.size(); i++) {
                Double temp = minusGrad.get(i);
                minusGrad.remove(i);
                temp = -temp;
                minusGrad.add(temp);
            }
            List<Double>  point = GoldenRatio(eps, args1, minusGrad);
            List<Double> args2 = List.copyOf(point);
            Double dist = 0.0;
            for (int i = 0; i < args1.size(); i++) {
                dist += Math.pow((args2.get(i) - args1.get(i)), 2);
            }

            if (dist < eps * eps && Math.abs(function.apply(args1) - function.apply(args2)) < eps) {
                stop = true;
            }

            startPoint = new ArrayList(args1);
            iter += 1;
        }

        return startPoint;
    }

}

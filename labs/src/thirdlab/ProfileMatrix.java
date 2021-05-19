package thirdlab;

import secondLab.Matrix;
import secondLab.NumberVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileMatrix implements Matrix {

    public boolean isLU = false;
    private final List<List<Double>> au;
    private final List<List<Double>> al;
    private final List<Double> di;


    public ProfileMatrix(List<List<Double>> m, int n) {
        au = new ArrayList<>();
        al = new ArrayList<>();
        di = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            al.add(new ArrayList<>());
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                double elem = m.get(i).get(j);
                if (flag || m.get(i).get(j) != 0) {
                    flag = true;
                    al.get(i).add(elem);
                }
            }
        }
        for (int j = 0; j < n; j++) {
            au.add(new ArrayList<>());
            boolean flag = false;
            for (int i = 0; i < j; i++) {
                double elem = m.get(i).get(j);
                if (flag || m.get(i).get(j) != 0) {
                    flag = true;
                    au.get(j).add(elem);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            di.add(m.get(i).get(i));
        }
        int a;
        a = 0;
    }

    @Override
    public NumberVector mulOnVector(NumberVector numberVector) {
        return null;
    }

    @Override
    public Matrix mulOnNumber(double number) {
        return null;
    }

    @Override
    public double get(int i, int j) {
        if (i < j) {
            return getFromU(i, j);
        }
        return getFromL(i, j);
    }

    @Override
    public double apply(NumberVector vector) {
        return 0;
    }

    public double getFromU(int i, int j) {
        if (i == j) {
            return 1;
        }
        if (i > j) {
            return 0;
        }
        if (j - i > au.get(j).size()) {
            return 0;
        }
        return au.get(j).get(i - (j - au.get(j).size()));
    }

    public double getFromL(int i, int j) {
        if (i == j) {
            return di.get(i);
        }
        if (i < j) {
            return 0;
        }
        if (i - j > al.get(i).size()) {
            return 0;
        }
        return al.get(i).get(j - (i - al.get(i).size()));
    }

    private void set(int i, int j, double d) {
        if (i == j) {
            di.set(i, d);
            return;
        }
        if (i < j) {
            if (j - i > au.get(j).size()) {
                return;
            }
            au.get(j).set(i - (j - au.get(j).size()), d);
            return;
        }
        if (i - j > al.get(i).size()) {
            return;
        }
        al.get(i).set(j - (i - al.get(i).size()), d);
    }

    private List<List<Double>> mul() {
        List<List<Double>> l = new ArrayList<>();
        for (int i = 0; i < au.size(); i++) {
            l.add(new ArrayList<>());
            for (int j = 0; j < au.size(); j++) {
                l.get(i).add(0.0);
            }
        }
        for (int i = 0; i < au.size(); i++) {
            for (int j = 0; j < au.size(); j++) {
                for (int k = 0; k < au.size(); k++) {
                    l.get(i).set(j, l.get(i).get(j) + getFromL(i, k) * getFromU(k, j));
                }
            }
        }
        return l;
    }

    public void turnTOLU() {
        isLU = true;
        int n = al.size();
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                double a = get(i, j);
                for (int k = 0; k < j; k++) {
                    a -= getFromL(i, k) * getFromU(k, j);
                }
                set(i, j, a);
            }
            for (int j = 0; j < i; j++) {
                double a = get(j, i);
                for (int k = 0; k < j; k++) {
                    a -= getFromL(j, k) * getFromU(k, i);
                }
                set(j, i, a / get(j, j));
            }
            double a = get(i, i);
            for (int k = 0; k < i; k++) {
                a -= getFromL(i, k) * getFromU(k, i);
            }
            set(i, i, a);
        }
    }

    public List<Double> solveSLAU(List<Double> b) {
        if (!isLU) {
            turnTOLU();
        }
        int n = au.size();
        List<Double> bnew = new ArrayList<>(b);
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                double temp = getFromL(i, k) / getFromL(k, k);
                bnew.set(i, bnew.get(i) - temp * bnew.get(k));
            }
        }
        List<Double> y = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            y.add(bnew.get(i) / di.get(i));
        }
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ans.add(0.0);
        }
        ans.set(n - 1, y.get(n - 1)/getFromU(n - 1, n - 1));
        for (int k = n - 2; k >= 0; k--) {
            double sum = 0;
            for (int j = k + 1; j < n; j++) {
                sum += getFromU(k, j) * ans.get(j);
            }
            ans.set(k, (y.get(k) - sum) / getFromU(k, k));
        }
        return ans;
    }

    public static void main(String[] args) {
        ProfileMatrix a = new ProfileMatrix(
                List.of(List.of(1.0, 2.0, 3.0),
                        List.of(4.0, 7.0, 6.0),
                        List.of(7.0, 8.0, 9.0)
                ), 3);

        a.turnTOLU();
        System.out.println(a.mul());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(a.get(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println(a.solveSLAU(List.of(1.0, 1.0, 1.0)));
    }

}

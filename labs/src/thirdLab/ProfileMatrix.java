package thirdLab;

import secondLab.Matrix;
import secondLab.NumberVector;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ProfileMatrix implements Matrix {

    public boolean isLU = false;
    private boolean broken = false;
    private final List<List<Double>> au;
    private final List<List<Double>> al;
    private final List<Double> di;
    private final List<Integer> startsForL;
    private final List<Integer> startsForU;
    private final List<Integer> permutation;
    private int width;
    private boolean isSwaped = false;
    private final int n;
    private boolean isFull;

    public ProfileMatrix(List<List<Double>> m, int n, boolean isFull) {
        this.isFull = isFull;
        this.n = n;
        permutation = new ArrayList<>();
        au = new ArrayList<>();
        al = new ArrayList<>();
        di = new ArrayList<>();
        startsForL = new ArrayList<>();
        startsForU = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            permutation.add(i);
            startsForU.add(i);
            startsForL.add(i);
        }
        for (int i = 0; i < n; i++) {
            al.add(new ArrayList<>());
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                double elem = m.get(i).get(j);
                if (flag || m.get(i).get(j) != 0 || isFull) {
                    if (!flag) {
                        startsForL.set(i, j);
                    }
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
                    if (!flag) {
                        startsForU.set(j, i);
                    }
                    flag = true;
                    au.get(j).add(elem);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            di.add(m.get(i).get(i));
        }
        width = -1;
        for (int i = 0; i < n; i++) {
            width = max(au.get(i).size(), width);
            width = max(al.get(i).size(), width);
        }
    }

    public ProfileMatrix(List<List<Double>> l, List<List<Double>> u, List<Double> di, int d, int width) {
        isFull = width == (d - 1);
        this.width = width;
        n = d;
        au = new ArrayList<>();
        al = new ArrayList<>();
        startsForU = new ArrayList<>();
        startsForL = new ArrayList<>();
        permutation = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            au.add(new ArrayList<>(u.get(i)));
            al.add(new ArrayList<>(l.get(i)));
            startsForU.add(i - u.get(i).size());
            startsForL.add(i - l.get(i).size());
            permutation.add(i);
        }
        this.di = new ArrayList<>(di);
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
        i = permutation.get(i);
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
        i = permutation.get(i);
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
            for (int j = startsForL.get(i); j < i; j++) {
                double a = get(i, j);
                for (int k = Math.max(startsForL.get(j), startsForU.get(i)); k < j; k++) {
                    a -= getFromL(i, k) * getFromU(k, j);
                }
                set(i, j, a);
            }
            for (int j = startsForU.get(i); j < i; j++) {
                double a = get(j, i);
                for (int k = Math.max(startsForL.get(j), startsForU.get(i)); k < j; k++) {
                    a -= getFromL(j, k) * getFromU(k, i);
                }
                set(j, i, a / get(j, j));
            }
            double a = get(i, i);
            for (int k = Math.max(startsForL.get(i), startsForU.get(i)); k < i; k++) {
                a -= getFromL(i, k) * getFromU(k, i);
            }
            set(i, i, a);
        }
    }

    private void swap(List<Double> b, int k) {
        if (isSwaped) {
            return;
        }
        double max = 0;
        int m = 0;
        for (int i = k; i < n; i++) {
            double ik = Math.abs(get(i, k));
            if (max < ik) {
                max = ik;
                m = i;
            }
        }
        int h = permutation.get(k);
        permutation.set(k, permutation.get(m));
        permutation.set(m, h);
//        double g = b.get(k);
//        b.set(k, b.get(m));
//        b.set(m, g);
//        for (int j = k; j < m; j++) {
//            double akj = get(k, j);
//            double amj = get(m, j);
//            set(m, j, akj);
//            set(k, j, amj);
//        }
    }

    private List<Double> haussFirst(List<Double> b) {
        return haussFirst(b, false);
    }

    private List<Double> haussFirst(List<Double> b, boolean swaps) {
        List<Double> bnew;
        bnew = new ArrayList<>(b);
        int n = au.size();
        for (int k = 0; k < n - 1; k++) {
            if (swaps) {
                swap(bnew, k);
            }
//            printWhole();
//            System.out.println(permutation);
            for (int i = k + 1; i < min(k + width + 1, n); i++) {
                double temp = get(i, k) / get(k, k);
                bnew.set(permutation.get(i), bnew.get(permutation.get(i)) - temp * bnew.get(permutation.get(k)));
                if (!isLU) {
                    for (int j = 0; j < n; j++) {
                        set(i, j, get(i, j) - temp * get(k, j));
                    }
                }
            }
        }
//        printWhole();
//        System.out.println(permutation);
//        System.out.println(bnew);
        return bnew;
    }

    private void printWhole() {
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }

    private void printL() {
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(getFromL(i, j) + " ");
            }
            System.out.println();
        }
    }

    private void printU() {
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(getFromU(i, j) + " ");
            }
            System.out.println();
        }
    }

    private List<Double> haussSecond(List<Double> b) {
        List<Double> bnew;
        bnew = new ArrayList<>(b);
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ans.add(0.0);
        }
        ans.set(n - 1, bnew.get(permutation.get(n - 1)) / (!isLU ? get(n - 1, n - 1) : 1));
        for (int k = n - 1; k > 0; k--) {
            double sum = 0;
            for (int j = 0; j < k; j++) {
                bnew.set(permutation.get(j), bnew.get(permutation.get(j)) - ans.get(k) * get(j, k));
            }
            ans.set(k - 1, bnew.get(permutation.get(k - 1)) / (!isLU ? get(k - 1, k - 1) : 1));
        }
        return ans;
    }

    private void brokeCheck() {
        if (broken) {
            throw new IllegalStateException("Solver is broken.");
        }
    }

    private void LUCheck() {
        if (isLU) {
            throw new IllegalStateException("Swap solve is unable for LU matrix.");
        }
    }

    public List<Double> haussSolve(List<Double> b) {
        brokeCheck();
        LUCheck();
        broken = true;
        return haussSecond(haussFirst(b));
    }

    public List<Double> swapSolve(List<Double> b) {
        fullCheck();
        LUCheck();
        brokeCheck();
        broken = true;
        return haussSecond(haussFirst(b, true));
    }

    private void fullCheck() {
        if (!isFull) {
            throw new IllegalStateException("Unable for not full matrix");
        }
    }

    public List<Double> LUSolve(List<Double> b) {
        brokeCheck();
        if (!isLU) {
            turnTOLU();
        }
        List<Double> bnew = haussFirst(b);
        List<Double> y = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            y.add(bnew.get(i) / di.get(i));
        }
        return haussSecond(y);
    }


//    public List<Double> solveSLAU(List<Double> b, boolean swaps) {
//        List<Double> bnew;
//        bnew = new ArrayList<>(b);
//        int n = au.size();
//        for (int k = 0; k < n - 1; k++) {
//            if (swaps) {
//                swap(bnew, k - 1);
//            }
//            for (int i = k + 1; i < min(k + width, n); i++) {
//                double temp = getFromL(i, k) / getFromL(k, k);
//                bnew.set(i, bnew.get(i) - temp * bnew.get(k));
//                if (!isLU) {
//                    for (int j = max(k - width, 0); j < min(k + width, n); j++) {
//                        set(i, j, get(i, j) - temp * get(k, j));
//                    }
//                }
//            }
//        }
//        List<Double> y = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            y.add(bnew.get(i) / di.get(i));
//        }
//        List<Double> ans = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            ans.add(0.0);
//        }
//        ans.set(n - 1, y.get(n - 1) / getFromU(n - 1, n - 1));
//        for (int k = n - 1; k > 0; k--) {
//            double sum = 0;
//            for (int j = startsForU.get(k); j < k; j++) {
//                y.set(j, y.get(j) - ans.get(k) * getFromU(j, k));
//            }
//            ans.set(k - 1, y.get(k - 1));
//        }
//        isSwaped |= swaps;
//        return ans;
//    }

    private List<Double> permutate(List<Double> b) {
        List<Double> bnew = new ArrayList<>();
        for (int i : permutation) {
            bnew.add(b.get(i));
        }
        return bnew;
    }

    public static void main(String[] args) {
       // new SlauGenerator().generate(5, 4, "here.txt", 10);
        Scanner scanner = null;
        try {
            scanner = new Scanner(Paths.get("here.txt").toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int dim = scanner.nextInt();
        int width = scanner.nextInt();
        List<List<Double>> au = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            au.add(new ArrayList<>());
            for (int j = 0; j < min(i, width); j++) {
                au.get(i).add(Double.parseDouble(scanner.next()));
            }
        }
        List<List<Double>> al = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            al.add(new ArrayList<>());
            for (int j = 0; j < min(i, width); j++) {
                al.get(i).add(Double.parseDouble(scanner.next()));
            }
        }
        List<Double> di = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            di.add(Double.parseDouble(scanner.next()));
        }
        ProfileMatrix profileMatrix = new ProfileMatrix(al, au, di, dim, width);
        profileMatrix.printWhole();
        List<Double> ans0 = profileMatrix.haussSolve(List.of(1.0, 2.0, 3.0, 4.0, 5.0));
        profileMatrix.printL();
        profileMatrix.printU();
        System.out.println(ans0);
    }
}

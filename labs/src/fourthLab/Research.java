package fourthLab;


import fourthLab.newton.AbstractNewtonMethod;
import fourthLab.newton.ClassicNewtonMethod;
import fourthLab.newton.DirectedNewtonMethod;
import fourthLab.newton.MinimizerNewtonMethod;
import fourthLab.quasinewton.DavidonFletcherPowellMethod;
import secondLab.UsualMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.lang.Math.*;

public class Research {

    final List<List<Double>> testSquare;
    final Function<List<Double>, Double> firstFn =
            (l -> {
                double x = l.get(0);
                double y = l.get(1);
                return 10 * x * pow(y, 2) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
            });
    final List<Function<List<Double>, Double>> firstGr = List.of(
            l -> {
                double x = l.get(0);
                double y = l.get(1);
                return -5 * exp(-(pow(x, 2) + pow(y, 2)) / 4) * (-2 + x * x) * y * y;
            },
            l -> {
                double x = l.get(0);
                double y = l.get(1);
                return -5 * exp(-(pow(x, 2) + pow(y, 2)) / 4) * x * y * (-4 + y * y);
            });

    //(5/2 x^3 y^2 e^(1/4 (-x^2 - y^2)) - 15 x y^2 e^(1/4 (-x^2 - y^2)) | 20 y e^(1/4 (-x^2 - y^2)) - 10 x^2 y e^(1/4 (-x^2 - y^2)) - 5 y^3 e^(1/4 (-x^2 - y^2)) + 5/2 x^2 y^3 e^(1/4 (-x^2 - y^2))
       //     20 y e^(1/4 (-x^2 - y^2)) - 10 x^2 y e^(1/4 (-x^2 - y^2)) - 5 y^3 e^(1/4 (-x^2 - y^2)) + 5/2 x^2 y^3 e^(1/4 (-x^2 - y^2)) | -25 x y^2 e^(1/4 (-x^2 - y^2)) + 20 x e^(1/4 (-x^2 - y^2)) + 5/2 x y^4 e^(1/4 (-x^2 - y^2)))

    final List<List<Function<List<Double>, Double>>> firstHs = List.of(
            List.of(
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5/2 * x * (x * x - 6) * y * y * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    },
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5/2 * (x * x - 2) * (y - 2) * y * (y + 2) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    }
            ),
            List.of(
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5/2 * (x * x - 2) * (y - 2) * y * (y + 2) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    },
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5/2 * x * (pow(y, 4) - 10 * y * y + 8) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    }
            )
    );
    final Function<List<Double>, Double> secondFn;
    final List<Function<List<Double>, Double>> secondGr;
    final List<List<Function<List<Double>, Double>>> secondHs;

    public Research() {
        testSquare = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testSquare.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    testSquare.get(i).add((i + 1) * 10.0);
                } else {
                    testSquare.get(i).add(0.0);
                }
            }
        }
        UsualMatrix testMatrix = new UsualMatrix(testSquare);
        secondFn = (l -> {
            double ans = 0;
            for (int i = 0; i < 10; i++) {
                ans += l.get(i) * l.get(i) * testMatrix.get(i, i);
            }
            return ans;
        });
        secondGr = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            secondGr.add(
                    l -> l.get(finalI) * 2 * testMatrix.get(finalI, finalI)
            );
        }
        secondHs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            secondHs.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                int finalI = i;
                int finalJ = j;
                secondHs.get(i).add(l -> testMatrix.get(finalI, finalJ) * 2);
            }
        }
    }

    private void runSnd() {
        List<Double> start = DoubleStream.iterate(1.0, i -> i < 151, i -> i += 15).boxed().collect(Collectors.toList());
        System.out.println(new ClassicNewtonMethod(secondFn, secondGr, secondHs, 10).run(start, 0.00001));
    }

    private void runFst(List<Double> list, double eps) {
        AbstractNewtonMethod newtonMethod = new DirectedNewtonMethod(firstFn, firstGr, firstHs, 2);
        List<Double> list1 = newtonMethod.run(list, eps);
        System.out.println(list1);
        System.out.println(newtonMethod.countGradient(list1));
        System.out.println(newtonMethod.apply(list1));
    }

    private void printList(List<Double> list) {
        for (double d : list) {
            System.out.printf(Locale.US, "%.15f \t", d);
        }
        System.out.println();
    }

    private void runMethod(AbstractNewtonMethod newtonMethod, List<Double> list, double eps, boolean forRes) {
        List<Double> min = newtonMethod.run(list, eps);
        if (!forRes) {
            System.out.println("-Found minimum");
            printList(min);
            System.out.println("-Gradient at this point");
            List<Double> grad = newtonMethod.countGradient(min);
            printList(grad);
            System.out.println("-Function at this point");
            System.out.println(newtonMethod.apply(min));
            System.out.println("-Iterations");
            for (List<Double> it : newtonMethod.lastIterations) {
                printList(it);
            }
        } else {
            for (List<Double> it : newtonMethod.lastIterations) {
                printList(it);
            }
            System.out.println(0);
        }
    }

    private void runFstClassic(List<Double> list, double eps, boolean forRes) {
        if (!forRes) {
            System.out.println("---Classic Newton Method---");
            runMethod(new ClassicNewtonMethod(firstFn, firstGr, firstHs, 2), list, eps, forRes);
            System.out.println("---Minimize Newton Method---");
            runMethod(new MinimizerNewtonMethod(firstFn, firstGr, firstHs, 2), list, eps, forRes);
            System.out.println("---Directed Newton Method---");
            runMethod(new DirectedNewtonMethod(firstFn, firstGr, firstHs, 2), list, eps, forRes);
        } else {
            runMethod(new ClassicNewtonMethod(firstFn, firstGr, firstHs, 2), list, eps, forRes);
            runMethod(new MinimizerNewtonMethod(firstFn, firstGr, firstHs, 2), list, eps, forRes);
            runMethod(new DirectedNewtonMethod(firstFn, firstGr, firstHs, 2), list, eps, forRes);
        }

    }


    //-2.0, -1.0
    //-3.0, 0.5
    //-1.5, 2.3
    //-1.0, -2.6

    public static void main(String[] args) {
        new Research().runFstClassic(List.of(0.5, 2.1), 0.0000001, true);
    }

}

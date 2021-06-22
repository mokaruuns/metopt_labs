package fourthLab;


import fourthLab.newton.*;
import fourthLab.quasinewton.AbstractQuasiNewton;
import fourthLab.quasinewton.DavidonFletcherPowellMethod;
import fourthLab.quasinewton.PowellMethod;
import fourthLab.util.DoubleMultiFunctionImpl;
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
    static final Function<List<Double>, Double> firstFn =
            (l -> {
                double x = l.get(0);
                double y = l.get(1);
                return 10 * x * pow(y, 2) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
            });
    static final List<Function<List<Double>, Double>> firstGr = List.of(
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
    static final List<List<Function<List<Double>, Double>>> firstHs = List.of(
            List.of(
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5 / 2 * x * (x * x - 6) * y * y * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    },
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5 / 2 * (x * x - 2) * (y - 2) * y * (y + 2) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    }
            ),
            List.of(
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5 / 2 * (x * x - 2) * (y - 2) * y * (y + 2) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    },
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return 5 / 2 * x * (pow(y, 4) - 10 * y * y + 8) * exp(-(pow(x, 2) + pow(y, 2)) / 4);
                    }
            )
    );

    final Function<List<Double>, Double> secondFn;
    final List<Function<List<Double>, Double>> secondGr;
    final List<List<Function<List<Double>, Double>>> secondHs;

    final static Function<List<Double>, Double> thirdFn = l -> {
        double x = l.get(0);
        double y = l.get(1);
        return x * x + y * y - 1.2 * x * y;
    };
    final static List<Function<List<Double>, Double>> thirdGr = List.of(
            l -> {
                double x = l.get(0);
                double y = l.get(1);
                return 2 * x - 1.2 * y;
            },
            l -> {
                double x = l.get(0);
                double y = l.get(1);
                return 2 * y - 1.2 * x;
            }
    );
    final static List<List<Function<List<Double>, Double>>> thirdHs = List.of(
            List.of(
                    l -> 2.0,
                    l -> -1.2
            ),
            List.of(
                    l -> -1.2,
                    l -> 2.0
            )
    );

    final static Function<List<Double>, Double> fourthFn = l -> {
        double x = l.get(0);
        double y = l.get(1);
        return 100 * pow(y - pow(x, 2), 2) + pow(1 - x, 2);
    };
    final static List<Function<List<Double>, Double>> fourthGr = List.of(
            l -> {
                double x = l.get(0);
                double y = l.get(1);
                return 2 * (-1 + x + 200 * pow(x, 3) - 200 * x * y);
            },
            l -> {
                double x = l.get(0);
                double y = l.get(1);
                return 200 * (-pow(x, 2) + y);
            }
    );
    final static List<List<Function<List<Double>, Double>>> fourthHs = List.of(
            List.of(
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return -400 * (y - pow(x, 2)) + 800 * pow(x, 2) + 2;
                    },
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return -400 * x;
                    }
            ),
            List.of(
                    l -> {
                        double x = l.get(0);
                        double y = l.get(1);
                        return -400 * x;
                    },
                    l -> 200.0
            )
    );


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
            System.out.println("-Minimizer results");
            printList(newtonMethod.addInfo);
            System.out.println("-Minimizer iterations");
            System.out.println(newtonMethod.getMinIters());

        } else {
            for (List<Double> it : newtonMethod.lastIterations) {
                printList(it);
            }
            printList(newtonMethod.addInfo);
            System.out.println(0);
        }
    }

    private void runQuasiMethod(AbstractQuasiNewton newtonMethod, List<Double> list, double eps, boolean forRes) {
        List<Double> min = newtonMethod.optimize();
        if (!forRes) {
            System.out.println("-Found minimum");
            printList(min);
            System.out.println("-Iterations");
            for (List<Double> it : newtonMethod.points()) {
                printList(it);
            }

        } else {
            for (List<Double> it : newtonMethod.points()) {
                printList(it);
            }
            System.out.println(0);
        }
    }

    private void runByFun(Function<List<Double>, Double> function, 
                          List<Function<List<Double>, Double>> gradient, 
                          List<List<Function<List<Double>, Double>>> hessian,
                          List<Double> list, double eps, boolean forRes) {
        int n = gradient.size();
        if (!forRes) {
            System.out.println("---Classic Newton Method---");
            runMethod(new ClassicNewtonMethod(function, gradient, hessian, n), list, eps, forRes);
            System.out.println("---Minimize Newton Method---");
            runMethod(new MinimizerNewtonMethod(function, gradient, hessian, n), list, eps, forRes);
            System.out.println("---Directed Newton Method---");
            runMethod(new DirectedNewtonMethod(function, gradient, hessian, n), list, eps, forRes);
            System.out.println("---Steepest Method---");
            runMethod(new Steepest(function, gradient, hessian, n), list, eps, forRes);
        } else {
            runMethod(new ClassicNewtonMethod(function, gradient, hessian, n), list, eps, forRes);
            runMethod(new MinimizerNewtonMethod(function, gradient, hessian, n), list, eps, forRes);
            runMethod(new DirectedNewtonMethod(function, gradient, hessian, n), list, eps, forRes);
            runMethod(new Steepest(function, gradient, hessian, n), list, eps, forRes);
        }

    }

    private void runQuasi(Function<List<Double>, Double> function,
                          List<Function<List<Double>, Double>> gradient,
                          List<Double> list, double eps, boolean forRes) {
        runQuasiMethod(new DavidonFletcherPowellMethod(new DoubleMultiFunctionImpl(function, gradient, null) , list), list, eps, forRes);
        runQuasiMethod(new PowellMethod(new DoubleMultiFunctionImpl(function, gradient, null), list), list, eps, forRes);
    }

    private void runFst(List<Double> list, double eps, boolean forRes) {
        runByFun(secondFn, secondGr, secondHs, list, eps, forRes);
    }

    //-2.0, -1.0
    //-3.0, 0.5
    //-1.5, 2.3
    //-1.0, -2.6

    public static void main(String[] args) {
        new Research().runQuasi(fourthFn, fourthGr, List.of(0.0, 0.0), 0.00001, false);
    }

}

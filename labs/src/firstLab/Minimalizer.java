package firstLab;

import java.util.Objects;
import java.util.function.Function;

/**
 * Ищет минимум переданной функции на заданном отрезке.
 */
public abstract class Minimalizer {

    protected final Function<Double, Double> function;
    protected final double leftBorder, rightBorder;

    /**
     * Создает минимализатор функции function на отрезке [leftBorder, rightBorder]
     *
     * @param function    Минимализируемая функция
     * @param leftBorder  Левая граница отрезка
     * @param rightBorder Правая граница отрезка
     * @throws NullPointerException     Если переданный объект функции является null
     * @throws IllegalArgumentException Если хотя бы одна из переданных границ является бесконечностью или NaN или если leftBorder > rightBorder
     */
    public Minimalizer(Function<Double, Double> function, double leftBorder, double rightBorder) {
        Objects.requireNonNull(function, "Given function is null");
        if (Double.isNaN(leftBorder) || Double.isNaN(rightBorder) ||
                Double.isInfinite(leftBorder) || Double.isInfinite(rightBorder) ||
                leftBorder > rightBorder) {
            throw new IllegalArgumentException("leftBorder > rightBorder");
        }
        this.function = function;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    /**
     * Возвращает значение минимализуемой функции в x
     *
     * @param x Точка, в которой необходимо найти значение
     * @return Значение function в x
     * @throws IllegalArgumentException Если x не принадлежит [leftBorder, rightBorder]
     */
    public double apply(double x) {
        checkBorders(x);
        return function.apply(x);
    }

    /**
     * Найти точку минимума.
     *
     * @param epsilon Точность нахождения точки минимума.
     * @return Точка минимума
     */
    abstract public double minimalize(double epsilon);

    protected void printDependence(int countApplying, double eps) {
//        System.out.println("Количество вычислений функции: " + countApplying + "; Логарифм точности: " + Math.log10(eps));
    }

    protected double printBorders(double leftBorder, double rightBorder, double lastLength, double x1, double x2, double fx1, double fx2) {
        String text = leftBorder + " " + rightBorder + " " + lastLength / (rightBorder - leftBorder) + " " + x1 + " " + fx1 + " " + x2 + " " + fx2;
//        System.out.println(text);
        return rightBorder - leftBorder;
    }

    protected double printBorders(double leftBorder, double rightBorder, double lastLength, double x1, double fx1) {
        String text = leftBorder + " " + rightBorder + " " + lastLength / (rightBorder - leftBorder) + " " + x1 + " " + fx1;
//        System.out.println(text);
        return rightBorder - leftBorder;
    }


    private void checkBorders(double x) {
        if (x < leftBorder || rightBorder < x) {
            throw new IllegalArgumentException("Given number " + x + " is out of allowed interval: [" + leftBorder + "; " + rightBorder + "]");
        }
    }

    private static void printMinimalizer(String method, Minimalizer minimalizer) {
        System.out.println(method);
        try {
            double x = minimalizer.minimalize(0.00001);
            System.out.println("Точка минимума: " + x + "; Минимум: " + minimalizer.apply(x) + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Во время работы метода \"" + method + "\" произошла ошибка: " + e + "\n");
        }
    }

    private static void printAllMinimalizers(Function<Double, Double> function, double leftBorder, double rightBorder) {
        printMinimalizer("Метод дихотомии", new DichotomyMinimalizer(function, leftBorder, rightBorder));
        printSmallDelimiter();
        printMinimalizer("Метод золотого сечения", new GoldenRatioMinimalizer(function, leftBorder, rightBorder));
        printSmallDelimiter();
        printMinimalizer("Метод Фибоначчи", new FibonacciMinimalizer(function, leftBorder, rightBorder));
        printSmallDelimiter();
        printMinimalizer("Метод парабол", new ParabolaMinimalizer(function, leftBorder, rightBorder));
        printSmallDelimiter();
        printMinimalizer("Метод Брента", new BrentsMinimalizer(function, leftBorder, rightBorder));
    }

    private static void printOneDependence(String method, Minimalizer minimalizer, int count) {
        System.out.println(method);
        for (int i = 0; i < count; i++) {
            try {
                double x = minimalizer.minimalize(Math.pow(10, -i));
                System.out.println("Точка минимума: " + x + "; Минимум: " + minimalizer.apply(x) + "\n");
            } catch (IllegalArgumentException e) {
                System.out.println("Во время работы метода произошла ошибка: " + e);
            }
        }
        printSmallDelimiter();
    }

    private static void printAllDependence(Function<Double, Double> function, double leftBorder, double rightBorder, int count) {
//        printOneDependence("Метод дихотомии", new DichotomyMinimalizer(function, leftBorder, rightBorder), count);
//        printOneDependence("Метод золотого сечения", new firstLab.GoldenRatioMinimalizer(function, leftBorder, rightBorder), count);
//        printOneDependence("Метод Фибоначчи", new firstLab.FibonacciMinimalizer(function, leftBorder, rightBorder), count);
//        printOneDependence("Метод парабол", new firstLab.ParabolaMinimalizer(function, leftBorder, rightBorder), count);
//        printOneDependence("Метод Брента", new firstLab.BrentsMinimalizer(function, leftBorder, rightBorder), count);
    }

    private static void printSmallDelimiter() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void printBigDelimiter() {
        for (int i = 0; i < 3; i++) {
            System.out.println("#########################################");
        }
    }

    public static void main(String[] args) {
      }
}

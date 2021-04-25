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

    protected double printBorders(double leftBorder, double rightBorder, double lastLength, double x1, double x2, double fx1, double fx2) {
        String text = leftBorder + " " + rightBorder + " " + lastLength / (rightBorder - leftBorder) + " " + x1 + " " + fx1 + " " + x2 + " " + fx2;
        return rightBorder - leftBorder;
    }


    private void checkBorders(double x) {
        if (x < leftBorder || rightBorder < x) {
            throw new IllegalArgumentException("Given number " + x + " is out of allowed interval: [" + leftBorder + "; " + rightBorder + "]");
        }
    }
}

package thirdLab;

import javax.swing.plaf.synth.SynthUI;
import java.util.ArrayList;
import java.util.List;

public class ConjugateGradientMethod extends AbstractGradientMethod {
    public ConjugateGradientMethod(double epsilon) {
        super(epsilon);
    }

    private List<Double> multi(List<Double> vec, Double num){
        List<Double> res = new ArrayList<>();
        for (Double aDouble : vec) {
            res.add(aDouble * num);
        }
        return res;
    }

    private List<Double> add(List<Double> vec1, List<Double> vec2){
        List<Double> res = new ArrayList<>();

        for(int i = 0; i < vec1.size(); i++){
            Double temp = vec1.get(i) + vec2.get(i);
            res.add(temp);
        }

        return res;
    }

    private Double dotProduct(List<Double> vec1, List<Double> vec2) {
        double res = 0.0;
        for(int i = 0; i < vec1.size(); i++){
            res += vec1.get(i) * vec2.get(i);
        }
        return res;
    }

    private Double norm(List<Double> vec){
        double sum = 0.0;

        for (Double aDouble : vec) {
            sum += Math.pow(aDouble, 2);
        }

        return Math.sqrt(sum);
    }

    private List<Double> startIteration(Function func, List<Double> prevX){
        int n = prevX.size();
        List<Double> prevGrad = func.runGradient(prevX);
        Double normPrevGrad = norm(prevGrad);
        List<Double> prevP = multi(prevGrad, -1.0);
        for(int i = 1; i < n && normPrevGrad >= epsilon; i++){
            List<Double> mulApPrev = func.multi(prevP);
            double aPrev = normPrevGrad * normPrevGrad / dotProduct(mulApPrev, prevP);
            List<Double>  nextX = add(prevX, multi(prevP, aPrev));
            List<Double> nextGrad = add(prevGrad, multi(mulApPrev, aPrev));
            double normNextGrad = norm(nextGrad);
            double b = normNextGrad * normNextGrad / normPrevGrad / normPrevGrad;
            prevP = add(multi(nextGrad, -1.0), multi(prevP, b));
            prevGrad = nextGrad;
            prevX = nextX;
            normPrevGrad = normNextGrad;
        }
        return prevX;
    }

    public List<Double> findMinimum(Function func, List<Double> x0){
        do {
            x0 = startIteration(func, x0);
        } while(norm(func.runGradient(x0)) >= epsilon);

        return x0;
    }
}

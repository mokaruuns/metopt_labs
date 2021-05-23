package thirdLab;

import java.util.List;

public interface Function {
    Double run(List<Double> x);


    public List<Double> runGradient(List<Double> x);

    public List<Double> multi(List<Double> x);
}

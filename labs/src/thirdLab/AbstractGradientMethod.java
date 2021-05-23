package thirdLab;

public abstract class AbstractGradientMethod implements Method {
    Double epsilon;

    public AbstractGradientMethod(Double epsilon){
        this.epsilon = epsilon;
    }
}

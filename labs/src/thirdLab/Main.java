package thirdLab;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void solve(){
        RowColumnMatrix matrix = new RowColumnMatrix(MatrixGenerator.generateOrdinaryMatrix(1000));
        Method m = new ConjugateGradientMethod(1e-7);
        List<Double> temp = new ArrayList<>();
        for(int i = 0; i < matrix.size(); i++){
            temp.add(0.0);
        }
        //matrix.print();
        List<Double> xSolved = m.findMinimum(matrix, temp);
        System.out.println(xSolved);
    }

    public static void main(final String[] args){
        solve();
    }
}

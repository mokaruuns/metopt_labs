package fourthLab.util;

import java.util.ArrayList;
import java.util.List;

public class LES {
    List<List<Double>> ratio = new ArrayList<>();
    List<Double> value = new ArrayList<>();

    public LES(List<List<Double>> ratio, List<Double> value) {
        this.ratio = ratio;
        this.value = value;
    }

    public List<Double> solve() {
        for(int k = 0; k < ratio.size() - 1; k++) {
            for(int i = k + 1; i < ratio.size(); i++)
            {
                Double temp = ratio.get(i).get(k) / ratio.get(k).get(k);
                value.set(i, value.get(i) - temp * value.get(k));
                for(int j = k + 1; j < ratio.size(); j++) {
                    ratio.get(i).set(j, ratio.get(i).get(j) - temp * ratio.get(k).get(j));
                }
            }
        }

        List<Double> x = new ArrayList<>();
        for(int i = 0; i < ratio.size(); i++)
            x.add(0.0);

        x.set(ratio.size() - 1, value.get(ratio.size() - 1) / ratio.get(ratio.size() - 1).get(ratio.size() - 1));

        for(int i = ratio.size() - 2; i >= 0; i--){
            Double sum = 0.0;
            for(int j = i + 1; j < ratio.size(); j++) {
                sum += x.get(j) * ratio.get(i).get(j);
            }
            x.set(i, (value.get(i) - sum) / ratio.get(i).get(i));
        }

        return x;
    }
}

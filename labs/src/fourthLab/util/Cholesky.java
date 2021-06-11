package fourthLab.util;

import java.util.ArrayList;
import java.util.List;

public class Cholesky {
    List<List<Double>> a;

    public Cholesky(List<List<Double>> a) {
        this.a = a;
    }

    public List<List<Double>> decompose() {
        List<List<Double>> res = new ArrayList<>();

        for(int i = 0; i < a.size(); i++) {
            res.add(new ArrayList<>());
            for(int j = 0; j < a.size(); j++){
                res.get(i).add(0.0);
            }
        }

        res.get(0).set(0, Math.sqrt(a.get(0).get(0)));
        for(int i = 1; i < a.size(); i++)
        {
            res.get(i).set(0, a.get(i).get(0) / res.get(0).get(0));
        }

        for(int i = 0; i < a.size(); i++) {
            Double sum = 0.0;
            for(int j = 0; j < i - 1; j++) {
                sum += res.get(i).get(j) * res.get(i).get(j);
            }

            res.get(i).set(i, Math.sqrt(a.get(i).get(i) - sum));
        }

        for(int i = 1; i < a.size() - 1; i++) {
            for(int j = i; j < a.size(); j++) {
                Double sum = 0.0;
                if(i == j) {
                    for(int k = 0; k < i - 1; k++) {
                        sum += res.get(i).get(k) * res.get(i).get(k);
                    }
                    res.get(i).set(i, Math.sqrt(a.get(i).get(i) - sum));
                    continue;
                }
                for(int k = 0; k < i - 1; k++) {
                    sum += res.get(i).get(k) * res.get(j).get(k);
                }
                res.get(j).set(i, (a.get(j).get(i) - sum) / res.get(i).get(i));
            }
        }

        return res;
    }
}

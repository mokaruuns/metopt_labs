package thirdLab;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Math.min;

public class SlauGenerator {

    public void generate(int dim, int width, String file, double maxMod) {
        Path path = Paths.get(file);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path);) {
            bufferedWriter.write(dim + " " + width);
            bufferedWriter.newLine();
            for (int o = 0; o < 2; o++) {
                for (int i = 0; i < dim; i++) {
                    for (int j = 0; j < min(i, width); j++) {
                        newRandom(maxMod, bufferedWriter);
                    }
                    bufferedWriter.newLine();
                }
            }
            for (int i = 0; i < dim; i++) {
                newRandom(maxMod, bufferedWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newRandom(double maxMod, BufferedWriter bufferedWriter) throws IOException {
        double a = (Math.random() - 0.5) * maxMod * 2;
        bufferedWriter.write(Double.toString(a));
        bufferedWriter.write(" ");
    }

    public static void main(String[] args) {
        new SlauGenerator().generate(5, 3, "here.txt", 10000);
    }
}

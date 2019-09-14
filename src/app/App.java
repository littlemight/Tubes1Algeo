package app;
import java.util.*;
import app.Gauss;

public class App {
    public static void main(String[] args) throws Exception {
        double[][] ar;
        int m, n;
        Scanner in = new Scanner(System.in);
        m = in.nextInt();
        n = in.nextInt();
        ar = new double[m + 1][n + 2];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= (n + 1); j++) {
                ar[i][j] = in.nextDouble();
            }
        }

        GaussJordan GJ = new GaussJordan(ar);
        double[][] cur = GJ.GetEchelon();
        double[] sol = GJ.GetSol();
        for (int i = 1; i <= cur.length - 1; i++) {
            for (int j = 1; j <= cur[0].length - 1; j++) {
                System.out.printf("%f ", cur[i][j]);
            }
            System.out.println();
        }
    }
}

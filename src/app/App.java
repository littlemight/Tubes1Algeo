package app;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        double[][] ar;
        int n, m;
        Scanner in = new Scanner(System.in);
        m = in.nextInt();
        n = in.nextInt();
        ar = new double[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                ar[i][j] = in.nextDouble();
            }
        }
        Matrix A = new Matrix(ar);
        Matrix TETE = A.getCofactor();
        TETE.show();
        System.out.println();
        Matrix EF = A.getEchelonG();
        EF.show();
        SPL tes = new SPL(EF);
        
        Matrix.Inverse(EF).show();
        n = in.nextInt();
        in.close();
    }
}

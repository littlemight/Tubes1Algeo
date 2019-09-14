package app;
import java.util.*;

class Interpolasi {
  private int n;
  private int m;
  private double[][] aug;
  private String pers;
  private double[] a;
  private double[] x;
  private double[] y;

  public void ReadPointsKB() {
    Scanner in = new Scanner(System.in);
    n = in.nextInt();
    x = new double[n + 1];
    y = new double[n + 1];
    for (int i = 0; i <= n; i++) {
      x[i] = in.nextDouble();
      y[i] = in.nextDouble();
    }

    aug = new double[n + 2][n + 3];
    for (int i = 1; i <= (n + 1); i++) {
      for (int j = 1; j <= (n + 1); j++) {
        aug[i][j] = Math.pow(x[i - 1], j - 1);
      }
      aug[i][n + 2] = y[i - 1];
    }

    Gauss G = new Gauss(aug);
    a = G.GetSol();
  }

  private void SolvePers() {
    Gauss G = new Gauss(aug);
    a = G.GetSol();
  }
}
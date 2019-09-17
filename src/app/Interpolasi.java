package app;
import java.util.*;

class Interpolasi {
  private int n;
  private double[] a;
  private double[] x;
  private double[] y;
  private Matrix M;

  Interpolasi (int n) { // Interpolasi dari n + 1 buah titik
    this.x = new double[n + 1];
    this.y = new double[n + 1];
    this.a = new double[n + 1];
    this.n = n;
  }

  public void readPointsKB() {
    Scanner in = new Scanner(System.in);

    for (int i = 0; i <= n; i++) {
      x[i] = in.nextDouble();
      y[i] = in.nextDouble();
    }

    double[][] aug = new double[n + 2][n + 3];
    for (int i = 1; i <= (n + 1); i++) {
      for (int j = 1; j <= (n + 1); j++) {
        aug[i][j] = Math.pow(x[i - 1], j - 1);
      }
      aug[i][n + 2] = y[i - 1];
    }
    this.M = new Matrix(aug);
    this.M.show();
  }

  public void solvePers() {
    Matrix EF = M.getEchelonG();
    for (int i = 0; i <= n; i++) {
      a[i] = EF.mat[i + 1][EF.n];  
    }
  }
  
  public void show() {
    for (int i = 0; i <= n; i++) {
      System.out.printf("a[%d] : %f\n", i, a[i]);
    }
  }
}
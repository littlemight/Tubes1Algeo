package app;
import java.util.*;

class Interpolasi {
  private int n;
  private double[] a;
  private double[] x;
  private double[] y;
  private Matrix M;

  //  Interpolasi (int n) { // Interpolasi dari n buah data
  // konstruktor nanti
  // }

  public void readPointsKB() {
    Scanner in = new Scanner(System.in);
    n = in.nextInt();
    this.x = new double[n + 1];
    this.y = new double[n + 1];
    this.a = new double[n + 1];

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
    in.close();
  }

  public void solvePers() {
    Matrix EF = M.getEchelonG();
    for (int i = 0; i <= n; i++) {
      a[i] = EF.mat[i + 1][EF.n];  
    }
  }

  public void show() {
    for (int i = 0; i <= n; i++) {
      System.out.printf("a[%d] : %lf\n", i, a[i]);
    }
  }

}
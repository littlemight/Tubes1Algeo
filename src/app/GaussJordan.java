package app;

public class GaussJordan {
  private static final double EPS = 1e-9;
  private int m;
  private int n;
  private double[][] mat;
  private double[] sol;
  
  GaussJordan(double[][] inp) {
    mat = inp;
    gaussJordanElim(mat);
    solveX();
  }

  private void gaussJordanElim(double[][] mat) {
      m = mat.length - 1;
      n = mat[0].length - 1;
      int nex = 1;
      for (int no = 1; no <= Math.min(m, n); no++) {
        int mx = no;

        // Find next candidate
        System.out.println(no + " + " + nex);
        for (int i = no + 1; i <= m; i++) {
          if (Math.abs(mat[i][nex]) > Math.abs(mat[mx][nex])) {
            mx = i;
          }
        }

        // Swap
        swap(no, mx);

        if (Math.abs(mat[no][nex]) > EPS) {
          // OBE
          for (int i = 1; i <= m; i++) {
            if (i != no) {
              double fac = mat[i][nex] / mat[no][nex];
              for (int j = nex; j <= n; j++) {
                mat[i][j] -= fac*mat[no][j];
              }
            }
          }

          double norm = mat[no][nex];
          // Bagi biar 1
          for (int j = nex; j <= n; j++) {
            mat[no][j] /= norm;
          }
          nex++;
        } else {
          if (nex < n) {
            while (Math.abs(mat[no][nex]) < EPS && nex < n) { // Find next leading non zero element
              nex++;
              if (nex > n) {
                break;
              }
            }
            if (nex <= n) {
              if (no != Math.min(n, m)) {
                no--;
              } else {
                double norm = mat[no][nex];
                for (int j = nex; j <= n; j++) {
                  mat[no][j] /= norm;
                }
              }
            }
          }
        }
      }
  }

  private void swap(int r1, int r2) {
    double tmp[] = mat[r1];
    mat[r1] = mat[r2];
    mat[r2] = tmp;
  }

  public double[][] getEchelon() {
    return mat;
  }

  private boolean isUnique() {
    for (int no = 1; no <= Math.min(n, m); no++) {
      if (Math.abs(mat[no][no] - 1) > EPS) {
        return false;
      }
    }
    return true;
  }
  
  private void solveX() {
    if (isUnique()) {
      sol = new double[n];
      for (int i = Math.min(n - 1, m); i >= 1; i--) {
        double left = 0;
        for (int j = i + 1; j < n; j++) {
          left += mat[i][j] * sol[j];
        }
        sol[i] = (mat[i][n] - left) / mat[i][i];
      }
    }
  }

  public double[] getSol() {
    return sol;
  }
}
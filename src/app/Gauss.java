package app;

public class Gauss {
  private static final double EPS = 1e-9;
  // private double[] sol;
  
  public static Matrix gaussElim(Matrix M) {
    int nex = 1;
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      int mx = no;

      // Find next candidate
      for (int i = no + 1; i <= M.m; i++) {
        if (Math.abs(M.mat[i][nex]) > Math.abs(M.mat[mx][nex])) {
          mx = i;
        }
      }

      // Swap
      M.swap(no, mx);

      if (Math.abs(M.mat[no][nex]) > EPS) {
        // OBE
        for (int i = no + 1; i <= M.m; i++) {
          double fac = M.mat[i][nex] / M.mat[no][nex];
          for (int j = nex; j <= M.n; j++) {
            M.mat[i][j] -= fac*M.mat[no][j];
          }
        }

        double norm = M.mat[no][nex];
        // Bagi biar 1
        for (int j = nex; j <= M.n; j++) {
          M.mat[no][j] /= norm;
        }
        nex++;
      } else {
        if (nex < M.n) {
          while (Math.abs(M.mat[no][nex]) < EPS && nex < M.n) { // Find next leading non zero element
            nex++;
            if (nex > M.n) {
              break;
            }
          }
          if (nex <= M.n) {
            if (no != Math.min(M.n, M.m)) {
              no--;
            } else {
              double norm = M.mat[no][nex];
              for (int j = nex; j <= M.n; j++) {
                M.mat[no][j] /= norm;
              }
            }
          }
        }
      }
    }
    return M;
  }

  private boolean isUnique(Matrix M) {
    for (int no = 1; no <= Math.min(M.n, M.m); no++) {
      if (Math.abs(M.mat[no][no] - 1) > EPS) {
        return false;
      }
    }
    return true;
  }

  // private void solveX() {
  //   if (isUnique()) {
  //     sol = new double[n];
  //     for (int i = Math.min(n - 1, m); i >= 1; i--) {
  //       double left = 0;
  //       for (int j = i + 1; j < n; j++) {
  //         left += mat[i][j] * sol[j];
  //       }
  //       sol[i] = (mat[i][n] - left) / mat[i][i];
  //     }
  //   }
  // }

  // public double[] getSol() {
  //   return sol;
  // }
}
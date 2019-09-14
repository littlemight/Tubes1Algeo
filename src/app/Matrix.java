package app;

class Matrix {
  private int m;
  private int n;
  Matrix(int maxm, int maxn) {
    this.m = maxm + 1;
    this.n = maxn + 1;
  }

  public void show() {
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        System.out.printf("%f ");
      }
      System.out.println();
    }
  }
}
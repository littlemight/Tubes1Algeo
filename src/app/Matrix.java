package app;

import java.io.*;
import java.util.*;

class Matrix {
  final int m;
  final int n;
  public double[][] mat;
  private static final double EPS = 1e-9;

  //================================================================================
  // Constructor
  //================================================================================

  public Matrix() {
    this.m = 0;
    this.n = 0;
    this.mat = new double[0][0];
  }

  public Matrix(int m, int n) {
    this.m = m;
    this.n = n;
    this.mat = new double[m + 1][n + 1];
  }

  public Matrix(double[][] inp) {
    this.m = inp.length - 1;
    this.n = inp[0].length - 1;
    this.mat = new double[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        this.mat[i][j] = inp[i][j];
      }
    }
  }

  public Matrix(Matrix inp) { 
    this.n = inp.n;
    this.m = inp.m;
    this.mat = new double[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        mat[i][j] = inp.mat[i][j];
      }
    }
  }

  //================================================================================
  // Selector / Getter
  //================================================================================

  public int getM() {
    return this.m;
  }

  public int getN() {
    return this.n;
  }

  public Matrix getEchelon() {
    return gaussElim(new Matrix(this));
  }
  
  public Matrix getReducedEchelon() {
    return gaussJordanElim(new Matrix(this));
  }

  public Matrix getCofactor() {
    Matrix res = new Matrix(this.m, this.n);

    for(int i=1;i<=this.m;i++){
      for(int j=1;j<=this.n;j++){
        Matrix dummy = new Matrix(this.m-1, this.n-1);

        for(int k=1;k<=this.m;k++){
          for(int l=1;l<=this.n;l++){
            if(k!=i && l!=j){
              if(k < i){
                if(l < j){
                  dummy.mat[k][l] = this.mat[k][l];
                } else {
                  dummy.mat[k][l-1] = this.mat[k][l];
                }
              } else {
                if(l < j){
                  dummy.mat[k-1][l] = this.mat[k][l];
                } else {
                  dummy.mat[k-1][l-1] = this.mat[k][l];
                }
              }
            }
          }
        }

        res.mat[i][j] = dummy.getDeterminant();
      }
    }
    return res;
  }

  public Matrix getAdjoint(){
    return this.getCofactor().getTranspose();
  }

  public double getDeterminant() {
    if (this.getN() != this.getM()) {
      return 420;
    }
    double det = 1;
    Matrix M = new Matrix(this);
    int nex = 1;
    // Modified Gauss Elimination with Determinant calculation
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      // Find next candidate
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
        det *= -1;
      }

      if (Math.abs(M.mat[no][nex]) > EPS) {
        // OBE
        for (int i = no + 1; i <= M.m; i++) {
          double fac = M.mat[i][nex] / M.mat[no][nex];
          M.add(i, no, -fac);
        }

        det *= M.mat[no][nex];
        normalize(M, no, nex);
        if (nex < M.n) {
          nex++;
        }
      } else {
        if (nex < M.n) {
          nex = findNextLeading(M, no, nex);
          if (nex <= M.n) {
            if (no != Math.min(M.n, M.m)) {
              no--;
            } else {
              if (Math.abs(M.mat[no][nex]) > EPS) {
                det *= M.mat[no][nex];
                normalize(M, no, nex);
                for (int i = 1; i <= M.m; i++) {
                  if (i == no) continue;
                  double fac = M.mat[i][nex] / M.mat[no][nex];
                  M.add(i, no, -fac);
                }
              }
            }
          }
        }
      }
    }

    for (int i = 1; i <= n; i++) {
      if (Math.abs(M.mat[i][i]) < EPS) {
        det = 0;
        break;
      }
    }
    return det;
  }

  public Matrix getTranspose(){
    Matrix res = new Matrix(this.n, this.m);

    for(int i=1;i<=this.m;i++){
      for(int j=1;j<=this.n;j++){
        res.mat[j][i] = this.mat[i][j];
      }
    }

    return res;
  }

  public static Matrix inverse(Matrix in_ar){ // Pre-kondisi matriks M x M
    int m=in_ar.getM();
    int n=m;
    Matrix ar = new Matrix(m,n+n);

    for (int i=1;i<=m;i++){
        for (int j=1;j<=n;j++){
            ar.mat[i][j] = in_ar.mat[i][j];
            ar.mat[i][j+n] = 0;
        }

        ar.mat[i][i+n] = 1;
    }

    int[] front = new int[m+2];
    int curid=1;

    while (curid<=m && front[curid]<=n){
        int min_id=curid;
        for (int i=curid;i<=m;i++){
            int tm=curid;

            while ((tm<=n) && Math.abs(ar.mat[i][tm])<EPS){
                tm++;
            }

            front[i]=tm;

            if (front[i]<front[min_id]){
                min_id=i;
            }
        }

        // swap
        ar.swap(curid, min_id);

        int tmp = front[min_id];
        front[min_id] = front[curid];
        front[curid] = tmp;
        //------------------
        
        if (front[curid]!=n+1){
            double bag = ar.mat[curid][front[curid]];
            ar.rowtimesX(curid, 1/bag);

            for (int i=1;i<=m;i++){
                if (front[i]>front[curid] || i==curid) continue;

                double factor = ar.mat[i][front[curid]] / ar.mat[curid][front[curid]];
                ar.add(i, curid, -factor);
            }
            
            curid++;
        }

    }

    // ar.show();
    Matrix res = new Matrix(m,n);
    for (int i = 1; i <= m; i++) {
      if (Math.abs(ar.mat[i][i]) < EPS) {
        res.mat = null;
        return res;
      }
    }

    for (int i=1;i<=m;i++){
        for (int j=1;j<=n;j++){
            res.mat[i][j] = ar.mat[i][j+n];
        }
    }

    return res;
  }

  //================================================================================
  // Setter
  //================================================================================
  public void toEchelon() {
    gaussElim(this);
  }

  public void toReducedEchelon() {
    gaussJordanElim(this);
  }

  //================================================================================
  // Input / Output
  //================================================================================

  public void show() {
    if (this.mat == null) {
      System.out.println("Matriks invalid.");
      return;
    }
    
    System.out.printf("Baris: %d | Kolom: %d\n", this.getM(), this.getN());
    for (int i = 1; i <= this.getM(); i++) {
      for (int j = 1; j <= this.getN(); j++) {
        System.out.printf("%f ", this.mat[i][j]);
      }
      System.out.println();
    }
  }
  
  public static Matrix readKB() {
    Scanner in = new Scanner(System.in);
    int m_sz, n_sz;
    System.out.println("Banyaknya baris: ");
    m_sz = in.nextInt();
    System.out.println("Banyaknya kolom: ");
    n_sz = in.nextInt();
    double[][] tmp = new double[m_sz + 1][n_sz + 1];

    for (int i = 1; i <= m_sz; i++) {
      for (int j = 1; j <= n_sz; j++) {
        tmp[i][j] = in.nextDouble();
      }
    }
    Matrix ret = new Matrix(tmp);
    in.close();
    return ret;
  }

  public static Matrix readFile(String filename){
    int line_cnt=0;
    String line;
    String[] arr = new String[101];
    double[][] res;
    Matrix mat = new Matrix();

    try {
      FileReader filetoread = new FileReader(filename);

      BufferedReader buffer = new BufferedReader(filetoread);

      while ((line = buffer.readLine()) != null){
        line_cnt++;
        arr[line_cnt] = line;
      }

      res = parse(arr,line_cnt);
      mat = new Matrix(res);
      // mat.show();

      buffer.close();
      return mat;
    }
    catch(FileNotFoundException ex) {
      System.out.println(
          "Unable to open file '" + 
          filename + "'");
      return mat;            
    }
    catch(IOException ex) {
      System.out.println(
          "Error reading file '" 
          + filename + "'");
      return mat;                  
      // Or we could just do this: 
      // ex.printStackTrace();
    }
    
  }

  private static double[][] parse(String[] s, int n){
    double[][] res;

    if (n==0) return new double[0][0];

    int m=0;
   
    String[] temp;
    temp = s[1].split(" ");
    m = temp.length;
    res = new double[n+1][m+1];
    for (int j=1;j<=m;j++){
      res[1][j] = Double.parseDouble(temp[j-1]);
    }

    
    for (int i=2;i<=n;i++){
      temp = s[i].split(" ");
      for (int j=1;j<=m;j++){
        res[i][j] = Double.parseDouble(temp[j-1]);
      }
    }

    return res;
  }

  //================================================================================
  // OBE (Basic Elementary Operation) & Elimination Helpers
  //================================================================================

  public void swap(int r1, int r2) {
    double[] tmp = mat[r1];
    mat[r1] = mat[r2];
    mat[r2] = tmp;
  }

  public void add(int r1, int r2, double fac) {
    for (int i=1;i<=this.getN();i++){
      if (Math.abs(this.mat[r2][i]) < EPS) continue;
      this.mat[r1][i] += this.mat[r2][i] * fac;
    }
  }

  public void rowtimesX(int r, double x) {
    for (int i=1;i<=this.getN();i++){
      if (Math.abs(this.mat[r][i]) < EPS) continue;
      this.mat[r][i] *= x;
    }
  }

  public int leading(int r) {
    for (int i=1;i<=this.getN();i++){
      if (Math.abs(this.mat[r][i]) > EPS) return i;
    }

    return -1;
  }

  private int nextCandidate(Matrix M, int r, int c) {
    int ret = r;
    for (int i = r; i <= M.m; i++) {
      if (Math.abs(M.mat[i][c]) > Math.abs(M.mat[ret][c])) {
        ret = i;
      }
    }
    return ret;
  }

  private int findNextLeading(Matrix M, int row, int col) {
    int ret = col;
    while (Math.abs(M.mat[row][ret]) < EPS && ret < M.n && nextCandidate(M, row, ret) == row) { // Find next leading non zero element
      ret++;
    }
    return ret;
  }

  private void normalize(Matrix M, int row, int col) {
    double norm = M.mat[row][col];
    for (int j = col; j <= M.n; j++) {
      M.mat[row][j] /= norm;
    }
  }

  //================================================================================
  // Operation
  //================================================================================
  public Matrix mult(Matrix M) {
    Matrix ret = new Matrix(this.m, M.n);
    for (int i = 1; i <= ret.m; i++) {
      for (int j = 1; j <= ret.n; j++) {
        for (int k = 1; k <= this.n; k++) {
          ret.mat[i][j] += (this.mat[i][k] * M.mat[k][j]);
        }
      }
    }
    return ret;
  }

  private Matrix gaussElim(Matrix M) {
    int nex = 1;
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      // Find next candidate
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
      }

      if (Math.abs(M.mat[no][nex]) > EPS) {
        // OBE
        for (int i = no + 1; i <= M.m; i++) {
          double fac = M.mat[i][nex] / M.mat[no][nex];
          M.add(i, no, -fac);
        }

        normalize(M, no, nex);
        if (nex < M.n) {
          nex++;
        }
      } else {
        if (nex < M.n) {
          nex = findNextLeading(M, no, nex);
          if (nex <= M.n) {
            if (no != Math.min(M.n, M.m)) {
              no--;
            } else {
              if (Math.abs(M.mat[no][nex]) > EPS) {
                normalize(M, no, nex);
                for (int i = 1; i <= M.m; i++) {
                  if (i == no) continue;
                  double fac = M.mat[i][nex] / M.mat[no][nex];
                  M.add(i, no, -fac);
                }
              }
            }
          }
        }
      }
    }
    return M;
  }

  private Matrix gaussJordanElim(Matrix M) {
    int nex = 1;
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      // Find next candidate
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
      }

      if (Math.abs(M.mat[no][nex]) > EPS) {
        // OBE
        for (int i = 1; i <= M.m; i++) {
          if (i == no) continue;
          double fac = M.mat[i][nex] / M.mat[no][nex];
          M.add(i, no, -fac);
        }

        normalize(M, no, nex);
        if (nex < M.n) {
          nex++;
        }
      } else {
        if (nex < M.n) {
          nex = findNextLeading(M, no, nex);
          if (nex <= M.n) {
            if (no != Math.min(M.n, M.m)) {
              no--;
            } else {
              if (Math.abs(M.mat[no][nex]) > EPS) {
                normalize(M, no, nex);
                for (int i = 1; i <= M.m; i++) {
                  if (i == no) continue;
                  double fac = M.mat[i][nex] / M.mat[no][nex];
                  M.add(i, no, -fac);
                }
              }
            }
          }
        }
      }
    }
    return M;
  }
}
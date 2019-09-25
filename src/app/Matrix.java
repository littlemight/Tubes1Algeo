package app;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class Matrix {
  final int m;
  final int n;
  public BigDecimal[][] mat;
  private static final BigDecimal EPS = BigDecimal.valueOf(1e-12);

  //================================================================================
  // Constructor
  //================================================================================

  public Matrix() {
    this.m = 0;
    this.n = 0;
    this.mat = new BigDecimal[0][0];
  }

  public Matrix(int m, int n) {
    this.m = m;
    this.n = n;
    this.mat = new BigDecimal[m + 1][n + 1];
    for (int i = 1; i <= this.m; i++) {
      for (int j = 1; j <= this.n; j++) {
        this.mat[i][j] = BigDecimal.ZERO;
      }
    }
  }

  public Matrix(BigDecimal[][] inp) {
    this.m = inp.length - 1;
    this.n = inp[0].length - 1;
    this.mat = new BigDecimal[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        this.mat[i][j] = inp[i][j];
      }
    }
  }

  public Matrix(Matrix inp) { 
    this.n = inp.n;
    this.m = inp.m;
    this.mat = new BigDecimal[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        mat[i][j] = inp.mat[i][j];
      }
    }
  }

  public boolean isSquare(){
    return (this.m==this.n);
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
    try {
      if (this.m != this.n) {
        throw new NullPointerException();
      }
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
    } catch (NullPointerException e) {
      System.out.println("Dimensi matriks tidak valid untuk matriks kofaktor");
      return null;
    }
  }

  public Matrix getAdjoint(){
    return this.getCofactor().getTranspose();
  }

  public BigDecimal getDeterminant() {
    // Pre kondisi n == m
    BigDecimal det = BigDecimal.ONE;
    Matrix M = new Matrix(this);
    int nex = 1;

    if (n != m) return BigDecimal.valueOf(-42690);
    // Modified Gauss Elimination with Determinant calculation
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      // Find next candidate
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
        det = det.multiply(BigDecimal.ONE.negate());
      }

      if (!Util.isZero(M.mat[no][nex])) {
        // OBE
        for (int i = no + 1; i <= M.m; i++) {
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], 30, RoundingMode.HALF_UP);
          M.add(i, no, fac.negate());
        }

        det = det.multiply(M.mat[no][nex]);
        normalize(M, no, nex);
        M.mat[no][nex] = BigDecimal.ONE;
        if (nex < M.n) {
          nex++;
        }
      } else {
          nex = findNextLeading(M, no, nex);
          mx = nextCandidate(M, no, nex);
          if (no != mx) {
            M.swap(no, mx);
          }
          if (M.mat[no][nex].abs().compareTo(EPS) >= 0) {
            for (int i = 1; i <= M.m; i++) {
              if (i == no) continue;
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], 30, RoundingMode.HALF_UP);
              M.add(i, no, fac.negate());
              M.mat[i][nex] = BigDecimal.ZERO;
            }
            det = det.multiply(M.mat[no][nex]);
            normalize(M, no, nex);
            M.mat[no][nex] = BigDecimal.ONE;
            if (nex < M.n) {
              nex++;
            }
          }
      }
    }

    for (int i = 1; i <= n; i++) {
      if (M.mat[i][i].abs().compareTo(EPS) < 0) {
        det = BigDecimal.ZERO;
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

  public static Matrix inverse(Matrix in_ar) {
    // int m=in_ar.getM();
    // int n=m;
    // Matrix ar = new Matrix(m,n+n);

    // for (int i=1;i<=m;i++){
    //     for (int j=1;j<=n;j++){
    //         ar.mat[i][j] = in_ar.mat[i][j];
    //         ar.mat[i][j+n] = 0;
    //     }

    //     ar.mat[i][i+n] = 1;
    // }

    // int[] front = new int[m+2];
    // int curid=1;

    // while (curid<=m && front[curid]<=n){
    //     int min_id=curid;
    //     for (int i=curid;i<=m;i++){
    //         int tm=curid;

    //         while ((tm<=n) && ar.mat[i][tm]==0){
    //             tm++;
    //         }

    //         front[i]=tm;

    //         if (front[i]<front[min_id]){
    //             min_id=i;
    //         }
    //     }

    //     // swap
    //     ar.swap(curid, min_id);

    //     int tmp = front[min_id];
    //     front[min_id] = front[curid];
    //     front[curid] = tmp;
    //     //------------------
        
    //     if (front[curid]!=n+1){
    //         BigDecimal bag = ar.mat[curid][front[curid]];
    //         ar.rowtimesX(curid, 1/bag);

    //         for (int i=1;i<=m;i++){
    //             if (front[i]>front[curid] || i==curid) continue;

    //             BigDecimal factor = ar.mat[i][front[curid]] / ar.mat[curid][front[curid]];
    //             ar.add(i, curid, -factor);
    //         }
            
    //         curid++;
    //     }

    // }

    // // ar.show();
    // Matrix res = new Matrix(m,n);
    // for (int i = 1; i <= m; i++) {
    //   if (Math.abs(ar.mat[i][i]) < EPS) {
    //     res.mat = null;
    //     return res;
    //   }
    // }

    // for (int i=1;i<=m;i++){
    //     for (int j=1;j<=n;j++){
    //         res.mat[i][j] = ar.mat[i][j+n];
    //     }
    // }

    // return res;
    return null;
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
    try {
      if (this.mat == null) {
        throw new NullPointerException();
      }
      System.out.printf("Baris: %d | Kolom: %d\n", this.getM(), this.getN());
      for (int i = 1; i <= this.getM(); i++) {
        for (int j = 1; j <= this.getN(); j++) {
          System.out.printf("%.4f ", this.mat[i][j]);
        }
        System.out.println();
      }
    } catch (NullPointerException e) {
      System.out.println("Matriks invalid.");
    }
  }
  
  public static Matrix readKB() {
    Scanner in = new Scanner(System.in);
    int m_sz, n_sz;
    System.out.println("Banyaknya baris: ");
    m_sz = in.nextInt();
    System.out.println("Banyaknya kolom: ");
    n_sz = in.nextInt();
    BigDecimal[][] tmp = new BigDecimal[m_sz + 1][n_sz + 1];

    for (int i = 1; i <= m_sz; i++) {
      for (int j = 1; j <= n_sz; j++) {
        tmp[i][j] = in.nextBigDecimal();
      }
    }
    Matrix ret = new Matrix(tmp);
    return ret;
  }

  public static Matrix readFile(String filename){
    int line_cnt=0;
    String line;
    String[] arr = new String[101];
    BigDecimal[][] res;
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

  private static BigDecimal[][] parse(String[] s, int n){
    BigDecimal[][] res;

    if (n==0) return new BigDecimal[0][0];

    int m=0;
   
    String[] temp;
    temp = s[1].split(" ");
    m = temp.length;
    res = new BigDecimal[n+1][m+1];
    for (int j=1;j<=m;j++){
      res[1][j] = new BigDecimal(temp[j-1]);
    }

    
    for (int i=2;i<=n;i++){
      temp = s[i].split(" ");
      for (int j=1;j<=m;j++){
        res[i][j] = new BigDecimal(temp[j-1]);
      }
    }

    return res;
  }

  //================================================================================
  // OBE (Basic Elementary Operation) & Elimination Helpers
  //================================================================================

  public void swap(int r1, int r2) {
    BigDecimal[] tmp = mat[r1];
    mat[r1] = mat[r2];
    mat[r2] = tmp;
  }

  public void add(int r1, int r2, BigDecimal fac) {
    // try {
      for (int i=1;i<=this.getN();i++){
        this.mat[r1][i] = this.mat[r1][i].add(this.mat[r2][i].multiply(fac));
      }
    // } catch (NullPointerException e) {
    //   System.out.println("mampus");
    // }
  }

  public void rowtimesX(int r, BigDecimal x) {
    for (int i=1;i<=this.getN();i++){
      this.mat[r][i] = this.mat[r][i].multiply(x);
    }
  }

  public int leading(int r) {
    for (int i=1;i<=this.getN();i++){
      if (!Util.isZero(mat[r][i])) return i;
    }

    return -1;
  }

  private int nextCandidate(Matrix M, int r, int c) {
    int ret = r;
    for (int i = r; i <= M.m; i++) {
      if (M.mat[i][c].abs().compareTo(M.mat[ret][c].abs()) > 0 && !Util.isZero(M.mat[i][c])) {
        ret = i;
      }
    }
    return ret;
  }

  private int findNextLeading(Matrix M, int row, int col) {
    int ret = col;
    while (M.mat[row][ret].abs().compareTo(EPS) < 0 && ret < M.n && nextCandidate(M, row, ret) == row) { // Find next leading non zero element
      ret++;
    }
    return ret;
  }

  private void normalize(Matrix M, int row, int col) {
    BigDecimal norm = M.mat[row][col];
    for (int j = col; j <= M.n; j++) {
      M.mat[row][j] = M.mat[row][j].divide(norm, 30, RoundingMode.HALF_UP);
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
          ret.mat[i][j] = ret.mat[i][j].add(this.mat[i][k].multiply(M.mat[k][j]));
        }
      }
    }
    return ret;
  }

  private Matrix gaussElim(Matrix M) {
    int nex = 1;
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      // Find next candidate
      // M.show();
      // System.out.println("Next");
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
      }

      if (!Util.isZero(M.mat[no][nex])) {
        // OBE
        for (int i = no + 1; i <= M.m; i++) {
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], 30, RoundingMode.HALF_UP);
          M.add(i, no, fac.negate());
          M.mat[i][nex] = BigDecimal.ZERO;
        }
        normalize(M, no, nex);
        M.mat[no][nex] = BigDecimal.ONE;
        if (nex < M.n) {
          nex++;
        }
      } else {
          nex = findNextLeading(M, no, nex);
          mx = nextCandidate(M, no, nex);
          if (no != mx) {
            M.swap(no, mx);
          }
          if (!Util.isZero(M.mat[no][nex])) {
            for (int i = no + 1; i <= M.m; i++) {
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], 30, RoundingMode.HALF_UP);
              M.add(i, no, fac.negate());
              M.mat[i][nex] = BigDecimal.ZERO;
            }
            normalize(M, no, nex);
            M.mat[no][nex] = BigDecimal.ONE;
            if (nex < M.n) {
              nex++;
            }
          }
      }
    }

    for (int i = 1; i <= M.getM(); i++) {
      for (int j = 1; j <= M.getN();j++) {
        if (Util.isZero(M.mat[i][j])) {
          M.mat[i][j] = BigDecimal.ZERO;
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

      if (M.mat[no][nex].abs().compareTo(EPS) > 0) {
        // OBE
        for (int i = 1; i <= M.m; i++) {
          if (i == no) continue;
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], 30, RoundingMode.HALF_UP);
          // System.out.println(M.mat[i][nex] + " " + fac + " " + M.mat[no][nex]);
          M.add(i, no, fac.negate());
          M.mat[i][nex] = BigDecimal.ZERO;
        }
        normalize(M, no, nex);
        M.mat[no][nex] = BigDecimal.valueOf(1);
        if (nex < M.n) {
          nex++;
        }
      } else {
          nex = findNextLeading(M, no, nex);
          mx = nextCandidate(M, no, nex);
          if (no != mx) {
            M.swap(no, mx);
          }
          if (M.mat[no][nex].abs().compareTo(EPS) > 0) {
            for (int i = 1; i <= M.m; i++) {
              if (i == no) continue;
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], 30, RoundingMode.HALF_UP);
              // System.out.println(M.mat[i][nex] + " " + fac + " " + M.mat[no][nex]);
              M.add(i, no, fac.negate());
              M.mat[i][nex] = BigDecimal.ZERO;
            }
            normalize(M, no, nex);
            M.mat[no][nex] = BigDecimal.ONE;
            if (nex < M.n) {
              nex++;
            }
          }
      }
    }

    for (int i = 1; i <= M.getM(); i++) {
      for (int j = 1; j <= M.getN();j++) {
        if (Util.isZero(M.mat[i][j])) {
          M.mat[i][j] = BigDecimal.ZERO;
        }
      }
    }
    return M;
  }
}
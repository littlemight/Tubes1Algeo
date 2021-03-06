package app;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * class Matrix mendefinisikan objek matriks dan semua operasi-operasinya
 */
class Matrix {
  final int m;
  final int n;
  public BigDecimal[][] mat;

  //================================================================================
  // Constructor
  //================================================================================

  /**
    * Konstruktor untuk membuat Matriks dengan dimensi 0x0 (Kosong)
    *
    */
  public Matrix() {
    this.m = 0;
    this.n = 0;
    this.mat = new BigDecimal[0][0];
  }
  /**
    * Konstruktor untuk membuat Matriks dengan dimensi m x n
    *
    * @param m jumlah baris
    * @param n jumlah kolom
    */
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
  /**
    * Konstruktor untuk membuat Matriks dengan dimensi sesuai dengan array dua dimensi BigDecimal
    *
    * @param inp array dua dimensi BigDecimal
    */
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
  /**
    * Konstruktor untuk membuat Matriks dengan input Matriks juga
    *
    * @param inp Objek Matriks
    */
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
  /**
   * mengecek apabila matriks merupakan matriks persegi
   * @return mengembalikan true atau false
   */
  public boolean isSquare(){
    return (this.m==this.n);
  }

  //================================================================================
  // Selector / Getter
  //================================================================================

  /**
   * selektor baris
   * @return mengembalikan banyak baris dari matriks
   */
  public int getM() {
    return this.m;
  }

  /**
   * selektor kolom
   * @return mengembalikan banyak kolom dari matriks
   */
  public int getN() {
    return this.n;
  }
  
  /**
   * mencari bentuk eselon
   * @return mengembalikan matriks eselon
   */
  public Matrix getEchelon() {
    return gaussElim(new Matrix(this));
  }
  
  /**
   * mencari bentuk eselon tereduksi
   * @return mengembalikan matriks eselon tereduksi
   */
  public Matrix getReducedEchelon() {
    return gaussJordanElim(new Matrix(this));
  }

  /**
   * mencari matriks kofaktor
   * @return mengembalikan matriks kefaktor
   */
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
          res.mat[i][j] = dummy.getDeterminantGJ();
          if ((i + j) % 2 == 1) res.mat[i][j] = res.mat[i][j].negate();
        }
      }
      return res;
    } catch (NullPointerException e) {
      System.out.println("Dimensi matriks tidak valid untuk matriks kofaktor");
      return null;
    }
  }

  /**
   * mencari adjoin matriks
   * @return mengembalikan matriks adjoin
   */
  public Matrix getAdjoint(){
    return this.getCofactor().getTranspose();
  }

  /**
   * mencari nilai determinan menggunakan kofaktor
   * @return mengembalikan determinan matriks
   */
  public BigDecimal getDeterminantCofactor() {
    BigDecimal det = BigDecimal.ZERO;
    Matrix M = new Matrix(this);
    if (M.getN() == 2) {
      det = (M.mat[1][1].multiply(M.mat[2][2])).subtract(M.mat[1][2].multiply(M.mat[2][1]));
      return det;
    }

    for (int c = 1; c <= M.getN(); c++) {
      Matrix dummy = new Matrix(M.getM() - 1, M.getN() - 1);
      for(int k=1;k<=M.m;k++){
        for(int l=1;l<=M.n;l++){
          if(k!=1 && l!=c){
            if(k < 1){
              if(l < 1){
                dummy.mat[k][l] = this.mat[k][l];
              } else {
                dummy.mat[k][l-1] = this.mat[k][l];
              }
            } else {
              if(l < c){
                dummy.mat[k-1][l] = this.mat[k][l];
              } else {
                dummy.mat[k-1][l-1] = this.mat[k][l];
              }
            }
          }
        }
      }
      BigDecimal cur = dummy.getDeterminantCofactor();
      cur = cur.multiply(M.mat[1][c]);
      if (c % 2 == 0) cur = cur.negate(); 
      det = det.add(cur);
    }
    return det;
  }

  /**
   * mencari nilai determinan menggunakan Gauss
   * @return mengembalikan determinan matriks
   */
  public BigDecimal getDeterminantG() {
    // Pre kondisi n == m
    BigDecimal det = BigDecimal.ONE;
    Matrix M = new Matrix(this);
    int nex = 1;

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
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
          if (!Util.isZero(M.mat[no][nex])) {
            for (int i = no + 1; i <= M.m; i++) {
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
      if (Util.isZero(M.mat[i][i])) {
        det = BigDecimal.ZERO;
        break;
      }
    }
    if (Util.isZero(det)) {
      det = BigDecimal.ZERO;
    }
    return det;
  }

  /**
   * mencari nilai determinan menggunakan Gauss-Jordan
   * @return mengembalikan determinan matriks
   */
  public BigDecimal getDeterminantGJ() {
    // Pre kondisi n == m
    BigDecimal det = BigDecimal.ONE;
    Matrix M = new Matrix(this);
    int nex = 1;

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
        for (int i = 1; i <= M.m; i++) {
          if (i == no) continue;
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
          if (!Util.isZero(M.mat[no][nex])) {
            for (int i = 1; i <= M.m; i++) {
              if (i == no) continue;
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
      if (Util.isZero(M.mat[i][i])) {
        det = BigDecimal.ZERO;
        break;
      }
    }
    // if (Util.isZero(det)) {
    //   det = BigDecimal.ZERO;
    // }
    return det;
  }

  /**
   * mencari matriks transpose
   * @return mengembalikan matriks transpose
   */
  public Matrix getTranspose(){
    Matrix res = new Matrix(this.n, this.m);

    for(int i=1;i<=this.m;i++){
      for(int j=1;j<=this.n;j++){
        res.mat[j][i] = this.mat[i][j];
      }
    }

    return res;
  }

  /**
   * mencari matriks balikan menggunakan Gauss-Jordan
   * @return mengembalikan matriks balikan
   */
  public Matrix getInverseGJ() {
    // kalo ga ketemu, return matrix null
    // kalo ketemu return matrix inversenya
    // prekondisi n == m
    
    int m = this.getM();
    int n = m;
    Matrix ar = new Matrix(m,n+n);

    for (int i=1;i<=m;i++){
        for (int j=1;j<=n;j++){
            ar.mat[i][j] = this.mat[i][j];
            ar.mat[i][j+n] = BigDecimal.ZERO;
        }
        ar.mat[i][i+n] = BigDecimal.ONE;
    }

    ar.toReducedEchelon();
    
    for (int i = 1; i <= ar.getM(); i++) {
      if (Util.isZero(ar.mat[i][i])) {
        return null;
      }
    }

    Matrix res = new Matrix(m,n);
    for (int i=1;i<=m;i++){
      for (int j=1;j<=n;j++){
        res.mat[i][j] = ar.mat[i][j+n];
      }
    }

    return res;
  }

  /**
   * mencari matriks balikan menggunakan kofaktor
   * @return mengembalikan matriks balikan
   */
  public Matrix getInverseCofactor() {
    Matrix ret = new Matrix(this);
    BigDecimal det = ret.getDeterminantGJ();
    if (Util.isZero(det)) {
      return null;
    }
    return ret.getAdjoint().divKons(det);
  }
  //================================================================================
  // Setter
  //================================================================================

  /**
   * mengubah matriks ini menjadi bentuk eselon
   */
  public void toEchelon() {
    gaussElim(this);
  }

  /**
   * mengubah matriks ini menjadi bentuk eselon tereduksi
   */
  public void toReducedEchelon() {
    gaussJordanElim(this);
  }

  //================================================================================
  // Input / Output
  //================================================================================

  /**
   * menampilkan matriks ke layar
   */
  public void show() {
    try {
      if (this.mat == null) {
        throw new NullPointerException();
      }
      // System.out.printf("Baris: %d | Kolom: %d\n", this.getM(), this.getN());
      for (int i = 1; i <= this.getM(); i++) {
        for (int j = 1; j <= this.getN(); j++) {
          System.out.print(Util.formatOutputLong(this.mat[i][j]));
          // System.out.print(this.mat[i][j]);
          if (j < this.getN()) System.out.print(" ");
        }
        System.out.println();
      }
    } catch (NullPointerException e) {
      System.out.println("Matriks tidak valid.");
    }
  }
  
  /**
   * menyimpan matriks ke file
   * @param filename nama file
   */
  public void showFile(String filename) throws IOException {
    try {
      if (this.mat == null) {
        throw new NullPointerException();
      }
      // System.out.printf("Baris: %d | Kolom: %d\n", this.getM(), this.getN());
      String ret = "";
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      for (int i = 1; i <= this.getM(); i++) {
        for (int j = 1; j <= this.getN(); j++) {
          String result = Util.formatOutput(this.mat[i][j]);
          ret += result;
          if (j == this.getN()) {
            if (i < this.getM()) {
              ret += "\n";
            }
          }
          else ret += " ";
        }
      }
      writer.write(ret);
      writer.close();
    } catch (NullPointerException e) {
      System.out.println("Matriks tidak valid.");
    }
  }

  /**
   * metode statik untuk membaca matriks dari keyboard
   */
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

  /**
   * metode statik untuk membaca matriks dari file
   * @param filename nama file
   */
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

      buffer.close();
      return mat;
    }
    catch(FileNotFoundException ex) {
      System.out.println(
          "File tidak dapat dibuka '" + 
          filename + "'");
      return mat;            
    }
    catch(IOException ex) {
      System.out.println(
          "File tidak dapat dibaca '" 
          + filename + "'");
      return mat;                  
    }    
  }
  
  /**
   * metode statik untuk parsing dari string ke BigDecimal
   * @param s array string
   * @param n banyak baris string
   */
  private static BigDecimal[][] parse(String[] s, int n){
    BigDecimal[][] res;

    if (n==0) return new BigDecimal[0][0];

    int m = 0;
   
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

  /**
   * swap berguna untuk menukar baris
   * @param r1 baris 1
   * @param r2 baris 2
   */
  public void swap(int r1, int r2) {
    BigDecimal[] tmp = mat[r1];
    mat[r1] = mat[r2];
    mat[r2] = tmp;
  }

  /**
   * menjumlahkan baris dengan kelipatan baris lain
   * @param r1 baris 1
   * @param r2 baris 2
   * @param fac skalar
   */
  public void add(int r1, int r2, BigDecimal fac) {
    for (int i=1;i<=this.getN();i++){
      this.mat[r1][i] = this.mat[r1][i].add(this.mat[r2][i].multiply(fac));
    }
  }

  /**
   * mengalikan baris dengan skalar
   * @param r baris
   * @param x skalar
   */
  public void rowtimesX(int r, BigDecimal x) {
    for (int i=1;i<=this.getN();i++){
      this.mat[r][i] = this.mat[r][i].multiply(x);
    }
  }

  /**
   * mencari nilai terdepan dari baris
   * @param r baris
   * @return nilai depan
   */
  public int leading(int r) {
    for (int i=1;i<=this.getN();i++){
      if (!Util.isZero(mat[r][i])) return i;
    }

    return -1;
  }

  /**
   * mencari nonzero pertama pada baris
   * @param M matriks
   * @param r baris
   * @param c kolom
   * @return nilai non zero pertama
   */
  private int nextCandidate(Matrix M, int r, int c) {
    int ret = r;
    for (int i = r; i <= M.m; i++) {
      if (M.mat[i][c].abs().compareTo(M.mat[ret][c].abs()) > 0 && !Util.isZero(M.mat[i][c])) {
        ret = i;
      }
    }
    return ret;
  }

  /**
   * mencari leading nonzero pertama
   * @param M matriks
   * @param row baris
   * @param col kolom
   * @return nilai non zero pertama
   */
  private int findNextLeading(Matrix M, int row, int col) {
    int ret = col;
    while (Util.isZero(M.mat[row][ret]) && ret < M.n && nextCandidate(M, row, ret) == row) { // Find next leading non zero element
      ret++;
    }
    return ret;
  }

  /**
   * membagi baris agar leadingnya menjadi 1
   * @param M matriks
   * @param r baris
   * @param c kolom
   */
  private void normalize(Matrix M, int row, int col) {
    BigDecimal norm = M.mat[row][col];
    for (int j = col; j <= M.n; j++) {
      M.mat[row][j] = M.mat[row][j].divide(norm, Util.divScale, RoundingMode.HALF_UP);
    }
  }

  //================================================================================
  // Operation
  //================================================================================

  /**
   * mengalikan matriks dengan matriks M
   * @param M matriks pengali
   * @return hasil perkalian
   */
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

  /**
   * mengalikan matriks dengan konstanta
   * @param k konstanta
   * @return hasil perkalian
   */
  public Matrix multKons(BigDecimal k) {
    Matrix ret = new Matrix(this);
    for (int i = 1; i <= ret.getM(); i++) {
      for (int j = 1; j <= ret.getN(); j++) {
        ret.mat[i][j] = ret.mat[i][j].multiply(k);   
      }
    }
    return ret;
  }

  /**
   * membagi matriks dengan konstanta
   * @param k konstanta
   * @return hasil pembagian
   */
  public Matrix divKons(BigDecimal k) {
    Matrix ret = new Matrix(this);
    BigDecimal kdiv = BigDecimal.ONE.divide(k, Util.divScale, RoundingMode.HALF_UP);
    return ret.multKons(kdiv);
  }

  /**
   * melakukan eliminasi Gauss
   * @param M matriks
   * @return matriks setelah eliminasi Gauss
   */
  private Matrix gaussElim(Matrix M) {
    int nex = 1;
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
      }

      if (!Util.isZero(M.mat[no][nex])) {
        // OBE
        for (int i = no + 1; i <= M.m; i++) {
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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

  /**
   * melakukan eliminasi Gauss-Jordan
   * @param M matriks
   * @return matriks setelah eliminasi Gauss-Jordan
   */
  private Matrix gaussJordanElim(Matrix M) {
    int nex = 1;
    for (int no = 1; no <= Math.min(M.m, M.n); no++) {
      // Find next candidate
      int mx = nextCandidate(M, no, nex);

      // Swap
      if (no != mx) {
        M.swap(no, mx);
      }

      if (!Util.isZero(M.mat[no][nex])) {
        // OBE
        for (int i = 1; i <= M.m; i++) {
          if (i == no) continue;
          BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
          if (!Util.isZero(M.mat[no][nex])) {
            for (int i = 1; i <= M.m; i++) {
              if (i == no) continue;
              BigDecimal fac = M.mat[i][nex].divide(M.mat[no][nex], Util.divScale, RoundingMode.HALF_UP);
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
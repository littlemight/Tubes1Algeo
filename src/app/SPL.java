package app;
import app.Matrix;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.io.*;

class SPL {
  private Matrix M; // Augmented matrix
  private Matrix A; // Coefficient matrix
  private Matrix B; // Kons matrix
  public Matrix EF;
  public Matrix free_solution;  // tempat solusi variabel bebas
  public Matrix sol;
  public static String[] free_var;
  private int state;

  /*
  0 : doesnt have sol
  1 : unique sol
  2 : many sol
  */
  public SPL(Matrix aug) { // Constructor from augmented matrix
    M = new Matrix(aug);
    EF = new Matrix(M.getEchelon());
    BigDecimal[][] koef = new BigDecimal[M.getM() + 1][M.getN()];
    BigDecimal[][] b = new BigDecimal[M.getM() + 1][2];
    for (int i = 1; i <= M.getM(); i++) {
      for (int j = 1; j <= M.getN() - 1; j++) {
        koef[i][j] = M.mat[i][j];
      }
    }

    for (int i = 1; i <= M.getM(); i++) {
      b[i][1] = M.mat[i][M.getN()];
    }
    
    A = new Matrix(koef);
    B = new Matrix(b);
    sol = new Matrix(M.getN(), 1);
    genFreeVar();
  }


  public SPL(Matrix K, Matrix X) {
    A = new Matrix(K);
    B = new Matrix(X);
    BigDecimal[][] tmp = new BigDecimal[K.getM() + 1][K.getN() + X.getN() + 1];
    for (int i = 1; i <= K.getM(); i++) {
      for (int j = 1; j <= K.getN(); j++) {
        tmp[i][j] = A.mat[i][j];
      }
      tmp[i][K.getN() + 1] = B.mat[i][1];
    }
    M = new Matrix(tmp);
    sol = new Matrix(M.getN(), 1);
    genFreeVar();
  }
  
  public void solveCramer() {
    BigDecimal det = A.getDeterminantGJ();
    this.state = (det.compareTo(BigDecimal.ZERO) == 0) ? 0 : 1;
    this.sol = new Matrix(A.getM(), 1);
    try {
      for(int i=1;i<=A.getN();i++){
        Matrix dummy = new Matrix(A.getM(), A.getN());
        for(int j=1;j<=A.getM();j++){
          for(int k=1;k<=A.getN();k++){
            if(k==i){
              dummy.mat[j][k]=B.mat[j][1];
            } else {
              dummy.mat[j][k]=A.mat[j][k];
            }
          }
        }
        BigDecimal det2 = dummy.getDeterminantGJ();
        this.sol.mat[i][1] = det2.divide(det, 30, RoundingMode.HALF_UP);
      }
    } catch (ArrayIndexOutOfBoundsException e){
      System.out.println("Dimensi matriks tidak valid untuk metode cramer.");
    }
  }

  public static void genFreeVar() {
    if (free_var != null) return;
    
    char[] alphabet = new char[26];
    for (char cur = 'a'; cur <= 'z'; cur++) {
      alphabet[cur - 'a'] = cur;
    }

    free_var = new String[26 + (26*26)];
    for (int i = 0; i < alphabet.length; i++) {
      free_var[i] = Character.toString(alphabet[i]);
    }
    int idx = 26;
    for (int i = 0; i < alphabet.length; i++) {
      for (int j = 0; j < alphabet.length; j++) {
        free_var[idx] = Character.toString(alphabet[i]) + Character.toString(alphabet[j]);
        idx++;
      }
    }
  }

  public void solveGauss() {
    EF = new Matrix(M.getEchelon());
    state = this.getState();
    solvePar();
  }

  public void solveGaussJordan() {
    EF = new Matrix(M.getReducedEchelon());
    state = this.getState();
    solvePar();
  }

  public void solveInverse() {
    if (Matrix.inverse(A).mat != null) {
      sol = new Matrix((Matrix.inverse(A)).mult(B));
    } else {
      sol.mat = null;
    }
  }

  private int getState() {
    int ret_state;
    if (!hasSol()) {
      ret_state = 0;
    } else {
      if (isUnique()) {
        ret_state = 1;
      } else {
        ret_state = 2;
      }
    }
    return ret_state;
  }

  private boolean hasSol() {
    boolean bisa = true;
    for (int i = EF.getM(); i >= 1 && bisa; i--) {
      boolean isAllZero = true;
      for (int j = 1; j <= EF.getN() - 1 && isAllZero; j++) {
        if (!Util.isZero(EF.mat[i][j])) {
          isAllZero = false;
        }
      }
      if (isAllZero && !Util.isZero(EF.mat[i][EF.getN()])) {
        bisa = false;
      }
    }
    return bisa;
  }

  private boolean isUnique() {
    boolean unik = true;
    for (int i = 1; i <= Math.min(EF.getM(), EF.getN()) && unik; i++) {
      if (Util.isZero(EF.mat[i][i])) {
        unik = false;
      }
    }
    return unik;
  }

  private void solvePar() {
    int r=EF.getM(), c=EF.getN();
    boolean[] udh = new boolean[c];
    Matrix free_sol = new Matrix(c-1,c+c-1);

    for (int i=1;i<=r;i++){
      int lead = EF.leading(i);
      if (lead == -1) break;
      if (lead >= c) break;

      for (int j=lead+1;j<c;j++){
        free_sol.mat[lead][j] = EF.mat[i][j].negate();
      }
      free_sol.mat[lead][c+c-1] = EF.mat[i][c];
      udh[lead] = true;
    }

    int misal_cnt = 0;
    for (int i=1;i<c;i++){
      if (udh[i]) continue;

      free_sol.mat[i][c+misal_cnt] = BigDecimal.ONE;
      misal_cnt++;
    }

    for (int i=c-1;i>0;i--){
      for (int j=i+1;j<c;j++){
        BigDecimal fac;
        if (Util.isZero(free_sol.mat[i][j])) continue;
        
        fac = free_sol.mat[i][j];
        free_sol.mat[i][j] = BigDecimal.ZERO;
        free_sol.add(i, j, fac);
      }
    }

    free_solution = new Matrix(c-1,c);
    for (int i=1;i<c;i++){
      for (int j=1;j<=c;j++){
        free_solution.mat[i][j] = free_sol.mat[i][c+j-1];
      }
    }

    if (state == 1) {
      storeSol();
    }
  }

  public void showAug() {
    for (int i = 1; i <= this.M.getM(); i++) {
      for (int j = 1; j <= this.M.getN() - 1; j++) {
        System.out.printf("%.4f ", this.M.mat[i][j]);
      }
      System.out.printf("| %.4f", this.M.mat[i][this.M.getN()]);
      System.out.println();
    }
  }

  public void showAugFile(String filename) throws IOException {
    try {
      if (this.M.mat == null) {
        throw new NullPointerException();
      }
      String ret = "";
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      for (int i = 1; i <= this.M.getM(); i++) {
        for (int j = 1; j <= this.M.getN(); j++) {
          String result = Util.formatOutput(this.M.mat[i][j]);
          ret += result;
          if (j == this.M.getN()) {
            if (i < this.M.getM()) {
              ret += "\n";
            }
          } else {
            ret += " ";
            if (j == this.M.getN() - 1) {
              ret += "| ";
            }
          }
        }
      }
      writer.write(ret);
      writer.close();
    } catch (NullPointerException e) {
      System.out.println("Matriks tidak valid.");
    }
  }

  public void showEF() {
    for (int i = 1; i <= this.M.getM(); i++) {
      for (int j = 1; j <= this.M.getN() - 1; j++) {
        System.out.printf("%.4f ", this.EF.mat[i][j]);
      }
      System.out.printf("| %.4f", this.EF.mat[i][this.EF.getN()]);
      System.out.println();
    }
  }

  public void showEFFile(String filename) throws IOException {
    try {
      if (this.M.mat == null) {
        throw new NullPointerException();
      }
      String ret = "";
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      for (int i = 1; i <= this.EF.getM(); i++) {
        for (int j = 1; j <= this.EF.getN(); j++) {
          String result = Util.formatOutput(this.EF.mat[i][j]);
          ret += result;
          if (j == this.EF.getN()) {
            if (i < this.EF.getM()) {
              ret += "\n";
            }
          } else {
            ret += " ";
            if (j == this.EF.getN() - 1) {
              ret += "| ";
            }
          }
        }
      }
      writer.write(ret);
      writer.close();
    } catch (NullPointerException e) {
      System.out.println("Matriks tidak valid.");
    }
  }

  public void showSol(){ 
    if (state == 0) {
      System.out.println("Solusi tidak ada");
      return;
    }
    int r=free_solution.getM(), c=free_solution.getN();
    boolean first;
    for (int i=1;i<=r;i++){
      boolean nemu=false;
      System.out.print("X" + i + " =");
      first = true;
      for (int j=1;j<c;j++){
        if (Util.isZero(free_solution.mat[i][j])) continue;
        nemu = true;
        if (free_solution.mat[i][j].compareTo(BigDecimal.ZERO) < 0){
          if (free_solution.mat[i][j].compareTo(BigDecimal.ONE.negate()) == 0){
            System.out.print(" -");
            if (!first) {
              System.out.print(" ");
            }  
          } else{
            System.out.print(" -");
            if (!first) {
              System.out.print(" ");
            }  
            System.out.print(Util.formatOutputAbs(free_solution.mat[i][j]));
          }
        } else if (first){
          if (free_solution.mat[i][j].compareTo(BigDecimal.ONE) == 0){
            System.out.print(" ");  
          } else{
            System.out.print(" " + Util.formatOutputAbs(free_solution.mat[i][j]));
          }
        } else{
          if (free_solution.mat[i][j].compareTo(BigDecimal.ONE) == 0){
            System.out.print(" + ");  
          } else{
           System.out.print(" + " + Util.formatOutputAbs(free_solution.mat[i][j]));
          }
        }
        first = false;
        System.out.print(free_var[j-1]);
      }
      if (!Util.isZero(free_solution.mat[i][c])){
        nemu = true;
        if (free_solution.mat[i][c].compareTo(BigDecimal.ZERO) < 0) {
          System.out.print(" -");
          if (!first) {
            System.out.print(" ");
          }  
          System.out.print(Util.formatOutputAbs(free_solution.mat[i][c]));
        } else if (!first) {
          System.out.print(" + " + Util.formatOutputAbs(free_solution.mat[i][c]));
        } else System.out.print(" " + Util.formatOutputAbs(free_solution.mat[i][c]));
      }
      if (!nemu) System.out.print(" " + 0);
      System.out.println();
    }
  }

  public void storeSol(){ // state = 1
    int r = free_solution.getM(), c = free_solution.getN();
    sol = new Matrix(r, 1);

    for (int i=1;i<=r;i++){
      sol.mat[i][1] = free_solution.mat[i][c];
    }
  }

  public void showFile(String filename){
    try {
      PrintWriter pwriter = new PrintWriter(new FileWriter(filename));

      if (state == 0) {
        System.out.println("Solusi tidak ada");
        pwriter.close();
        return;
      }
      int r=free_solution.getM(), c=free_solution.getN();
      boolean first;
      for (int i=1;i<=r;i++){
        boolean nemu=false;
        pwriter.print("X" + i + " =");
        first = true;
        for (int j=1;j<c;j++){
          if (Util.isZero(free_solution.mat[i][j])) continue;
          nemu = true;
          if (free_solution.mat[i][j].compareTo(BigDecimal.ZERO) < 0){
            if (free_solution.mat[i][j].equals(BigDecimal.ONE.negate())){
              pwriter.print(" -");
              if (!first) {
                pwriter.print(" ");
              }  
            } else{
              pwriter.print(" -");
              if (!first) {
                pwriter.print(" ");
              }  
              pwriter.print(Util.formatOutputAbs(free_solution.mat[i][j]));
            }
          } else if (first){
            if (free_solution.mat[i][j].equals(BigDecimal.ONE)){
              pwriter.print(" ");  
            } else{
              pwriter.print(" " + Util.formatOutputAbs(free_solution.mat[i][j]));
            }
          } else{
            if (free_solution.mat[i][j].equals(BigDecimal.ONE)){
              pwriter.print(" + ");  
            } else{
             pwriter.print(" + " + Util.formatOutputAbs(free_solution.mat[i][j]));
            }
          }
          first = false;
          pwriter.print(free_var[j-1]);
        }
        if (!Util.isZero(free_solution.mat[i][c])){
          nemu = true;
          if (free_solution.mat[i][c].compareTo(BigDecimal.ZERO) < 0) {
            pwriter.print(" -");
            if (!first) {
              pwriter.print(" ");
            }  
            pwriter.print(Util.formatOutputAbs(free_solution.mat[i][c]));
          } else if (!first) {
            pwriter.print(" + " + Util.formatOutputAbs(free_solution.mat[i][c]));
          } else pwriter.print(" " + Util.formatOutputAbs(free_solution.mat[i][c]));
        }
        if (!nemu) pwriter.print(" " + 0);
        pwriter.println();
      }

      pwriter.close();
    } catch (NullPointerException e) {
      System.out.println("Matriks tidak valid.");
    } catch(IOException ex) {
      System.out.println(
          "File tidak dapat dibaca '" 
          + filename + "'");
    }
  }
}
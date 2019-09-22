package app;
import app.Matrix;
import java.util.*;

class SPL {
  private Matrix M; // Augmented matrix
  private Matrix A; // Coefficient matrix
  private Matrix B; // Kons matrix
  public Matrix EF;
  public Matrix free_solution;  // tempat solusi variabel bebas
  public Matrix sol;
  public static String[] free_var;
  private int state;
  private double EPS = 1e-9;

  /*
  0 : doesnt have sol
  1 : unique sol
  2 : many sol
  */
  public SPL(Matrix aug) { // Constructor from augmented matrix
    M = new Matrix(aug);
    EF = new Matrix(M.getEchelon());
    double[][] koef = new double[M.getM() + 1][M.getN()];
    double[][] b = new double[M.getM() + 1][2];
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
    double[][] tmp = new double[K.getM() + 1][K.getN() + X.getN() + 1];
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
    double det = A.getDeterminant();
    this.state = (det==0) ? 0 : 1;
    this.sol = new Matrix(A.getM(), 1);

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
      // dummy.show();
      double det2 = dummy.getDeterminant();
      this.sol.mat[i][1] = det2/det;
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
        if (Math.abs(EF.mat[i][j]) > EPS) {
          isAllZero = false;
        }
      }
      if (isAllZero && Math.abs(EF.mat[i][EF.getN()]) > EPS) {
        bisa = false;
      }
    }
    return bisa;
  }

  private boolean isUnique() {
    boolean unik = true;
    for (int i = 1; i <= Math.min(EF.getM(), EF.getN()) && unik; i++) {
      if (Math.abs(EF.mat[i][i]) <= EPS) {
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
        free_sol.mat[lead][j] = -EF.mat[i][j];
      }
      free_sol.mat[lead][c+c-1] = EF.mat[i][c];
      udh[lead] = true;
    }

    int misal_cnt = 0;
    for (int i=1;i<c;i++){
      if (udh[i]) continue;

      free_sol.mat[i][c+misal_cnt] = 1;
      misal_cnt++;
    }

    for (int i=c-1;i>0;i--){
      for (int j=i+1;j<c;j++){
        double fac;
        if (Math.abs(free_sol.mat[i][j]) < EPS) continue;
        
        fac = free_sol.mat[i][j];
        free_sol.mat[i][j] = 0;
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

  public void showSol(){ 
    if (state == 0) {
      System.out.println("Solusi tidak ada");
      return;
    }
    int r=free_solution.getM(), c=free_solution.getN();
    boolean first;
    for (int i=1;i<=r;i++){
      System.out.print("X" + i + " =");
      first = true;
      for (int j=1;j<c;j++){
        if (Math.abs(free_solution.mat[i][j])<EPS) continue;

        if (free_solution.mat[i][j]<0){
          if (free_solution.mat[i][j]==-1){
            System.out.print(" -");
            if (!first) {
              System.out.print(" ");
            }  
          } else{
            System.out.print(" -");
            if (!first) {
              System.out.print(" ");
            }  
            System.out.print(Math.abs(free_solution.mat[i][j]));
          }
        } else if (first){
          if (free_solution.mat[i][j]==1){
            System.out.print(" ");  
          } else{
            System.out.print(" " + free_solution.mat[i][j]);
          }
        } else{
          if (free_solution.mat[i][j]==1){
            System.out.print(" + ");  
          } else{
           System.out.print(" + " + free_solution.mat[i][j]);
          }
        }
        first = false;
        System.out.print(free_var[j-1]);
      }
      if (Math.abs(free_solution.mat[i][c])>EPS){
        if (free_solution.mat[i][c]<0) {
          System.out.print(" -");
          if (!first) {
            System.out.print(" ");
          }  
          System.out.print(Math.abs(free_solution.mat[i][c]));
        } else if (!first) {
          System.out.print(" + " + free_solution.mat[i][c]);
        } else System.out.print(" " + free_solution.mat[i][c]);
      }
      System.out.println();
    }
  }

  public void storeSol(){ // state = 1
    int r=free_solution.getM(), c=free_solution.getN();
    sol = new Matrix(r, 1);

    for (int i=1;i<=r;i++){
      sol.mat[i][1] = 0;
      for (int j=1;j<=c;j++){
        if (Math.abs(free_solution.mat[i][j])<EPS) continue;
        sol.mat[i][1] += free_solution.mat[i][j];
      }
    }
  }
}
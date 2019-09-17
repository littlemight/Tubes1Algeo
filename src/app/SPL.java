package app;
import app.Matrix;

class SPL {
  private Matrix M;
  private Matrix EF;
  public double[] sol;
  public String[] free_var;
  public String str_sol;
  private int state;
  private double EPS = 1e-9;
  /*
  0 : doesnt have sol
  1 : unique sol
  2 : many sol
  */
  public SPL(Matrix aug) { // Constructor from a augmented matrix
    M = new Matrix(aug);
    EF = new Matrix(M.getEchelonG());
    this.makeSol();
  }
  
  public void genFreeVar() {
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

  private void makeSol() {
    state = this.getState();
    if (state == 0) {
      this.str_sol = "Solusi tidak ada";
    } else if (state == 1) {
      reverseSubstitute();
    } else {

    }
  }

  private int getState() {
    int state;
    if (!hasSol()) {
      state = 0;
    } else {
      if (isUnique()) {
        state = 1;
      } else {
        state = 2;
      }
    }
    return state;
  }

  private boolean hasSol() {
    boolean bisa = true;
    double[] x =  new double[EF.getM() + 1];
    for (int i = EF.getM(); i >= 1; i--) {
      double lhs = 0;
      for (int j = i + 1; j <= EF.getN(); j++) {
        lhs += EF.mat[i][j] * x[j];
      }
      if (Math.abs(EF.mat[i][i]) > EPS) {
        x[i] = (EF.mat[i][EF.getN()] - lhs) / (EF.mat[i][i]);
      } else if (Math.abs(EF.mat[i][EF.getN()] - lhs) > EPS) {
        bisa = false;
      }
    }
    return bisa;
  }

  private boolean isUnique() {
    boolean unik = true;
    for (int i = 1; i <= Math.min(EF.getM(), EF.getN()) && unik; i++) {
      if (EF.mat[i][i] != 0) {
        unik = false;
      }
    }
    return unik;
  }

  private void reverseSubstitute() { // prekondisi, state == 1
    sol =  new double[EF.getM() + 1];
    for (int i = EF.getM(); i >= 1; i--) {
      double lhs = 0;
      for (int j = i + 1; j <= EF.getN(); j++) {
        lhs += EF.mat[i][j] * sol[j];
      }
        sol[i] = (EF.mat[i][EF.getN()] - lhs) / (EF.mat[i][i]);
    }
  }
}
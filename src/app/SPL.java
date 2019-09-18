package app;
import app.Matrix;

class SPL {
  private Matrix M;
  private Matrix EF;
  public Matrix free_solution;
  public double[] sol;
  public static String[] free_var;
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
    genFreeVar();

    free_solution.show();
  }
  
  public static void genFreeVar() {
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
      solvePar();
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

  private void SolvePar() {
    int r=EF.getM(), c=EF.getN();
    boolean[] udh = new boolean[c+2];
    Matrix free_sol = new Matrix(c,c+c+1);

    for (int i=1;i<=r;i++){
      int lead = EF.leading(i);
      if (lead == -1) break;
      if (lead > c) break;

      for (int j=lead+1;j<=c;j++){
        free_sol.mat[lead][j] = -EF.mat[i][j];
      }
      free_sol.mat[lead][c+c+1] = EF.mat[i][c];
      udh[lead] = 1;
    }

    int misal_cnt = 0;
    for (int i=1;i<=c;i++){
      if (udh[i]) continue;

      free_sol.mat[i][c+misal_cnt+1] = 1;
      misal_cnt++;
    }

    for (int i=c;i>0;i--){
      for (int j=i+1;j<=c;j++){
        double fac;
        if (Math.abs(free_sol.mat[i][j]) < EPS) continue;
        
        fac = free_sol.mat[i][j];
        free_sol.mat[i][j] = 0;
        free_sol.add(i, j, fac);
      }
    }

    free_solution = new Matrix(c,c+1);
    for (int i=1;i<=c;i++){
      for (int j=1;j<=c+1;j++){
        free_solution.mat[i][j] = free_sol.mat[i][c+j];
      }
    }

  }
}
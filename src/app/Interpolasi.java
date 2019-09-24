package app;
import java.util.*;
import app.SPL;

class Interpolasi {
  private static final double EPS = 1e-12;
  private SPL solver;
  String persamaan;
  double mn_x, mx_x;    
  
  Interpolasi (double[][] x, double[][] y) {
     Matrix B = new Matrix(y);
     double[][] kons = new double[y.length][x.length];
     for (int i = 1; i <= y.length - 1; i++) {
       for (int j = 1; j <= x.length - 1; j++) {
         kons[i][j] = Math.pow(x[i][1], j - 1);
       }
     }
     Matrix A = new Matrix(kons);
     System.out.println("LHS");
     A.show();
     System.out.println();

     System.out.println("RHS");
     B.show();
     System.out.println();
     
     solver = new SPL(A, B);
  }

  public static Interpolasi readKB() {
    Scanner in = new Scanner(System.in);
    System.out.print("Masukkan banyaknya titik: ");
    int n = in.nextInt();
    HashSet<Double> cekX = new HashSet<Double>();

    boolean valid = true;

    double[] in_x = new double[n + 1];
    double[] in_y = new double[n + 1];
    int id = 1;
    for (int i = 1; i <= n; i++) {
      double tm_x, tm_y;
      tm_x = in.nextDouble();
      tm_y = in.nextDouble();
      if (cekX.contains(tm_x)) {
        valid=false;
        break;
      } else {
        cekX.add(tm_x);
        in_x[id] = tm_x;
        in_y[id] = tm_y;
        id++;
      }
    }
    if(valid){
      id--;

      double[][] fil_x = new double[id + 1][2];
      double[][] fil_y = new double[id + 1][2];
      for (int i = 1; i <= id; i++) {
        fil_x[i][1] = in_x[i];
        fil_y[i][1] = in_y[i];
      }

      Interpolasi ret = new Interpolasi(fil_x, fil_y);
      return ret;
    } else {
      return null;
    }
  }

  public static Interpolasi fread(String filename){
    Matrix temp = new Matrix();

    temp = Matrix.readFile(filename);
    Interpolasi ret;

    if (temp != null){
      int n = temp.getM(), id = 1;
      HashSet<Double> cekX = new HashSet<Double>();

      double[] in_x = new double[n + 1];
      double[] in_y = new double[n + 1];
      for (int i = 1; i <= n; i++) {
        double tm_x, tm_y;
        tm_x = temp.mat[i][1];
        tm_y = temp.mat[i][2];
        if (cekX.contains(tm_x)) {
          System.out.println("Titik tidak valid.");
        } else {
          cekX.add(tm_x);
          in_x[id] = tm_x;
          in_y[id] = tm_y;
          id++;
        }
      }
      id--;

      double[][] fil_x = new double[id + 1][2];
      double[][] fil_y = new double[id + 1][2];
      for (int i = 1; i <= id; i++) {
        fil_x[i][1] = in_x[i];
        fil_y[i][1] = in_y[i];
      }

      ret = new Interpolasi(fil_x, fil_y);
      return ret;
    } else {  // input tidak valid;
      return null;  
    }
  }

  public void solveInterGauss() {
    solver.solveGauss();
    // solver.sol.show();
    makePersamaan();
  }

  public void solveInterGaussJordan() {
    solver.solveGaussJordan();
    // solver.sol.show();
    makePersamaan();
  }

  public void solveInterCramer() {
    solver.solveCramer();
    // solver.sol.show();  
    makePersamaan();
  }

  public void solveInterInverse() {
    solver.solveInverse();
    // solver.sol.show();
    makePersamaan();
  }

  public void makePersamaan() {
    persamaan = "p(x) =";
    boolean first = true;
    for (int i = 1; i <= solver.sol.getM(); i++) {
      if (Math.abs(solver.sol.mat[i][1]) < EPS) continue;
      double cur = solver.sol.mat[i][1];
      if (Math.abs(cur) <= 1e-4) continue;

      if (first) {
        first = false;
        if (cur > 0) {
          persamaan += " ";
          persamaan += formatOutput(cur);
        } else {
          persamaan += " -";
          persamaan += formatOutput(cur);
        }
      } else {
        if (cur > 0) {
          persamaan += " + ";
        } else {
          persamaan += " - ";
        }
        persamaan += formatOutput(cur);
      }

      if (i > 1) {
        persamaan += "x";
        if (i > 2) {
          persamaan += "^";
          persamaan += (i - 1);
        }
      }
    }
  }

  public static String formatOutput(double val) { // normalize absolute value of val
    String ret = "";
    if (Math.abs(val) > EPS) {
      ret += String.format("%.4f", Math.abs(val));
    } 
    return ret;
  }

  public void showPersamaan() {
    System.out.println(persamaan);
  }

  public double getY(double x) {
    double ret = 0;
    for (int i = 1; i <= solver.sol.getM(); i++) {
      ret += Math.pow(x, i - 1) * solver.sol.mat[i][1];
    }
    return ret;
  }

  public static void main(String[] args) {
    try {
      Interpolasi sol = Interpolasi.readKB();
      System.out.println();

      System.out.println("GAUSS");
      sol.solveInterGauss();
      sol.showPersamaan();
      System.out.println();

      System.out.println("ECHELON FORM:");
      sol.solver.EF.show();
      sol.showPersamaan();
      System.out.println();

      System.out.println("GAUSS JORDAN");
      sol.solveInterGaussJordan();
      sol.showPersamaan();
      System.out.println();

      System.out.println("CRAMER");
      sol.solveInterCramer();
      sol.showPersamaan();
      System.out.println();

      // System.out.println("INVERSE");
      // sol.solveInterInverse();
      // sol.showPersamaan();
      // System.out.println();

      System.out.println(1971 + " " + sol.getY(1971));
    } catch (NullPointerException e){
      System.out.println("Input tidak valid.");
    }
  }
}
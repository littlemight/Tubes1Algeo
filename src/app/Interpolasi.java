package app;
import java.util.*;

class Interpolasi {
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

    double[] in_x = new double[n + 1];
    double[] in_y = new double[n + 1];
    int id = 1;
    for (int i = 1; i <= n; i++) {
      double tm_x, tm_y;
      tm_x = in.nextDouble();
      tm_y = in.nextDouble();
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

    Interpolasi ret = new Interpolasi(fil_x, fil_y);
    return ret;
  }

  public void solveInterGauss() {
    solver.solveGauss();
    solver.sol.show();
  }

  public void solveInterGaussJordan() {
    solver.solveGaussJordan();
    solver.sol.show();
  }

  public void solveInterCramer() {
    solver.solveCramer();
    solver.sol.show();  
  }

  public void solveInterInverse() {
    solver.solveInverse();
    solver.sol.show();
  }

  public void getPersamaan() {
    persamaan = "";
    boolean first = true;
    
  }
  public static void main(String[] args) {
    Interpolasi sol = Interpolasi.readKB();
    System.out.println();

    System.out.println("GAUSS");
    sol.solveInterGauss();
    System.out.println();

    System.out.println("ECHELON FORM:");
    sol.solver.EF.show();
    System.out.println();

    System.out.println("GAUSS JORDAN");
    sol.solveInterGaussJordan();
    System.out.println();

    System.out.println("CRAMER");
    sol.solveInterCramer();
    System.out.println();

    System.out.println("INVERSE");
    sol.solveInterInverse();
    System.out.println();
  }
}
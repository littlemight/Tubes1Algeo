package app;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import app.SPL;

class Interpolasi {
  private SPL solver;
  String persamaan;
  BigDecimal[] x, y;
  BigDecimal x_min, x_max;    
  HashSet<BigDecimal> recX;
  
  Interpolasi (BigDecimal[][] x, BigDecimal[][] y) {
     Matrix B = new Matrix(y);
     BigDecimal[][] kons = new BigDecimal[y.length][x.length];
     this.x = new BigDecimal[x.length];
     this.y = new BigDecimal[y.length];

     recX = new HashSet<BigDecimal>();

     for (int i = 1; i <= this.x.length - 1; i++) {
       this.x[i] = x[i][1];
       recX.add(this.x[i]);
       if (i == 1) {
         x_min = this.x[i];
         x_max = this.y[i];
       } else {
         x_min = x_min.min(this.x[i]);
         x_max = x_max.max(this.x[i]);
       }

       this.y[i] = y[i][1];
     }

     for (int i = 1; i <= y.length - 1; i++) {
       for (int j = 1; j <= x.length - 1; j++) {
         kons[i][j] = Util.fastPow(x[i][1], j - 1);
       }
     }

     Matrix A = new Matrix(kons);
     solver = new SPL(A, B);
  }

  public static Interpolasi readKB() {
    Scanner in = new Scanner(System.in);
    System.out.print("Masukkan banyaknya titik: ");
    int n = in.nextInt();
    HashSet<BigDecimal> cekX = new HashSet<BigDecimal>();

    boolean valid = true;

    BigDecimal[] in_x = new BigDecimal[n + 1];
    BigDecimal[] in_y = new BigDecimal[n + 1];
    int id = 1;
    for (int i = 1; i <= n; i++) {
      double tm_x, tm_y;
      tm_x = in.nextDouble();
      tm_y = in.nextDouble();
      if (cekX.contains(BigDecimal.valueOf(tm_x))) {
        valid=false;
        break;
      } else {
        cekX.add(BigDecimal.valueOf(tm_x));
        in_x[id] = BigDecimal.valueOf(tm_x);
        in_y[id] = BigDecimal.valueOf(tm_y);
        id++;
      }
    }

    if(valid){
      id--;

      BigDecimal[][] fil_x = new BigDecimal[id + 1][2];
      BigDecimal[][] fil_y = new BigDecimal[id + 1][2];
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

  public static Interpolasi readFile(String filename){
    Matrix temp = new Matrix();

    temp = Matrix.readFile(filename);
    Interpolasi ret;

    if (temp != null){
      int n = temp.getM(), id = 1;
      HashSet<BigDecimal> cekX = new HashSet<BigDecimal>();

      BigDecimal[] in_x = new BigDecimal[n + 1];
      BigDecimal[] in_y = new BigDecimal[n + 1];
      for (int i = 1; i <= n; i++) {
        BigDecimal tm_x, tm_y;
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

      BigDecimal[][] fil_x = new BigDecimal[id + 1][2];
      BigDecimal[][] fil_y = new BigDecimal[id + 1][2];
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
    makePersamaan();
  }

  public void solveInterGaussJordan() {
    solver.solveGaussJordan();
    makePersamaan();
  }

  public void solveInterCramer() {
    solver.solveCramer();
    makePersamaan();
  }

  public void solveInterInverse() {
    solver.solveInverse();
    makePersamaan();
  }

  public void makePersamaan() {
    persamaan = "p(x) =";
    boolean first = true;
    for (int i = 1; i <= solver.sol.getM(); i++) {
      if (Util.isZero(solver.sol.mat[i][1])) continue;
      BigDecimal cur = solver.sol.mat[i][1];

      if (first) {
        first = false;
        if (cur.compareTo(BigDecimal.ZERO) > 0) {
          persamaan += " ";
          persamaan += Util.formatOutputAbs(cur);
        } else {
          persamaan += " -";
          persamaan += Util.formatOutputAbs(cur);
        }
      } else {
        if (cur.compareTo(BigDecimal.ZERO) > 0) {
          persamaan += " + ";
        } else {
          persamaan += " - ";
        }
        persamaan += Util.formatOutputAbs(cur);
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


  public void showPersamaan() {
    System.out.println(persamaan);
  }

  public BigDecimal getY(double x) {
    BigDecimal ret = BigDecimal.ZERO;
    BigDecimal xdec = BigDecimal.valueOf(x);
    for (int i = 1; i <= solver.sol.getM(); i++) {
      ret = ret.add(Util.fastPow(xdec, i - 1).multiply(solver.sol.mat[i][1]));
    }
    return ret;
  }

  public BigDecimal getVal(double x) {
    BigDecimal ret = BigDecimal.ZERO;
    return ret;
  }

  public static void main(String[] args) {
    try {
      Scanner in = new Scanner(System.in);
      System.out.println("Masukkan nama file:");
      String str = in.nextLine();
      System.out.println(str);
      Interpolasi sol = Interpolasi.readFile("../test/" + str + ".txt");
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

      System.out.println("Masukkan X: ");
      for (int i = 1; i <= sol.x.length - 1; i++) {
        System.out.println(sol.x[i] + " " + sol.getY(sol.x[i].doubleValue()));
      }
    } catch (NullPointerException e){
      System.out.println("Input tidak valid.");
    }
  }
}
package app;
import java.util.*;
import java.math.BigDecimal;
import java.io.*;
import app.SPL;

/**
 * class Interpolasi untuk operasi dan primitif menyelesaikan permasalahan interpolasi
 */
class Interpolasi {
  SPL solver;
  String persamaan;
  BigDecimal[] x, y;
  BigDecimal x_min, x_max;    
  HashSet<BigDecimal> recX;
  
  /**
   * Konstruktor dari matriks masukan titik x dan y
   * @param x
   * @param y
   */
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
         this.x_min = this.x[i];
         this.x_max = this.x[i];
       } else {
         this.x_min = this.x_min.min(this.x[i]);
         this.x_max = this.x_max.max(this.x[i]);
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

  /**
   * Primitif untuk membaca masukan titik dari keyboard
   * @return
   */
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

  /**
   * Primitif untuk membaca masukan dari file input bernama "filename"
   * @param filename
   * @return
   */
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

  /**
   * Primitif untuk menyelesaikan interpolasi, menggunakan operasi Gauss
   */
  public void solveInterGauss() {
    solver.solveGauss();
    makePersamaan();
  }

  /**
   * Primitif untuk menyelesaikan interpolasi, menggunakan operasi Gauss-Jordan
   */
  public void solveInterGaussJordan() {
    solver.solveGaussJordan();
    makePersamaan();
  }

  /**
   * Primitif untuk menyelesaikan interpolasi, menggunakan operasi Cramer
   */
  public void solveInterCramer() {
    solver.solveCramer();
    makePersamaan();
  }


  /**
   * Primitif untuk menyelesaikan interpolasi, menggunakan operasi matriks balikan
   */
  public void solveInterInverse() {
    solver.solveInverse();
    makePersamaan();
  }

  /**
   * Primitif untuk membuat persamaan interpolasi dalam bentuk string
   */
  public void makePersamaan() {
    persamaan = "p(x) =";
    boolean first = true;
    for (int i = 1; i <= solver.sol.getM(); i++) {
      if (Util.isZero(solver.sol.mat[i][1]) || Util.formatOutputAbsLong(solver.sol.mat[i][1]) == "0.00000000") continue;
      BigDecimal cur = solver.sol.mat[i][1];

      if (first) {
        first = false;
        if (cur.compareTo(BigDecimal.ZERO) > 0) {
          persamaan += " ";
          persamaan += Util.formatOutputAbsLong(cur);
        } else {
          persamaan += " -";
          persamaan += Util.formatOutputAbsLong(cur);
        }
      } else {
        if (cur.compareTo(BigDecimal.ZERO) > 0) {
          persamaan += " + ";
        } else {
          persamaan += " - ";
        }
        persamaan += Util.formatOutputAbsLong(cur);
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

  /**
   * Primitif untuk menampilkan persamaan interpolasi
   */
  public void showPersamaan() {
    System.out.println(persamaan);
  }
  
  /**
   * Primitif untuk menyimpan string persamaan interpolasi ke file bernama "filename"
   * @param filename
   * @throws IOException
   */
  public void showPersamaanFile(String filename) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    writer.write(persamaan);
    writer.close();
  }

  /**
   * Primitif untuk mendapatkan nilai y dari x berdasarkan fungsi interpolasi
   * @param x
   * @return
   */
  public BigDecimal getY(double x) {
    BigDecimal ret = BigDecimal.ZERO;
    BigDecimal xdec = BigDecimal.valueOf(x);
    for (int i = 1; i <= solver.sol.getM(); i++) {
      ret = ret.add(Util.fastPow(xdec, i - 1).multiply(solver.sol.mat[i][1]));
    }
    return ret;
  }

  /**
   * Primitif untuk memeriksa apakah nilai x berada di rentang interpolasi untuk absis titik-titik yang menjadi acuan interpolasi sebelumnya
   * @param x
   * @return
   */
  public boolean isInRange(double x) {
    BigDecimal xdec = BigDecimal.valueOf(x);
    return (xdec.compareTo(x_min) >= 0 && xdec.compareTo(x_max) <= 0);
  }

  /**
   * Primitif untuk mendapatkan string yang merupakan nilai y untuk masukan x sesuai fungsi interpolasi yang sudah didapat
   * @param x
   */
  public void queryY(double x) { // x sudah di range xmin xmax
    System.out.println("y = " + Util.formatOutput(this.getY(x)));
  }

  /**
   * Primitif untuk memberikan peringatan jika titik x yang ditanya diluar rentang interpolasi untuk absis-absis titik yang menjadi acuan interpolasi 
   */
  public void warnX() {
    System.out.println("! Titik di luar selang interpolasi [" + Util.formatOutput(this.x_min) + ", " + Util.formatOutput(this.x_max) + "], interpolasi mungkin tidak akurat.");
  }
}
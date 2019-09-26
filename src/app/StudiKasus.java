package app;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

class StudiKasus {
  static Matrix spl1_1, spl1_2, spl1_3;
  static Matrix spl2_1, spl2_2, spl2_i, spl2_ii;
  static Matrix det3_5, det3_10;
  static Matrix spl4_rang;
  static Matrix[] hilbert;
  static Interpolasi inter_penduduk;
  static Interpolasi inter_pendudukEx;
  static Interpolasi inter_seder;

  public static void initKasus() {
    // 1. SPL
    spl1_1 = new Matrix(Matrix.readFile("../test/1_" + 1 + ".txt"));
    spl1_2 = new Matrix(Matrix.readFile("../test/1_" + 2 + ".txt"));
    spl1_3 = new Matrix(Matrix.readFile("../test/1_" + 3 + ".txt"));

    hilbert = new Matrix[11];
    for (int i = 1; i <= 10; i++) {
      hilbert[i] = new Matrix(Matrix.readFile("../test/1_H" + i + ".txt"));
    }

    // 2. SPL
    spl2_1 = new Matrix(Matrix.readFile("../test/1_" + 1 + ".txt"));
    spl2_2 = new Matrix(Matrix.readFile("../test/1_" + 2 + ".txt"));
    spl2_i = new Matrix(Matrix.readFile("../test/2_i.txt"));
    spl2_ii = new Matrix(Matrix.readFile("../test/2_ii.txt"));

    // 3. Determinan
    det3_5 = new Matrix(Matrix.readFile("../test/3_5x5.txt"));
    det3_10 = new Matrix(Matrix.readFile("../test/3_10x10.txt"));

    // 4. SPL - Rangkaian
    spl4_rang = new Matrix(Matrix.readFile("../test/4_Rangkaian.txt"));

    // 5. Interpolasi - Penduduk
    inter_penduduk = Interpolasi.readFile("../test/5_Penduduk.txt");
    inter_pendudukEx = Interpolasi.readFile("../test/5_PendudukE6.txt");
  }

  public static void initSeder(int n) { // n = derajat interpolasi yang diinginkan
    BigDecimal x_lo = BigDecimal.ZERO;
    BigDecimal x_hi = BigDecimal.valueOf(2);
    BigDecimal two = x_hi.subtract(x_lo);
    BigDecimal x_cur = x_lo;
    BigDecimal[][] x, y;
    x = new BigDecimal[n + 2][2];
    y = new BigDecimal[n + 2][2];
    for (int i = 0; i <= n; i++) {
      x[i + 1][1] = x_cur;
      y[i + 1][1] = func(x[i + 1][1]);
      x_cur = two.multiply(BigDecimal.valueOf(i + 1)).divide(BigDecimal.valueOf(n), 30, RoundingMode.HALF_UP);
    }

    for (int i = 1; i <= x.length - 1; i++) {
      System.out.println(x[i][1] + " " + y[i][1]);
    }
    inter_seder = new Interpolasi(x, y);
  }

  public static BigDecimal func(BigDecimal x) {
    BigDecimal ret = BigDecimal.ZERO;
    MathContext mc = new MathContext(30);
    ret = (Util.fastPow(x, 2).add(x.sqrt(mc)));
    ret = ret.divide(BigDecimal.valueOf(Math.exp(x.doubleValue())).add(x), 30, RoundingMode.HALF_UP);
    if (Util.isZero(ret)) {
      ret = BigDecimal.ZERO;
    }
    return ret;
  }

  public static void main(String[] args) {
    // BigDecimal x = BigDecimal.ZERO;
    // Scanner in = new Scanner(System.in);
    // BigDecimal two = BigDecimal.valueOf(2);
    // int n = in.nextInt();
    // for (int i = 0; i <= n; i++) {
    //   BigDecimal y = func(x);
    //   // System.out.println(x + " " + y);
    //   x = two.multiply(BigDecimal.valueOf(i + 1)).divide(BigDecimal.valueOf(n), 30, RoundingMode.HALF_UP);
    // }

    // System.out.println("POINTS:");
    // initSeder(n);
    // inter_seder.solveInterGaussJordan();
    // inter_seder.showPersamaan();
    initKasus();
    // for (int i = 1; i <= n + 1; i++) {
    //   System.out.println(inter_seder.x[i] + " " + inter_seder.getY(inter_seder.x[i].doubleValue()));
    // }
  }
}
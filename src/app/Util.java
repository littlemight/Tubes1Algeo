package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * class Util berguna untuk menampung method-method pembantu untuk menjalankan program
 */
class Util { 
  private static BigDecimal EPS = BigDecimal.valueOf(1e-12);
  public static final int divScale = 50;

  /**
   * untuk format string
   * @param val
   * @return string setelah di format
   */
  public static String formatOutputAbsLong(BigDecimal val) { // normalize absolute value of val
    String ret = "";
    if (!isZero(val)) {
      ret += val.abs().setScale(8, RoundingMode.HALF_UP).toString();
    } 
    return ret;
  }

  /**
   * untuk format string
   * @param val
   * @return string setelah di format
   */
  public static String formatOutputAbs(BigDecimal val) { // normalize absolute value of val
    String ret = "";
    if (!isZero(val)) {
      ret += val.abs().setScale(2, RoundingMode.HALF_UP).toString();
    } 
    return ret;
  }

  /**
   * untuk format string
   * @param val
   * @return string setelah di format
   */
  public static String formatOutputLong(BigDecimal val) { // normalize value of val
    String ret = "";
    if (!isZero(val)) {
      if (val.compareTo(BigDecimal.ZERO) < 0) ret += "-";
      ret += formatOutputAbsLong(val);
    } else ret += "0.00000000";
    return ret;
  }

  /**
   * untuk format string
   * @param val
   * @return string setelah di format
   */
  public static String formatOutput(BigDecimal val) { // normalize value of val
    String ret = "";
    if (!isZero(val)) {
      if (val.compareTo(BigDecimal.ZERO) < 0) ret += "-";
      ret += formatOutputAbs(val);
    } else ret += "0.00";
    return ret;
  }

  /**
   * fast binary exponentiation pada BigDecimal
   * @param val
   * @param p 
   * @return nilai setelah di pangkatkan
   */
  public static BigDecimal fastPow(BigDecimal val, int p) {
    if (p == 0) return BigDecimal.ONE;
    if (p == 1) return val;
    BigDecimal ret = fastPow(val, p / 2);
    ret = ret.multiply(ret);
    if (p % 2 == 1) ret = ret.multiply(val);
    return ret; 
  }

  /**
   * mengecek apabila BigDecimal mendekati 0
   * @param val
   * @return menghasilkan true atau false
   */
  public static boolean isZero(BigDecimal val) {
    return (val.abs().compareTo(EPS) < 0);
  }

  /**
   * menyimpan BigDecimal pada file
   * @param val
   * @param filename 
   * @throws IOException
   */
  public static void showBD(BigDecimal val, String filename) throws IOException {
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      writer.write(Util.formatOutputLong(val));
      writer.close();
  }
}
package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Util { 
  private static BigDecimal EPS = BigDecimal.valueOf(1e-12);

  public static String formatOutputAbsLong(BigDecimal val) { // normalize absolute value of val
    String ret = "";
    if (!isZero(val)) {
      ret += val.abs().setScale(8, RoundingMode.HALF_UP).toString();
    } 
    return ret;
  }

  public static String formatOutputAbs(BigDecimal val) { // normalize absolute value of val
    String ret = "";
    if (!isZero(val)) {
      ret += val.abs().setScale(2, RoundingMode.HALF_UP).toString();
    } 
    return ret;
  }

  public static String formatOutputLong(BigDecimal val) { // normalize value of val
    String ret = "";
    if (!isZero(val)) {
      if (val.compareTo(BigDecimal.ZERO) < 0) ret += "-";
      ret += formatOutputAbs(val);
    } else ret += "0.00000000";
    return ret;
  }

  public static String formatOutput(BigDecimal val) { // normalize value of val
    String ret = "";
    if (!isZero(val)) {
      if (val.compareTo(BigDecimal.ZERO) < 0) ret += "-";
      ret += formatOutputAbs(val);
    } else ret += "0.0000";
    return ret;
  }

  public static BigDecimal fastPow(BigDecimal val, int p) {
    if (p == 0) return BigDecimal.ONE;
    if (p == 1) return val;
    BigDecimal ret = fastPow(val, p / 2);
    ret = ret.multiply(ret);
    if (p % 2 == 1) ret = ret.multiply(val);
    return ret; 
  }

  public static boolean isZero(BigDecimal val) {
    return (val.abs().compareTo(EPS) < 0);
  }
}
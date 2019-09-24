package app;

class Util { 
  private static double EPS = 1e-4;

  public static String formatOutputAbs(double val) { // normalize absolute value of val
    String ret = "";
    if (Math.abs(val) > EPS) {
      // double roun = fastPow(10, 4); 
      // ret += String.format("%.4f", Math.round(Math.abs(val)*roun) / roun);
      ret += String.format("%.4f", Math.abs(val));
    } 
    return ret;
  }

  public static String formatOutput(double val) { // normalize absolute value of val
    String ret = "";
    if (Math.abs(val) > EPS) {
      if (val > 0) ret += "+";
      else ret += "-";
      ret += formatOutputAbs(val);
    } 
    return ret;
  }

  public static double fastPow(double val, int p) {
    if (p == 0) return 1;
    if (p == 1) return val;
    double ret = fastPow(val, p / 2);
    ret *= ret;
    if (p % 2 == 1) ret *= val;
    return ret; 
  }

  public static boolean isZero(double val) {
    return (Math.abs(val) < EPS);
  }

  public static void main(String[] args) {
    System.out.println(Util.fastPow(2,5));
  }
}
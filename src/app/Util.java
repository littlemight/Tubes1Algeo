package app;

class Util { 
  private static double EPS = 1e-4;

  public static String formatOutput(double val) { // normalize absolute value of val
    String ret = "";
    if (Math.abs(val) > EPS) {
      ret += String.format("%.4f", Math.abs(val));
    } 
    return ret;
  }

  public double fastPow(double val, int p) {
    if (p == 0) return 1;
    if (p == 1) return val;
    double ret = fastPow(val, p / 2);
    ret *= ret;
    if (p % 2 == 1) ret *= val;
    return ret; 
  }
  
}
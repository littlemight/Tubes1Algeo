import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    double[][] ar = new double[n + 1][n + 2];
    for (int x = 1; x <= n; x++) { 
      for (int i = 1; i <= x; i++) {
        for (int j = 1; j <= x; j++) {
          ar[i][j] = 1.0 / (i + j - 1);
        }
        ar[i][x + 1] = (i == 1) ? 1 : 0;
      }
      in.close();

      for (int i = 1; i <= x; i++) {
        for (int j = 1; j <= x + 1; j++) {
          System.out.printf("%f ", ar[i][j]);
        }
        System.out.println();
      }
      System.out.println();
      BufferedWriter writer = new BufferedWriter(new FileWriter("./1SPLHilbert" + x + ".txt"));
      String out = "";
      for (int i = 1; i <= x; i++) {
        for (int j = 1; j <= x + 1; j++) {
          out += Double.toString(ar[i][j]);
          if (j <= x) out += " "; 
        }
        if (i < x) out += "\n";
      }
      writer.write(out);
      writer.close();
    }
  }
}
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

  public static void read(){
    int line_cnt=0;
    String filename = "./1SPL1.txt", line; // untuk ngetes
    String[] arr = new String[101];
  //  Scanner cin = new Scanner(System.in);
  //  filename = cin.next();  // input dikasih .txt
    double[][] res;
    Matrix mat;

    try {
      FileReader filetoread = new FileReader(filename);

      BufferedReader buffer = new BufferedReader(filetoread);

      while ((line = buffer.readLine()) != null){
        line_cnt++;
        arr[line_cnt] = line;
      }

      res = parse(arr,line_cnt);
      mat = new Matrix(res);
      mat.show();

      buffer.close();
    }
    catch(FileNotFoundException ex) {
      System.out.println(
          "Unable to open file '" + 
          filename + "'");                
    }
    catch(IOException ex) {
      System.out.println(
          "Error reading file '" 
          + filename + "'");                  
      // Or we could just do this: 
      // ex.printStackTrace();
    }

  }

  public static double[][] parse(String[] s, int n){
    double[][] res;

    if (n==0) return new double[0][0];

    int m=0;
   
    String[] temp;
    temp = s[1].split(" ");
    m = temp.length;
    res = new double[n+1][m+1];
    for (int j=1;j<=m;j++){
      res[1][j] = Double.parseDouble(temp[j-1]);
    }

    

    for (int i=2;i<=n;i++){
      temp = s[i].split(" ");
      for (int j=1;j<=m;j++){
        res[i][j] = Double.parseDouble(temp[j-1]);
      }
    }

    return res;
  }
}
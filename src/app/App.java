package app;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        
        System.out.print("Nama file: ");
        String namaFile = in.nextLine();
        while (!Objects.equals(namaFile, new String("stop"))) {
            Matrix M = new Matrix(Matrix.readFile("../test/" + namaFile + ".txt"));

            System.out.println("Matrix:");
            M.show();
            System.out.println();
            
            System.out.println("Echelon:");
            M.getEchelon().show(); 
            System.out.println();
    
            System.out.println("Reduced Echelon:");
            M.getReducedEchelon().show();
            System.out.println();
    
            System.out.printf("Determinan: %f\n", M.getDeterminant());
            System.out.println();
            
            // System.out.println("Inverse: ");
            // Matrix.inverse(M).show();
            // System.out.println();
    
            SPL solusi = new SPL(M);
            System.out.println("SPL:");
            System.out.println("Pake Gauss: ");
            solusi.solveGauss();
            // solusi.EF.show();
            solusi.showSol();
            System.out.println();
    
            System.out.println("Pake Gauss Jordan: ");
            solusi.solveGaussJordan();
            solusi.showSol();
            System.out.println();
    
            // System.out.println("Pake Cramer: ");
            // solusi.solveCramer();
            // solusi.showSol();
            // System.out.println();
    
            // System.out.println("Pake Inverse: ");
            // solusi.solveInverse();
            // solusi.showSol();
            // System.out.println();

            System.out.println("================================== NEXT TEST ==================================");
            System.out.print("Nama file: ");
            namaFile = in.nextLine();
        }
        in.close();
        System.out.println("FINISH");
    }
}

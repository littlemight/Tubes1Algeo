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
            Matrix M = new Matrix(Matrix.readFile("../test/" + namaFile));
            // Matrix M = new Matrix(Matrix.readKB());
            System.out.println("Matrix:");
            // M.show();
            // System.out.println("G:");
            // System.out.println(Util.formatOutput(M.getDeterminantG()));
            // System.out.println();
            // System.out.println("GJ:");
            // System.out.println(Util.formatOutput(M.getDeterminantGJ()));
            // System.out.println();
            // System.out.println("KOFAK:");
            // System.out.println(Util.formatOutput(M.getDeterminantCofactor()));
            // System.out.println("nama file simpan");
            // String nm = in.nextLine();
            // M.showFile("../test/" + nm);
            // System.out.println();
            
            // System.out.println("Echelon:");
            // M.getEchelon().show(); 
            // System.out.println();
    
            // System.out.println("Reduced Echelon:");
            // M.getReducedEchelon().show();
            // System.out.println();
    
            // System.out.printf("Determinan: %f\n", M.getDeterminant());
            // System.out.println();
            
            System.out.println("Inverse: ");
            System.out.println("GJ:");
            if (M.getInverseGJ() != null) {
                M.getInverseGJ().show();
            } else {
                System.out.println("Tidak punya inverse.");
            }
            System.out.println();
    
            System.out.println("Kofaktor:");
            if (M.getInverseCofactor() != null) {
                M.getInverseCofactor().show();
            } else {
                System.out.println("Tidak punya inverse.");
            }

            // SPL solusi = new SPL(M);
            // System.out.println("SPL:");
            // System.out.println("Pake Gauss: ");
            // solusi.solveGauss();
            // solusi.showAug();
            // solusi.showAugFile("../test/titit.txt");

            // // solusi.EF.show();
            // solusi.showAug();
            // solusi.showEF();
            // solusi.showSol();
            // System.out.println();
    
            // System.out.println("Pake Gauss Jordan: ");
            // solusi.solveGaussJordan();
            // // solusi.showAug();
            // solusi.showEF();
            // System.out.println();
    
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

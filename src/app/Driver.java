package app;
import java.util.*;
import java.io.*;
import app.*;

/**
 * Class Driver untuk menjalankan program utama
 */
public class Driver {

    /**
     * Main untuk menjalankan program utama
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("--------------------------------------------------");
        System.out.println("||                                              ||");
        System.out.println("||                                              ||");
        System.out.println("||                                              ||");
        System.out.println("||          -Sistem Persamaan Linier-           ||");
        System.out.println("||                -Version 1.0-                 ||");
        System.out.println("||                    Dari                      ||");
        System.out.println("||                Jun From Korea                ||");
        System.out.println("||                                              ||");
        System.out.println("||                                              ||");
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("Untuk melanjutkan, ketikkan 'menu' untuk memasuki, atau 'exit' untuk keluar program");
        System.out.println();
        System.out.print("Masukkan perintah: ");

        String cmd = in.nextLine();
        do {
            if(!cmd.equals("menu")){
                System.out.println("invalid command.");
            } else {
                break;
            }
            System.out.print("enter a command: ");
            cmd = in.nextLine();
            System.out.println(cmd);
        } while(!cmd.equals("menu"));

        if(cmd.equals("menu")){
            showMenu();
        }

        in.close();
    }

    /**
     * Menampilkan menu di program utama
     */
    private static void showMenu(){
        Scanner in = new Scanner(System.in);
        int cmd;
        do {
            System.out.println("----------------------MENU------------------------");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinant Matriks");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Matriks Kofaktor");
            System.out.println("5. Matriks Adjoin");
            System.out.println("6. Interpolasi Polinom");
            System.out.println("7. Studi Kasus");
            System.out.println("8. Exit");
            System.out.println("--------------------------------------------------");
            System.out.print("Pilihan: ");
            cmd = in.nextInt();
            in.nextLine();
            switch(cmd){
                case 1:
                    linearProgram(in);
                    break;
                case 2:
                    determinantProgram(in);
                    break;
                case 3:
                    inverseProgram(in);
                    break;
                case 4:
                    cofactorProgram(in);
                    break;
                case 5:
                    adjointProgram(in);
                    break;
                case 6:
                    interpolationProgram(in);
                    break;
                case 7:
                    caseStudies(in);
                    break;
            }
        } while(cmd!=8);
        System.out.println("Terima kasih sudah menggunakan produk kami, more at junho.id/buy.");
    }

    /**
     * primitif untuk memanggil operasi-operasi untuk menyelesaikan Sistem Persamaan Linier
     * @param in
     */
    public static void linearProgram(Scanner in){
        System.out.println("Program ini akan mencari solusi dari sistem persamaan linier dengan menggunakan bermacam-macam metode");
        System.out.println("Silahkan pilih jenis input (pastikan persamaan sudah dalam bentuk matriks augmented): ");
        System.out.println("1. Input Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan perintah: ");
        int cmd = in.nextInt();

        while(cmd!=1 && cmd !=2){
            System.out.println("Perintah tidak valid! coba lagi");
            System.out.print("Masukkan perintah: ");
            cmd = in.nextInt();
        }
        in.nextLine();
        Matrix mat;
        if(cmd==1){
            mat = Matrix.readKB();
        } else { // pasti cmd=2
            System.out.println("Masukkan nama file yang berisi matriks (lengkap dengan ekstensi): ");
            String filename = in.nextLine();
            filename = "./test/" + filename;
            mat = Matrix.readFile(filename);
        }
        if (mat.getN() <= 1) {
            System.out.println("Matriks augmented tidak valid.");
        } else {
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
            splUtil(in, mat);
        }
    }

    /**
     * primitif untuk antarmuka operasi SPL
     * @param in
     * @param t
     */
    private static void splUtil(Scanner in, Matrix t){
        int cmd;
        SPL spl = new SPL(t);
        System.out.println("Pilih metode yang ingin digunakan untuk mencari solusi dari sistem persamaan linier");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Cramer");

        System.out.print("Masukkan perintah: ");
        do {
            cmd = in.nextInt();
            if (cmd < 1 || cmd > 4) {
                System.out.println("Perintah tidak valid! coba lagi");
                System.out.print("Masukkan perintah: ");
            }
        } while(cmd < 1 || cmd > 4);
        in.nextLine();
        
        System.out.println("Berikut solusi dari sistem persamaan linier yang sudah di masukkan.");
        switch(cmd){
            case 1:
                spl.solveGauss();
                spl.showSol();
                break;
            case 2:
                spl.solveGaussJordan();
                spl.showSol();
                break;
            case 3:
                try {
                    spl.solveInverse();
                    spl.showSol();
                } catch(NullPointerException e){
                    System.out.println("Solusi tidak dapat ditemukan menggunakan metode ini.");
                }
                break;
            case 4:
                if (spl.A.isSquare()) {
                    spl.solveCramer();
                    spl.showSol();
                } else {
                    System.out.println("Solusi tidak dapat ditemukan menggunakan metode ini.");
                }
                break;
        }

        System.out.println("Apakah anda ingin menyimpan hasil matriks augmented ke file? (Y/N) ");
        String test = in.nextLine();
        if(test.equals("Y")){
            System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
            test = in.nextLine();
            try {
                spl.showAugFile(test);
            } catch (IOException e){
                System.out.println("Output ke file " + test + " gagal.");
            } 
        } 

        if (cmd == 1 || cmd == 2) {
            System.out.println("Apakah anda ingin menyimpan hasil matriks eselon ke file? (Y/N) ");
            test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
                test = in.nextLine();
                try {
                    spl.showEFFile(test);
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 
        }
        
        System.out.println("Apakah anda ingin menyimpan solusi dari sistem persamaan linier ke file? (Y/N) ");
        test = in.nextLine();
        if(test.equals("Y")){
            System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
            test = in.nextLine();
            spl.showFile(test);
        } 
    }

    /**
     * primitif untuk antarmuka operasi Interpolasi
     * @param in
     * @param itp
     */
    private static void interpolasiUtil(Scanner in, Interpolasi itp){
        int cmd;
        System.out.println("Pilih metode yang ingin digunakan untuk interpolasi");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Cramer");

        System.out.print("Masukkan perintah: ");
        do {
            cmd = in.nextInt();
            if (cmd < 1 || cmd > 4) {
                System.out.println("Perintah tidak valid! coba lagi");
                System.out.print("Masukkan perintah: ");
            }
        } while(cmd < 1 || cmd > 4);
        in.nextLine();

        System.out.println("Berikut persamaan yang dihasilkan dari interpolasi dengan metode yang dipilih.");
        switch(cmd){
            case 1:
                itp.solveInterGauss();
                break;
            case 2:
                itp.solveInterGaussJordan();
                break;
            case 3:
                itp.solveInterInverse();
                break;
            case 4:
                itp.solveInterCramer();
                break;
        }
        itp.showPersamaan();

        System.out.println("Apakah anda ingin mencari nilai persamaan fungsi untuk masukan x? (Y/N) ");
        String test = in.nextLine();
        if(test.equals("Y")){
            System.out.print("Masukkan jumlah titik yang ingin dicari: ");
            int q = in.nextInt();
            while(q > 0){
                System.out.print("Masukkan x: ");
                double x = in.nextDouble();
                if(!itp.isInRange(x)){
                    itp.warnX();
                }
                itp.queryY(x);
                q--;
            }
            in.nextLine();
        } 

        System.out.println("Apakah anda ingin menyimpan persamaan hasil interpolasi ke file? (Y/N) ");
        test = in.nextLine();
        if(test.equals("Y")){
            System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
            test = in.nextLine();
            try {
                itp.showPersamaanFile(test);
            } catch (IOException e){
                System.out.println("Output ke file " + test + " gagal.");
            } 
        } 

    }

    /**
     * primitif untuk memanggil operasi-operasi untuk menyelesaikan masalah interpolasi
     * @param in
     */
    public static void interpolationProgram(Scanner in){
        System.out.println("Program ini akan melakukan interpolasi pada titik-titik yang di input menggunakan keyboard atau dari file");
        System.out.println("Silahkan pilih jenis input titik: ");
        System.out.println("1. Input Keyboard");
        System.out.println("2. File");
        System.out.print("Masukkan perintah: ");
        int cmd = in.nextInt();

        while(cmd!=1 && cmd !=2){
            System.out.println("Perintah tidak valid! coba lagi");
            System.out.print("Masukkan perintah: ");
            cmd = in.nextInt();
        }

        in.nextLine();
        Interpolasi itp;
        if(cmd==1){
            itp = Interpolasi.readKB();
            if (itp == null) {
                System.out.println("Masukkan titik tidak valid (ada titik yang tidak unik, atau ada satu absis dengan dua ordinat).");
                return;
            }
        } else {
            System.out.println("Masukkan nama file yang berisi data titik-titik yang akan di interpolasi: ");
            String filename = in.nextLine();
            filename = "./test/" + filename;
            itp = Interpolasi.readFile(filename);
        }

        interpolasiUtil(in, itp);
    }

    /**
     * primitif untuk menjalankan studi kasus yang diberikan pada spesifikasi tubes
     * @param in
     */
    public static void caseStudies(Scanner in){
        System.out.println("Berikut adalah pengujian studi kasus yang diberikan pada spesifikasi tugas ini.");
        System.out.println("1. 1_1");
        System.out.println("2. 1_2");
        System.out.println("3. 1_3");
        System.out.println("4. Matriks Hilbert");
        System.out.println("5. 2_1");
        System.out.println("6. 2_2");
        System.out.println("7. 2_i");
        System.out.println("8. 2_ii");
        System.out.println("9. 3_5x5");
        System.out.println("10. 3_10x10");
        System.out.println("11. 4_Rangkaian");
        System.out.println("12. 5_Penduduk (dalam jutaan) ");
        System.out.println("13. 5_Penduduk");
        System.out.println("14. Menghitung fungsi f(x)");
        int cmd = in.nextInt();

        while(cmd < 1 || cmd > 14){
            System.out.println("perintah tidak valid! coba lagi");
            System.out.print("masukkan perintah: ");
            cmd = in.nextInt();
        }
        StudiKasus.initKasus();
        switch(cmd){
            case 1:
                splUtil(in, StudiKasus.spl1_1);
                break;
            case 2:
                splUtil(in, StudiKasus.spl1_2);
                break;
            case 3:
                splUtil(in, StudiKasus.spl1_3);
                break;
            case 4:
                System.out.println("Studi Kasus untuk matriks Hilbert, untuk nilai n dari 1 sampai 10");
                System.out.print("Masukkan banyaknya uji coba: ");
                int t = in.nextInt();
                while(t > 0){
                    int n;
                    do {
                        System.out.print("Masukkan n : ");
                        n = in.nextInt();
                        if(n < 1 || n > 10){
                            System.out.println("masukkan tidak valid!");
                        }
                    } while (n < 1 || n > 10);
                    splUtil(in, StudiKasus.hilbert[n]);
                    t--;
                }
                in.nextLine();
                break;
            case 5:
                splUtil(in, StudiKasus.spl2_1);
                break;
            case 6:
                splUtil(in, StudiKasus.spl2_2);
                break;
            case 7:
                splUtil(in, StudiKasus.spl2_i);
                break;
            case 8:
                splUtil(in, StudiKasus.spl2_ii);
                break;
            case 9:
                detUtil(in, StudiKasus.det3_5);
                break;
            case 10:
                detUtil(in, StudiKasus.det3_10);
                break;
            case 11:
                splUtil(in, StudiKasus.spl4_rang);
                break;
            case 12:
                interpolasiUtil(in, StudiKasus.inter_penduduk);
                break;
            case 13:
                interpolasiUtil(in, StudiKasus.inter_pendudukEx);
                break;
            case 14:
                int n;
                do {
                    System.out.print("Masukkan derajat polinom yang ingin dibuat: ");
                    n = in.nextInt();
                    if(n<1){
                        System.out.println("derajat tidak valid");
                    }
                } while(n<1);
                StudiKasus.initSeder(n);
                interpolasiUtil(in, StudiKasus.inter_seder);
                break;
        }
    }

    private static void detUtil(Scanner in, Matrix mat){
        in.nextLine();
        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
            System.out.println("Determinan dari matriks tersebut adalah: " + Util.formatOutput(mat.getDeterminantGJ()));
            System.out.println("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
                test = in.nextLine();
                try {
                    mat.showFile(test);
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 

        } else {
            System.out.println("Matriks tidak memiliki determinan karena bukan matriks persegi.");
        }
    }

    public static void determinantProgram(Scanner in){
        System.out.println("Program ini akan menghitung determinan dari matriks persegi");
        System.out.println("Silahkan pilih jenis input matriks: ");
        System.out.println("1. Input Keyboard");
        System.out.println("2. File");
        System.out.print("masukkan perintah: ");
        int cmd = in.nextInt();

        while(cmd!=1 && cmd !=2){
            System.out.println("perintah tidak valid! coba lagi");
            System.out.print("masukkan perintah: ");
            cmd = in.nextInt();
        }
        in.nextLine();
        Matrix mat;
        if(cmd==1){
            mat = Matrix.readKB();
        } else { // pasti cmd=2
            System.out.println("masukkan nama file yang berisi matriks (lengkap dengan ekstensi): ");
            String filename = in.nextLine();
            filename = "./test/" + filename;
            mat = Matrix.readFile(filename);
        }
        // getDeterminanG
        // getDeterminanGJ
        // getDeterminanCofactor

        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
            System.out.println("Silahkan pilih metode yang akan digunakan untuk mencari determinan: ");
            System.out.println("1. Gauss");
            System.out.println("2. Gauss-Jordan");
            System.out.println("3. Kofaktor");
            System.out.print("masukkan perintah: ");
            cmd = in.nextInt();
            while(cmd < 1 || cmd > 3){
                System.out.println("Perintah tidak valid! coba lagi");
                System.out.print("Masukkan perintah: ");
                cmd = in.nextInt();
            }
            switch(cmd){
                case 1:
                    System.out.println("Determinan dari matriks tersebut menggunakan eliminasi Gauss adalah: " + Util.formatOutputLong(mat.getDeterminantG()));
                    break;
                case 2:
                    System.out.println("Determinan dari matriks tersebut menggunakan eliminasi Gauss-Jordan adalah: " + Util.formatOutputLong(mat.getDeterminantGJ()));
                    break;
                case 3:
                    System.out.println("Determinan dari matriks tersebut menggunakan matriks kofaktor adalah: " + Util.formatOutputLong(mat.getDeterminantCofactor()));
                    break;

            }
            in.nextLine();
            System.out.println("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
                test = in.nextLine();
                try {
                    Util.showBD(mat.getDeterminantG(), test);
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 

        } else {
            System.out.println("Matriks tidak memiliki determinan karena bukan matriks persegi.");
        }

    }

    public static void inverseProgram(Scanner in){
        System.out.println("Program ini akan mencari matriks invers dari matriks persegi");
        System.out.println("Silahkan pilih jenis input matriks: ");
        System.out.println("1. Input Keyboard");
        System.out.println("2. File");
        System.out.print("masukkan perintah: ");
        int cmd = in.nextInt();

        while(cmd!=1 && cmd !=2){
            System.out.println("Perintah tidak valid! coba lagi");
            System.out.print("Masukkan perintah: ");
            cmd = in.nextInt();
        }
        in.nextLine();
        Matrix mat;
        if(cmd==1){
            mat = Matrix.readKB();
        } else { // pasti cmd=2
            System.out.println("Masukkan nama file yang berisi matriks: ");
            String filename = in.nextLine();
            filename = "./test/" + filename;
            mat = Matrix.readFile(filename);
        }

        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();

            System.out.println("Pilih metode untuk mencari matriks balikan");
            System.out.println("1. Gauss-Jordan");
            System.out.println("2. Matriks Adjoin");
            do {
                cmd = in.nextInt();
            } while(cmd < 1 || cmd > 2);
            Matrix inverse = new Matrix();
            switch(cmd){
                case 1:
                    inverse = mat.getInverseGJ();
                    System.out.println("Matriks balikan yang didapat menggunakan metode Gauss-Jordan dari matriks diatas adalah: ");
                    break;
                case 2:
                    inverse = mat.getInverseCofactor();
                    System.out.println("Matriks balikan yang didapat menggunakan metode Matriks Adjoin dari matriks diatas adalah: ");
                    break;
            }
            inverse.show();
            System.out.println("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file: ");
                test = in.nextLine();
                try {
                    inverse.showFile(test);
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 

        } else {
            System.out.println("Matriks tidak memiliki invers karena bukan matriks persegi.");
        }
    }

    public static void cofactorProgram(Scanner in){
        System.out.println("Silahkan pilih jenis input matriks: ");
        System.out.println("1. Input Keyboard");
        System.out.println("2. File");
        System.out.print("masukkan perintah: ");
        int cmd = in.nextInt();

        while(cmd!=1 && cmd !=2){
            System.out.println("perintah tidak valid! coba lagi");
            System.out.print("masukkan perintah: ");
            cmd = in.nextInt();
        }
        in.nextLine();
        Matrix mat;
        if(cmd==1){
            mat = Matrix.readKB();
        } else { // pasti cmd=2
            System.out.println("masukkan nama file yang berisi matriks: ");
            String filename = in.nextLine();
            filename = "./test/" + filename;            
            mat = Matrix.readFile(filename);
        }

        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
            System.out.println("Matriks Kofaktor dari matriks diatas adalah: ");
            Matrix cofactor = mat.getCofactor();
            cofactor.show();
            System.out.print("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file: ");
                test = in.nextLine();
                try {
                    cofactor.showFile(test);
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 

        } else {
            System.out.println("Matriks tidak memiliki kofaktor karena bukan matriks persegi.");
        }
    }

    public static void adjointProgram(Scanner in){
        System.out.println("Silahkan pilih jenis input matriks: ");
        System.out.println("1. Input Keyboard");
        System.out.println("2. File");
        System.out.print("masukkan perintah: ");
        int cmd = in.nextInt();

        while(cmd!=1 && cmd !=2){
            System.out.println("perintah tidak valid! coba lagi");
            System.out.print("masukkan perintah: ");
            cmd = in.nextInt();
        }
        in.nextLine();

        Matrix mat;
        if(cmd==1){
            mat = Matrix.readKB();
        } else { // pasti cmd=2
            System.out.println("masukkan nama file yang berisi matriks: ");
            String filename = in.nextLine();
            filename = "./test/" + filename;
            mat = Matrix.readFile(filename);
        }

        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
            System.out.println("Matriks Adjoin dari matriks diatas adalah: ");
            Matrix adjoint = mat.getAdjoint();
            adjoint.show();
            System.out.println("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file: ");
                test = in.nextLine();
                try {
                    adjoint.showFile(test);
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 

        } else {
            System.out.println("Matriks tidak memiliki adjoin karena bukan matriks persegi.");
        }
    }
}
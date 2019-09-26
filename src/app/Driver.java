package app;
import java.util.*;
import java.io.*;

public class Driver {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("--------------------------------------------------");
        System.out.println("||                                              ||");
        System.out.println("||                                              ||");
        System.out.println("||                                              ||");
        System.out.println("||          -Sistem Persamaan Linier-           ||");
        System.out.println("||                -Version 1.0-                 ||");
        System.out.println("||                     By                       ||");
        System.out.println("||                Jun From Korea                ||");
        System.out.println("||                                              ||");
        System.out.println("||                                              ||");
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("To continue, please type in 'menu' to enter the main menu, or 'exit' to quit the program");
        System.out.println();
        System.out.print("enter a command: ");

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
    }

    private static void showMenu(){
        Scanner in = new Scanner(System.in);
        int cmd;
        do {
            System.out.println("----------------------MENU------------------------");
            System.out.println("1. Linear System of Equation");
            System.out.println("2. Determinant of a Matrix");
            System.out.println("3. Inverse Matrix");
            System.out.println("4. Cofactor Matrix");
            System.out.println("5. Adjoint Matrix");
            System.out.println("6. Polynomial Interpolation");
            System.out.println("7. Studi Kasus");
            System.out.println("8. Exit");
            System.out.println("--------------------------------------------------");
            System.out.print("select a command: ");
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
            }
        } while(cmd!=8);
        System.out.println("Thank you for trying the trial.");
    }

    public static void linearProgram(Scanner in){
        System.out.println("Program ini akan mencari solusi dari sistem persamaan linear dengan menggunakan bermacam-macam metode");
        System.out.println("Silahkan pilih jenis input (pastikan persamaan sudah dalam bentuk matriks augmented): ");
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
            mat = Matrix.readFile(filename);
        }
        SPL spl = new SPL(mat);

        System.out.println("Pilih metode yang ingin digunakan untuk mencari solusi dari sistem persamaan linear");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Cramer");
        do {
            cmd = in.nextInt();
        } while(cmd < 1 || cmd > 4);
        in.nextLine();

        System.out.println("Berikut solusi dari sistem persamaan linear yang sudah di masukkan.");
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
                spl.solveInverse();
                spl.showSol();
                break;
            case 4:
                spl.solveCramer();
                spl.showSol();
                break;
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
            mat = Matrix.readFile(filename);
        }

        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
<<<<<<< HEAD
            System.out.println("Tentukan metode yang ingin anda gunakan: ");
            System.out.println("1. Gauss");
            System.out.println("2. Gauss-Jordan");
            System.out.println("3. Ekspansi Kofaktor");
            do {
                cmd = in.nextInt();
            } while(cmd > 3 || cmd < 1);
            in.nextLine();
            switch(cmd){
                case 1:
                    System.out.println("Determinan dari matriks tersebut menggunakan metode Gauss adalah: " + Util.formatOutputLong(mat.getDeterminantG()));
                    break;
                case 2:
                    System.out.println("Determinan dari matriks tersebut menggunakan metode Gauss-Jordan adalah: " + Util.formatOutputLong(mat.getDeterminantGJ()));
                    break;
                case 3:
                    System.out.println("Determinan dari matriks tersebut menggunakan metode ekspansi kofaktor adalah: " + Util.formatOutputLong(mat.getDeterminantCofactor()));
                    break;
            }
=======
            System.out.println("Determinan dari matriks tersebut adalah: " + mat.getDeterminantGJ());
>>>>>>> 32b4baa33add3e8cc6d39a239bf41e533d12c722
            System.out.println("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file (lengkap dengan ekstensi): ");
                test = in.nextLine();
                try {
                    mat.showFile(test);
<<<<<<< HEAD
                } catch(IOException e){
                    System.out.println("Hasil tidak bisa disimpan ke file. ");
                }
=======
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
>>>>>>> 32b4baa33add3e8cc6d39a239bf41e533d12c722
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
            mat = Matrix.readFile(filename);
        }

        if(mat.isSquare()){
            System.out.println("Matriks yang anda masukkan adalah: ");
            mat.show();
            System.out.println("Matriks invers dari matriks diatas adalah: ");
            Matrix inverse = Matrix.inverse(mat);
            inverse.show();
            System.out.println("Apakah anda ingin menyimpan hasil ke file? (Y/N) ");
            String test = in.nextLine();
            if(test.equals("Y")){
                System.out.print("Masukkan nama file: ");
                test = in.nextLine();
                try {
                    inverse.showFile(test);
<<<<<<< HEAD
                } catch(IOException e){
                    System.out.println("Hasil tidak bisa disimpan ke file. ");
                }
=======
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
>>>>>>> 32b4baa33add3e8cc6d39a239bf41e533d12c722
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
<<<<<<< HEAD
                } catch(IOException e){
                    System.out.println("Hasil tidak bisa disimpan ke file. ");
                }
            }
=======
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
            } 
>>>>>>> 32b4baa33add3e8cc6d39a239bf41e533d12c722

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
<<<<<<< HEAD
                } catch(IOException e){
                    System.out.println("Hasil tidak bisa disimpan ke file. ");
                }
=======
                } catch (IOException e){
                    System.out.println("Output ke file " + test + " gagal.");
                } 
>>>>>>> 32b4baa33add3e8cc6d39a239bf41e533d12c722
            } 

        } else {
            System.out.println("Matriks tidak memiliki adjoin karena bukan matriks persegi.");
        }
    }
}
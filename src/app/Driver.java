package app;
import java.util.*;

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
            System.out.println("7. Exit");
            System.out.println("--------------------------------------------------");
            System.out.print("select a command: ");
            cmd = in.nextInt();
            switch(cmd){
                case 2:
                    determinantProgram(in);
                    break;
            }
        } while(cmd!=7);
        System.out.println("Thank you for trying the trial.");
    }

    public static void determinantProgram(Scanner in){
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

        if(cmd==1){
            Matrix mat = Matrix.readKB();

            if(mat.isSquare()){
                System.out.println("Matriks yang anda masukkan adalah: ");
                mat.show();
                System.out.println("Determinan dari matriks tersebut adalah: " + mat.getDeterminant());
            } else {
                System.out.println("Matriks tidak memiliki determinan karena bukan matriks persegi.");
            }
        } else { // pasti cmd=2

        }

    }
}
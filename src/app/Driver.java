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
            }
            System.out.print("enter a command: ");
            cmd = in.nextLine();
            System.out.println(cmd);
        } while(!cmd.equals("menu"));

        if(cmd.equals("menu")){
            showMenu(in);
        }
    }

    private static void showMenu(Scanner in){

        int cmd;
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

        while(cmd!=7){

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
        }
        System.out.println("Thank you for using our program.");
    }
}
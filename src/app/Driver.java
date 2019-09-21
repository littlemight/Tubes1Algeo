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
            showMenu();
        }
    }

    private static void showMenu(){
        System.out.println("Hello");
    }
}
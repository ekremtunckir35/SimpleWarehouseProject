//package SimpleWarehouseProject;

import java.util.Scanner;
public class DepoApp {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {

        mainMenu();
    }
    public static void mainMenu() {
        ProductService ps = new ProductService();
        int select;

        do {

            System.out.println("========= Depo Management System =========");
            System.out.println("1- Define a product");
            System.out.println("2- List products");
            System.out.println("3- Enter a product");
            System.out.println("4- Place a product on the shelf");
            System.out.println("5- Product output");
            System.out.println("0- EXIT");
            System.out.print("Select an Option: ");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 0 and 5.");
                scan.next(); // Clear invalid input
            }
            select = scan.nextInt();
            scan.nextLine(); // Clear the newline
            switch (select) {
                case 1 -> ps.addProduct(ps.products);
                case 2 -> ps.listProduct(ps.products);
                case 3 -> ps.enterProduct(ps.products);
                case 4 -> ps.putProductOnShelf(ps.products);
                case 5 -> ps.productOutput(ps.products);
                case 0 -> System.out.println("Exiting the system...");
                default -> System.out.println("Invalid selection, please enter a number between 0 and 5.");
            }
            if (select != 0) {
                promptReturnToMenu();
            }
        } while (select != 0);
    }
    private static void promptReturnToMenu() {
        System.out.println(" Press Enter to return to the main menu...");
        scan.nextLine(); // Wait for user input to return to the main menu
    }
}



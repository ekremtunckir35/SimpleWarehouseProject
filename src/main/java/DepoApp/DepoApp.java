package DepoApp;

import java.util.Scanner;

public class DepoApp {
    static Scanner scan = new Scanner(System.in);

    // ANSI renk kodları
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";



    public static void main(String[] args) {
        ProductService productService = new ProductService(); // Ürün servisi oluşturma
        mainMenu(productService);
    }

    public static void mainMenu(ProductService productService) {
        int select;

        do {
            System.out.println(BLUE_BACKGROUND +BLACK+ "========= Depo Management System =========" + RESET);
            System.out.println(BLUE + "1- Define a product" + RESET);
            System.out.println(BLUE + "2- List products" + RESET);
            System.out.println(BLUE + "3- Enter a product" + RESET);
            System.out.println(BLUE + "4- Place a product on the shelf" + RESET);
            System.out.println(BLUE + "5- Product output" + RESET);
            System.out.println(BLUE + "6- Search Product" + RESET);
            System.out.println(BLUE + "7- Remove a product" + RESET);
            System.out.println(BLUE + "8- Clear all products" + RESET);
            System.out.println(RED + "0- EXIT" + RESET);
            System.out.print(YELLOW + "Select an Option: " + RESET);

            while (!scan.hasNextInt()) {
                System.out.println(RED + "Invalid input! Please enter a number between 0 and 8." + RESET);
                scan.next(); // Hatalı giriş varsa temizle
            }
            select = scan.nextInt();
            scan.nextLine(); // Temizle

            // Seçimin geçerliliğini kontrol et
            if (select < 0 || select > 8) {
                System.out.println(RED + "Invalid selection, please enter a number between 0 and 8." + RESET);
                continue; // Geçersiz seçimde döngüye geri dön
            }

            switch (select) {
                case 1 -> {
                    System.out.println(GREEN + "Defining a new product..." + RESET);
                    productService.addProduct(productService.products);
                }
                case 2 -> {
                    System.out.println(GREEN + "Listing all products..." + RESET);
                    productService.listProduct(productService.products);
                    productService.listProductWithSorting(productService.products);
                }
                case 3 -> {
                    System.out.println(GREEN + "Entering a product..." + RESET);
                    productService.enterProduct(productService.products);
                }
                case 4 -> {
                    System.out.println(GREEN + "Placing a product on the shelf..." + RESET);
                    productService.putProductOnShelf(productService.products);
                }
                case 5 -> {
                    System.out.println(GREEN + "Outputting a product..." + RESET);
                    productService.productOutput(productService.products);
                }case 6 -> {
                    System.out.println(GREEN + "Outputting a product..." + RESET);
                    productService.searchProduct(productService.products);
                }
                case 7 -> {
                    System.out.println(GREEN + "Removing a product..." + RESET);
                    productService.removeProduct(productService.products);
                }
                case 8 -> {
                    System.out.println(GREEN + "Clearing all products..." + RESET);
                    productService.clearProducts(productService.products);
                }
                case 0 -> System.out.println(RED + "Exiting the system..." + RESET);
            }
            if (select != 0) {
                promptReturnToMenu();
            }
        } while (select != 0);
    }

    private static void promptReturnToMenu() {
        System.out.println(YELLOW + "Press Enter to return to the main menu..." + RESET);
        scan.nextLine(); // Ana menüye dönmek için Enter'a basmayı bekle
    }
}

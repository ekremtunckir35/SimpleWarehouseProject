package DepoApp;


import java.util.Scanner;

public class DepoApp {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        ProductService productService = new ProductService(); // Ürün servisi oluşturma
        mainMenu(productService);
    }

    public static void mainMenu(ProductService productService) {
        int select;

        do {
            System.out.println("========= Depo Management System =========");
            System.out.println("1- Define a product");
            System.out.println("2- List products");
            System.out.println("3- Enter a product");
            System.out.println("4- Place a product on the shelf");
            System.out.println("5- Product output");
            System.out.println("6- Remove a product");
            System.out.println("7- Clear all products");
            System.out.println("0- EXIT");
            System.out.print("Select an Option: ");

            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 0 and 7.");
                scan.next(); // Hatalı giriş varsa temizle
            }
            select = scan.nextInt();
            scan.nextLine(); // Temizle

            // Seçimin geçerliliğini kontrol et
            if (select < 0 || select > 7) {
                System.out.println("Invalid selection, please enter a number between 0 and 7.");
                continue; // Geçersiz seçimde döngüye geri dön
            }

            switch (select) {
                case 1 -> {
                    System.out.println("Defining a new product...");
                    productService.addProduct(productService.products);
                }
                case 2 -> {
                    System.out.println("Listing all products...");
                    productService.listProduct(productService.products);
                }
                case 3 -> {
                    System.out.println("Entering a product...");
                    productService.enterProduct(productService.products);
                }
                case 4 -> {
                    System.out.println("Placing a product on the shelf...");
                    productService.putProductOnShelf(productService.products);
                }
                case 5 -> {
                    System.out.println("Outputting a product...");
                    productService.productOutput(productService.products);
                }
                case 6 -> {
                    System.out.println("Removing a product...");
                    productService.removeProduct(productService.products);
                }
                case 7 -> {
                    System.out.println("Clearing all products...");
                    productService.clearProducts(productService.products);
                }
                case 0 -> System.out.println("Exiting the system...");
            }
            if (select != 0) {
                promptReturnToMenu();
            }
        } while (select != 0);
    }

    private static void promptReturnToMenu() {
        System.out.println("Press Enter to return to the main menu...");
        scan.nextLine(); // Ana menüye dönmek için Enter'a basmayı bekle
    }
}

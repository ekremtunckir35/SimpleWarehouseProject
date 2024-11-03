import java.time.LocalDate;
import java.util.*;

public class ProductService {
    Map<String, Product> products = new LinkedHashMap<>();
    Scanner sc = new Scanner(System.in);

    public void addProduct(Map<String, Product> products) {
        Product pr = new Product(null, null, null, 0, null, null);
        System.out.print("Enter a product name: ");
        String productName = sc.nextLine().toUpperCase().trim();
        System.out.print("Enter a productor name: ");
        String productorName = sc.nextLine().toUpperCase().trim();
        System.out.print("Enter a part: ");
        String part = sc.nextLine().toUpperCase().trim();

        for (Product w : products.values()) {
            if (w.getProductName().equals(productName) && w.getProductorName().equals(productorName) && w.getPart().equals(part)) {
                System.out.println("This product already exists. You can update the quantity instead.");
                return;
            }
        }

        int productQuantity;
        do {
            System.out.print("Enter a quantity: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a numeric value for quantity.");
                sc.next(); // Clear invalid input
            }
            productQuantity = sc.nextInt();
            sc.nextLine(); // Clear the newline
            if (productQuantity <= 0) {
                System.out.println("Quantity should be a positive number.");
            }
        } while (productQuantity <= 0);

        pr.setProductName(productName);
        pr.setProductorName(productorName);
        pr.setQuantity(productQuantity);
        pr.setPart(part);
        productId(pr);
        products.put(pr.getId(), pr);
    }

    public void listProduct(Map<String, Product> products) {
        System.out.printf("%-20s %-20s %-20s %-7s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (Product product : products.values()) {
            System.out.printf("%-20s %-20s %-20s %-7s %-10s %-10s%n", product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
        }
    }

    public void productId(Product pr) {
        pr.setId(pr.getProductName().toUpperCase().substring(0, 2) + LocalDate.now().getYear() + Product.counter);
        Product.counter++;
    }

    public void enterProduct(Map<String, Product> products) {
        System.out.print("Enter the product ID to update quantity: ");
        String productId = sc.nextLine().trim();
        Product product = products.get(productId);

        if (product != null) {
            int quantity;
            do {
                System.out.print("Enter the quantity to add: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a numeric value for quantity.");
                    sc.next();
                }
                quantity = sc.nextInt();
                sc.nextLine();
                if (quantity <= 0) {
                    System.out.println("Quantity should be a positive number.");
                }
            } while (quantity <= 0);
            product.setQuantity(product.getQuantity() + quantity);
            System.out.println("Product quantity updated successfully. New stock: " + product.getQuantity());
        } else {
            System.out.println("Product not found with this ID.");
        }
    }

    public void putProductOnShelf(Map<String, Product> products) {
        System.out.print("Enter the product ID to place on the shelf: ");
        String productId = sc.nextLine().trim();
        Product product = products.get(productId);

        if (product != null) {
            int shelfNo;
            boolean isShelfAvailable;
            do {
                System.out.print("Enter a shelf number: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a valid shelf number.");
                    sc.next();
                }
                shelfNo = sc.nextInt();
                sc.nextLine();

                isShelfAvailable = true;
                for (Product p : products.values()) {
                    if (p.getShelf() != null && p.getShelf().equals("SHELF" + shelfNo)) {
                        System.out.println("This shelf is already occupied. Try a different one.");
                        isShelfAvailable = false;
                        break;
                    }
                }
            } while (shelfNo < 0 || !isShelfAvailable);

            product.setShelf("SHELF" + shelfNo);
            System.out.println("Product placed on shelf " + product.getShelf() + " successfully.");
        } else {
            System.out.println("Product not found with this ID.");
        }
    }

    public void productOutput(Map<String, Product> products) {
        System.out.print("Enter the product ID for output: ");
        String productId = sc.nextLine().trim();
        Product product = products.get(productId);

        if (product != null) {
            int quantity;
            do {
                System.out.print("Enter the quantity to remove: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a numeric value for quantity.");
                    sc.next();
                }
                quantity = sc.nextInt();
                sc.nextLine();

                if (quantity > product.getQuantity()) {
                    System.out.println("Insufficient quantity in stock. Maximum available: " + product.getQuantity());
                } else if (quantity <= 0) {
                    System.out.println("Quantity should be a positive number.");
                }
            } while (quantity <= 0 || quantity > product.getQuantity());

            product.setQuantity(product.getQuantity() - quantity);
            System.out.println("Product output successful. Remaining stock: " + product.getQuantity());
        } else {
            System.out.println("Product not found with this ID.");
        }
    }
}
import java.time.LocalDate;
import java.util.*;

public class ProductService implements DepoInterface {
    Map<String, Product> products = new LinkedHashMap<>();
    Scanner sc = new Scanner(System.in);


    @Override
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
                System.out.println("This product is already exists. You can update the quantity instead.");
                return;
            }

            //Bunu lambda ile yapmak istersek:
            //if (products.values().stream().anyMatch(w ->
            //        w.getProductName().equals(productName) &&
            //        w.getProductorName().equals(productorName) &&
            //        w.getPart().equals(part))) {
            //    System.out.println("This product already exists. You can update the quantity instead.");
            //    return;
            //}
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
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "----------", "------------", "--------------", "--------", "-------", "------");
        for (Product product : products.values()) {
            System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
        }

        //for dongusunu lambda ile yapmak istersek:

        //products.values().forEach(product -> System.out.printf("%-20s %-20s %-20s %-7s %-10s %-10s%n",
        //product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(),
        //product.getPart(), product.getShelf()));

    }

    // id tanımlama method
    public void productId(Product pr) {
//ürün ismi boş geçerse
        try{
            pr.setId(pr.getProductName().toUpperCase().substring(0, 2) + LocalDate.now().getYear() + Product.counter);
            Product.counter++;
        }catch (StringIndexOutOfBoundsException e){
            pr.setId("NULL" + LocalDate.now().getYear() + Product.counter);
            Product.counter++;

        }

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
            System.out.println("Product quantity updated successfully. NEW STOCK: " + product.getQuantity());
        } else {
            System.out.println("the id you have entered is not on the list plz check again Id");
        }

    }

    public void putProductOnShelf(Map<String, Product> products) {

        //yanlis giris yapinca ana menuye donmek icin 2 kez enter
        // yapmak gerekiyor. sanirim bir tane bos nextline atmak gerekiyor

        // iyilestirme onerisi: tum metodlari bir interface icinde tanimlayip sonra product servisi bu interface implements yapip concretelastirabiliriz.
        System.out.print("Enter the product ID to place on the shelf: ");
        String productId = sc.nextLine().trim();

        Product product = products.get(productId);

        if (product != null) {
            int shelfNo;
            boolean isShelfAvailable;

            do {
                System.out.print("Enter a positive shelf number: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a valid  number value for the Shelf Number.");
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

            //lambda ile yaparsak soyle olabilir:
            /*isShelfAvailable = products.values().stream().noneMatch(p -> "SHELF" + shelfNo.equals(p.getShelf()));
            if (!isShelfAvailable) {
                System.out.println("This shelf is already occupied. Try a different one.");
            } else {
                product.setShelf("SHELF" + shelfNo);
                System.out.println("Product placed on shelf " + product.getShelf() + " successfully.");
            }
        } while (!isShelfAvailable);*/

            product.setShelf("SHELF" + shelfNo);
            System.out.println("Product placed on shelf " + product.getShelf() + " successfully.");
        } else {
            System.out.println("the id you have entered is not on the list plz check again Id");
        }



    }

    public void productOutput(Map<String, Product> products) {
        // id numarasini yanlis girince hata vermiyor. direk ana menuye doduruyor duzeltmek gerek.
        //3. metoddaki calisiyor onu kullanabiliriz

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
                    System.out.println("Insufficient quantity in stock. MAXIMUM AVAILABLE: " + product.getQuantity());
                } else if (quantity <= 0) {
                    System.out.println("Quantity should be a positive number.");
                }
            } while (quantity <= 0 || quantity > product.getQuantity());

            product.setQuantity(product.getQuantity() - quantity);
            System.out.println("Product output successful. REMAINING STOCK: " + product.getQuantity());
        } else {
            System.out.println("the id you have entered is not on the list plz check again");
        }

    }
}




//Tugce

package DepoApp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class ProductService implements DepoInterface {
    // Ürünlerin saklanacağı Map, LinkedHashMap kullanılarak eklenme sırası korunur
    Map<String, Product> products = new LinkedHashMap<>();
    Scanner sc = new Scanner(System.in);
    ProductSaveService saveService = new ProductSaveService(); // Ürünlerin dosyaya kaydedilmesi için servis sınıfı

    // Yapıcı metot, uygulama başlarken dosyadan ürünleri yükler
    public ProductService() {
        products = saveService.loadFromFile();
    }

    @Override
    public void addProduct(Map<String, Product> products) {
        // Yeni bir ürün nesnesi oluştur
        Product pr = new Product(null, null, null, 0, null, null);

        // Ürün bilgilerini kullanıcıdan al
        System.out.print("Enter a product name: ");
        String productName = sc.nextLine().toUpperCase().trim();
        System.out.print("Enter a productor name: ");
        String productorName = sc.nextLine().toUpperCase().trim();
        System.out.print("Enter a part: ");
        String part = sc.nextLine().toUpperCase().trim();

        // Aynı ürünün mevcut olup olmadığını kontrol et
        for (Product w : products.values()) {
            if (w.getProductName().equals(productName) && w.getProductorName().equals(productorName) && w.getPart().equals(part)) {
                System.out.println("This product already exists. You can update the quantity instead.");
                return;
            }
        }

        // Ürün miktarını doğrula ve al
        int productQuantity;
        do {
            System.out.print("Enter a quantity: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a numeric value for quantity.");
                sc.next(); // Hatalı girişi temizle
            }
            productQuantity = sc.nextInt();
            sc.nextLine(); // Newline karakterini temizle
            if (productQuantity <= 0) {
                System.out.println("Quantity should be a positive number.");
            }
        } while (productQuantity <= 0);

        // Ürün özelliklerini ayarla ve ID'yi oluştur
        pr.setProductName(productName);
        pr.setProductorName(productorName);
        pr.setQuantity(productQuantity);
        pr.setPart(part);
        productId(pr); // Ürün ID'sini ayarla
        products.put(pr.getId(), pr); // Ürünü Map'e ekle

        // Counter dosyasına güncel değeri yaz
        Product.saveCounter(Product.counter);

        // Ürünü dosyaya kaydet
        saveService.saveToFile(this.products);
    }

    // Ürün ID'sini oluşturur
    public void productId(Product pr) {
        try {
            // ID'yi ürün adı ve yıl bilgisiyle oluştur, counter'ı kullanarak benzersiz yap
            pr.setId(pr.getProductName().toUpperCase().substring(0, 2) + LocalDate.now().getYear() + Product.counter);
            Product.counter++;
        } catch (StringIndexOutOfBoundsException e) {
            // Eğer ürün adı kısa ise "NULL" kullanarak ID oluştur
            pr.setId("NULL" + LocalDate.now().getYear() + Product.counter);
            Product.counter++;
        }
    }
    // Ürünleri listeler
    public void listProduct(Map<String, Product> products) {
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "----------", "------------", "--------------", "--------", "-------", "------");
        for (Product product : products.values()) {
            System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
        }
    }



    // Ürünün miktarını günceller
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
            // Miktarı güncelle
            product.setQuantity(product.getQuantity() + quantity);
            System.out.println("Product quantity updated successfully. NEW STOCK: " + product.getQuantity());
        } else {
            System.out.println("The ID you have entered is not on the list. Please check again.");
        }

        // Güncellenen ürünü dosyaya kaydet
        saveService.saveToFile(this.products);
    }

    // Ürünü rafa yerleştirir
    public void putProductOnShelf(Map<String, Product> products) {
        System.out.print("Enter the product ID to place on the shelf: ");
        String productId = sc.nextLine().trim();
        Product product = products.get(productId);

        if (product != null) {
            int shelfNo;
            boolean isShelfAvailable;

            do {
                System.out.print("Enter a positive shelf number: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a valid number value for the Shelf Number.");
                    sc.next();
                }
                shelfNo = sc.nextInt();
                sc.nextLine();

                // Rafın dolu olup olmadığını kontrol et
                isShelfAvailable = true;
                for (Product p : products.values()) {
                    if (p.getShelf() != null && p.getShelf().equals("SHELF" + shelfNo)) {
                        System.out.println("This shelf is already occupied. Try a different one.");
                        isShelfAvailable = false;
                        break;
                    }
                }
            } while (shelfNo < 0 || !isShelfAvailable);

            // Ürünü rafa yerleştir
            product.setShelf("SHELF" + shelfNo);
            System.out.println("Product placed on shelf " + product.getShelf() + " successfully.");
        } else {
            System.out.println("The ID you have entered is not on the list. Please check again.");
        }

        // Güncellenen ürünü dosyaya kaydet
        saveService.saveToFile(this.products);
    }

    // Ürün çıkışı yapar
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

                // Mevcut stok miktarını kontrol et
                if (quantity > product.getQuantity()) {
                    System.out.println("Insufficient quantity in stock. MAXIMUM AVAILABLE: " + product.getQuantity());
                } else if (quantity <= 0) {
                    System.out.println("Quantity should be a positive number.");
                }
            } while (quantity <= 0 || quantity > product.getQuantity());

            // Miktarı azalt
            product.setQuantity(product.getQuantity() - quantity);
            System.out.println("Product output successful. REMAINING STOCK: " + product.getQuantity());
        } else {
            System.out.println("The ID you have entered is not on the list. Please check again.");
        }

        // Güncellenen ürünü dosyaya kaydet
        saveService.saveToFile(this.products);
    }

    // Ürünü listeden kaldırır
    public void removeProduct(Map<String, Product> products) {
        System.out.print("Enter the product ID to remove: ");
        String productId = sc.nextLine().trim();
        Product product = products.get(productId);

        if (product != null) {
            // Ürünü sil
            products.remove(productId);
            System.out.println("Product with ID " + productId + " has been removed successfully.");
        } else {
            System.out.println("The ID you entered is not in the list. Please check again.");
        }

        // Güncellenen listeyi dosyaya kaydet
        saveService.saveToFile(this.products);
    }

    // Listeyi tamamen sıfırlar
    public void clearProducts(Map<String, Product> products) {
        // Kullanıcıdan onay al: tüm ürünleri silmek isteyip istemediğini sorar
        System.out.print("Are you sure you want to clear all products? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        // İlk onay "yes" ise, ikinci bir onay iste
        if (confirmation.equals("yes")) {
            System.out.println("Warning: This action will remove all products from the list.");
            System.out.print("Are you really sure? Type 'yes' to confirm: ");
            String secondConfirmation = sc.nextLine().trim().toLowerCase();

            // İkinci onay da "yes" ise ürün listesini tamamen temizle
            if (secondConfirmation.equals("yes")) {
                products.clear(); // Ürünleri temizle
                System.out.println("All products have been cleared successfully.");
            } else {
                // İkinci onay verilmezse işlemi iptal et
                System.out.println("Action canceled. Products remain in the list.");
            }
        } else {
            // İlk onay verilmezse işlemi iptal et
            System.out.println("Action canceled. Products remain in the list.");
        }

        // Ürün listesi temizlendikten veya iptal edildikten sonra, mevcut durumu dosyaya kaydet
        saveService.saveToFile(this.products);
    }


}
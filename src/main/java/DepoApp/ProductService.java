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

    // ANSI renk kodları
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public ProductService() {
        products = saveService.loadFromFile();
    }// Yapıcı metot, uygulama başlarken dosyadan ürünleri yükler

    //Urun tanımlanır
    @Override
    public void addProduct(Map<String, Product> products) {


        int select = 1;
        do {
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

            // İşleme devam veya çıkış seçeneği sunma
            System.out.println("Press 1 to CONTINUE the process or 0 to EXIT.");
            try {
                select = sc.nextInt();
                sc.nextLine();  // Satır sonu karakterini temizlemek için
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 0 or 1.");
                sc.nextLine();  // Hatalı girişi temizlemek için
                select = 1;     // Hatalı giriş durumunda döngüyü devam ettirmek için
            }

        }while (select !=0);

        listProduct(products);
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

    // ürünleri listeler
    public void listProduct(Map<String, Product> products) {
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "----------", "------------", "--------------", "--------", "-------", "------");

        for (Product product : products.values()) {
            // Eğer ürün miktarı 0 ise kırmızı renkte yazdır
            if (product.getQuantity() == 0) {
                System.out.printf(ANSI_RED + "%-20s %-20s %-20s %-15s %-10s %-10s" + ANSI_RESET + "%n",
                        product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
            } else {
                System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n",
                        product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
            }
        }
    }

    //Tercihe göre sıralı liste
    public void listProductWithSorting(Map<String, Product> products) {
        // Kullanıcıdan sıralama tercihini al
        Scanner sc = new Scanner(System.in);
        int select = -1;

        while (select == -1) { // Geçerli bir seçim yapılana kadar döngü
            try {
                System.out.println("Choose sorting criteria: ");
                System.out.println("1. Sort by Quantity");
                System.out.println("2. Sort by Shelf Number");
                System.out.println("3. Sort by Product Name");
                System.out.println("4. Sort by Productor Name");  // Üretici ismine göre sıralama seçeneği
                System.out.print("Enter your choice (1-4): ");
                select = sc.nextInt();

                // Geçersiz seçim kontrolü
                if (select < 1 || select > 4) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    select = -1; // Döngüyü tekrar çalıştırmak için choice değeri sıfırlanır
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number between 1 and 4.");
                sc.nextLine(); // Geçersiz girdiyi temizlemek için
                select = -1; // Döngüyü tekrar çalıştırmak için choice değeri sıfırlanır
            }
        }

        List<Product> productList = new ArrayList<>(products.values());

        // Seçilen tercihe göre sıralama
        switch (select) {
            case 1: // Miktara göre sıralama
                Collections.sort(productList, Comparator.comparingInt(Product::getQuantity));
                break;
            case 2: // Raf numarasına göre sıralama
                Collections.sort(productList, (p1, p2) -> {
                    if (p1.getShelf() == null && p2.getShelf() == null) {
                        return 0; // İki ürün de rafsızsa eşit kabul edilir
                    }
                    if (p1.getShelf() == null) {
                        return -1; // Rafsız olanları öncelikli yapar
                    }
                    if (p2.getShelf() == null) {
                        return 1; // Rafsız olanları öncelikli yapar
                    }
                    return p1.getShelf().compareTo(p2.getShelf()); // Raf numarasına göre sıralama
                });
                break;
            case 3: // Ürün ismine göre sıralama
                Collections.sort(productList, Comparator.comparing(Product::getProductName));
                break;
            case 4: // Üretici ismine göre sıralama
                Collections.sort(productList, Comparator.comparing(Product::getProductorName));
                break;
        }

        // Başlıkları yazdır
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "----------", "------------", "--------------", "--------", "-------", "------");

        // Ürünleri listele
        for (Product product : productList) {
            // Eğer ürün miktarı 0 ise kırmızı renkte yazdır
            if (product.getQuantity() == 0) {
                System.out.printf(ANSI_RED + "%-20s %-20s %-20s %-15s %-10s %-10s" + ANSI_RESET + "%n",
                        product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
            } else {
                System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n",
                        product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
            }
        }
    }

    // Ürünün miktarını günceller
    public void enterProduct(Map<String, Product> products) {
        listProduct(products);

        int select = 1;
        do {


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

            // İşleme devam veya çıkış seçeneği sunma
            System.out.println("Press 1 to CONTINUE the process or 0 to EXIT.");
            try {
                select = sc.nextInt();
                sc.nextLine();  // Satır sonu karakterini temizlemek için
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 0 or 1.");
                sc.nextLine();  // Hatalı girişi temizlemek için
                select = 1;     // Hatalı giriş durumunda döngüyü devam ettirmek için
            }

        }while (select != 0 );

        //işlemler bitince güncel listeyi gösterir
        listProduct(this.products);
    }

    // Ürünü rafa yerleştirir
    public void putProductOnShelf(Map<String, Product> products) {
        int select = 1;

        do {
            // Raf değeri null olan ürünlerin varlığını kontrol et
            boolean hasUnassignedProducts = false;
            for (Product product : products.values()) {
                if (product.getShelf() == null) {
                    hasUnassignedProducts = true;
                    break;
                }
            }

            // Eğer raf değeri null olan ürün varsa, sadece bu ürünleri listeler
            if (hasUnassignedProducts) {
                System.out.println("Listing products without shelf assignment:");
                listUnassignedProducts();
            } else {
                System.out.println("All products are assigned shelves. Listing all products for update:");
                listProduct(products);  // Tüm ürünleri listele
            }

            System.out.print("Enter the product ID to place or update on the shelf: ");
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

                // Ürünü rafa yerleştir veya güncelle
                product.setShelf("SHELF" + shelfNo);
                System.out.println("Product placed on shelf " + product.getShelf() + " successfully.");
            } else {
                System.out.println("The ID you have entered is not on the list. Please check again.");
            }

            // Güncellenen ürünü dosyaya kaydet
            saveService.saveToFile(this.products);

            // İşleme devam veya çıkış seçeneği sunma
            System.out.println("Press 1 to CONTINUE the process or 0 to EXIT.");
            try {
                select = sc.nextInt();
                sc.nextLine();  // Satır sonu karakterini temizlemek için
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 0 or 1.");
                sc.nextLine();  // Hatalı girişi temizlemek için
                select = 1;     // Hatalı giriş durumunda döngüyü devam ettirmek için
            }
        } while (select != 0);

        // İşlem tamamlandıktan sonra ürünleri listele
        listProduct(products);
    }

    // Raf değeri null olan ürünleri listeleyen metot
    public void listUnassignedProducts() {
        System.out.println("Listing products without a shelf:");
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
        System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "----------", "------------", "--------------", "--------", "-------", "------");

        boolean found = false;
        for (Product product : products.values()) {
            if (product.getShelf() == null) {
                System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), "null");
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are no products without a shelf.");
        }
    }

    // Ürün çıkışı yapar
    public void productOutput(Map<String, Product> products) {
        listProduct(products);

        int select = 1;
        do {
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
                        try{
                            throw new InsufficentQuantityException("Insufficient quantity in stock." );
                        } catch (InsufficentQuantityException e) {
                            e.printStackTrace();
                            System.out.println("MAXIMUM AVAILABLE QUANTITY IS: " + product.getQuantity());

                        }
                        //System.out.println("Insufficient quantity in stock. MAXIMUM AVAILABLE: " + product.getQuantity());
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
            // İşleme devam veya çıkış seçeneği sunma
            System.out.println("Press 1 to CONTINUE the process or 0 to EXIT.");
            try {
                select = sc.nextInt();
                sc.nextLine();  // Satır sonu karakterini temizlemek için
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 0 or 1.");
                sc.nextLine();  // Hatalı girişi temizlemek için
                select = 1;     // Hatalı giriş durumunda döngüyü devam ettirmek için
            }
        } while (select != 0);

        // İşlem tamamlandıktan sonra ürünleri listele
        listProduct(products);
    }

    // Ürün adını veya üretici adını kullanarak ürün arama
    public void searchProduct(Map<String, Product> products) {
        System.out.print("Enter product name or productor name to search: ");
        String searchQuery = sc.nextLine().toUpperCase().trim();

        // Arama sonuçlarını tutmak için bir liste
        List<Product> searchResults = new ArrayList<>();

        // Aranan ada veya üreticiye göre ürünleri filtrele
        for (Product product : products.values()) {
            if (product.getProductName().contains(searchQuery) || product.getProductorName().contains(searchQuery)) {
                searchResults.add(product);
            }
        }

        // Arama sonuçlarını listele
        if (!searchResults.isEmpty()) {
            System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCTOR NAME", "QUANTITY", "PART", "SHELF");
            System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", "----------", "------------", "--------------", "--------", "-------", "------");
            for (Product product : searchResults) {
                System.out.printf("%-20s %-20s %-20s %-15s %-10s %-10s%n", product.getId(), product.getProductName(), product.getProductorName(), product.getQuantity(), product.getPart(), product.getShelf());
            }
        } else {
            System.out.println("No products found with the given name or productor name.");
        }
    }


    // Ürünü listeden kaldırır
    public void removeProduct(Map<String, Product> products) {
        listProduct(products);

        int select = 1;
        do {
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

            // İşleme devam veya çıkış seçeneği sunma
            System.out.println("Press 1 to CONTINUE the process or 0 to EXIT.");
            try {
                select = sc.nextInt();
                sc.nextLine();  // Satır sonu karakterini temizlemek için
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 0 or 1.");
                sc.nextLine();  // Hatalı girişi temizlemek için
                select = 1;     // Hatalı giriş durumunda döngüyü devam ettirmek için
            }
        } while (select != 0);

        // İşlem tamamlandıktan sonra ürünleri listele
        listProduct(products);
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
        listProduct(products);
    }


}
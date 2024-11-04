package DepoApp;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Product {

    private String id;
    public static int counter = loadCounter(); // ID sayma
    private static final String COUNTER_FILE ="counter.txt";  // ID'yi saklayacağımız dosya

    private String productName;
    private String productorName;
    private int quantity; // miktar
    private String part; // birim
    private String shelf; // raf

    public Product(String id, String productName, String productorName, int quantity, String part, String shelf) {
        this.id = id;
        this.productName = productName;
        this.productorName = productorName;
        this.quantity = quantity;
        this.part = part;
        this.shelf = shelf;
    }

    // Dosyadan counter değerini yükleyen statik metot
    public static int loadCounter() {
        try {
            // Dosyanın mevcut olup olmadığını kontrol et
            if (Files.exists(Paths.get(COUNTER_FILE))) {
                // Dosyayı okuyucu ile aç ve ilk satırı oku
                BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE));
                String line = reader.readLine(); // İlk satırdaki sayacı oku
                reader.close(); // Okuyucuyu kapat
                return Integer.parseInt(line); // Satırdaki değeri integer'a çevir ve döndür
            }
        } catch (IOException e) {
            e.printStackTrace(); // Herhangi bir IO hatasında hatayı yazdır
        }
        return 1000; // Dosya yoksa veya hata oluşursa varsayılan olarak 1000 döner
    }

    // Counter değerini dosyaya yazan metot
    public static void saveCounter(int counter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            // Counter değerini dosyaya yaz
            writer.write(String.valueOf(counter)); // Counter'ı String'e çevir ve yaz
        } catch (IOException e) {
            e.printStackTrace(); // Herhangi bir IO hatasında hatayı yazdır
        }
    }


    // Getter ve Setter metodları
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getProductName() {return productName;}

    public void setProductName(String productName) {this.productName = productName;}

    public String getProductorName() {return productorName;}

    public void setProductorName(String productorName) {this.productorName = productorName;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getPart() {return part;}

    public void setPart(String part) {this.part = part;}

    public String getShelf() {return shelf;}

    public void setShelf(String shelf) {this.shelf = shelf;}
}

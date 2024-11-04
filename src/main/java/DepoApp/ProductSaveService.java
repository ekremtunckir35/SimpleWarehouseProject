package DepoApp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

// Ürünleri dosyaya kaydetmek ve dosyadan yüklemek için kullanılan sınıf
public class ProductSaveService {
    private static final String FILE_PATH = "products.json"; // Ürünlerin kaydedileceği dosya yolu

    // Ürünleri dosyaya kaydetme işlemi
    public void saveToFile(Map<String, Product> products) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) { // Dosya yazıcı oluştur
            Gson gson = new Gson(); // Gson nesnesi oluştur
            gson.toJson(products, writer); // Ürünleri JSON formatında dosyaya yaz
        } catch (IOException e) {
            // Hata oluşursa hata mesajını yazdır
            System.out.println("Error while saving products: " + e.getMessage());
        }
    }

    // Dosyadan ürünleri okuma işlemi
    public Map<String, Product> loadFromFile() {
        Map<String, Product> products = new LinkedHashMap<>(); // Ürünleri tutacak LinkedHashMap oluştur
        try (FileReader reader = new FileReader(FILE_PATH)) { // Dosya okuyucu oluştur
            Gson gson = new Gson(); // Gson nesnesi oluştur
            Type type = new TypeToken<LinkedHashMap<String, Product>>() {}.getType(); // Ürünlerin tipini belirle
            products = gson.fromJson(reader, type); // JSON verisini ürün haritasına dönüştür
            if (products == null) { // Eğer ürünler null ise, boş bir LinkedHashMap oluştur
                products = new LinkedHashMap<>();
            }
        } catch (IOException e) {
            // Hata oluşursa hata mesajını yazdır
            System.out.println("Error while loading products: " + e.getMessage());
        }
        return products; // Ürün haritasını döndür
    }
}

import java.time.LocalDate;
import java.util.*;

public class ProductService {
    Map<String, Product> products = new LinkedHashMap<>();
    Scanner sc = new Scanner(System.in);
    public void addProduct () {
        Product pr = new Product(null, null, null, 0, null, null);
        System.out.println(" Enter a product name");
        String productName = sc.nextLine();
        System.out.println(" Enter a productor name");
        String productorName = sc.nextLine();
        System.out.println(" Enter a quantity");
        int productQuantity = sc.nextInt();
        sc.nextLine();
        System.out.println(" Enter a part");
        String part = sc.nextLine();
        //kullanici verisi urune ekledik.
        pr.setProductName(productName);
        pr.setProductorName(productorName);
        pr.setQuantity(productQuantity);
        pr.setPart(part);
        // id tanimlama

        pr.setId(pr.getProductName().substring(0,1) + LocalDate.now().getYear() + Product.counter);
        Product.counter++;


        if (!this.products.containsKey(pr.getId())) {
            products.put(pr.getId(), pr);

        } else {
            System.out.println("This product is already defined.");
        }
    }
        public void listProduct( Map <String, Product> products) {

            System.out.printf("%-15s %-12s %-15s %-7s %-10s %-10s%n"," PRODUCT ID ","PRODUCT NAME ", "PRODUCTOR NAME ","QUANTIY ","PART  "," SHELF   ");
            System.out.printf("%-15s %-12s %-15s %-7s %-10s %-10s%n"," ------- ","--------------- ","------------ ","------ ","-----  ","----    ");
            List<Product> productValue =new ArrayList<>(products.values());

            for ( Product product: productValue  ){
                System.out.printf("%-15s %-12s %-15s %-7s %-10s %-10s%n" ,  product.getId(), product.getProductName() , product.getProductorName() , product.getQuantity(), product.getPart() , product.getShelf() );
            }

        }

































    //Ummuhan
    public void productEter(Map <String, Product> products) {
        System.out.printf("%-15s %-12s %-15s %-7s %-10s %-10s%n"," PRODUCT ID ","PRODUCT NAME ", "PRODUCTOR NAME ","QUANTIY ","PART  "," SHELF   ");
        System.out.printf("%-15s %-12s %-15s %-7s %-10s %-10s%n"," ------- ","--------------- ","------------ ","------ ","-----  ","----    ");
        List<Product> productValue =new ArrayList<>(products.values());
        System.out.print("Enter the product ID to add stock: ");
        String id = sc.nextLine();
        Product product = products.get(id);

        if (product != null) {
            System.out.print("Enter quantity to add: ");
            int additionalQuantity = sc.nextInt();
            sc.nextLine(); // buffer temizliği
            product.setQuantity(product.getQuantity() + additionalQuantity);
            System.out.println("Product quantity updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    /**
     Ürün ID'sini Almak:
     * Kullanıcıdan stok eklemek istediği ürünün ID'si istenir.
     * Girilen ID, products haritasında bir anahtar olarak aranır ve eşleşen ürün varsa product değişkenine atanır.
     * Ürün Mevcutsa Stok Eklemek:

     * Eğer product değişkeni null değilse, yani ürün bulunmuşsa:
     * Kullanıcıdan eklenecek miktar istenir.
     * sc.nextInt() ile girilen değer additionalQuantity değişkenine atanır.
     * sc.nextLine() komutu, nextInt() sonrası tamponu temizlemek için kullanılır, böylece kullanıcıdan sonraki girişlerde sorun yaşanmaz.
     * Ürünün mevcut miktarına (product.getQuantity()) yeni eklenen miktar eklenir ve bu toplam değer setQuantity() ile ürünün quantity alanına atanır.
     * Stok başarıyla güncellendiğinde, "Product quantity updated successfully." mesajı ekrana yazdırılır.
     * Ürün Bulunamazsa:

     * Eğer product null ise, yani belirtilen ID'ye sahip bir ürün bulunmazsa, "Product not found." mesajı ekrana yazdırılır.
     * Bu yapı, kullanıcıya, mevcut bir ürüne kolayca stok ekleyebilme imkânı sunar.
     */
    //Leven







}

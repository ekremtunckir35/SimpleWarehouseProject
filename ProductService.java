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


        public void productId(Product pr){  pr.setId(pr.getProductName().toUpperCase().substring(0,2) + LocalDate.now().getYear() + Product.counter); Product.counter++;  }
        //Tugce






























    public void putProductOnShelf(Map<String, Product> products) {
        try {
            System.out.println("Enter the product ID to place on the shelf:");
            String productId = sc.nextLine().trim();
            if (!products.containsKey(productId)) {
                System.out.println("Product not found with this ID.");
                return;
            }   Product product = products.get(productId);
            int shelf = 0;
            do {
                System.out.println("Raf no giriniz :");
                shelf = sc.nextInt();
                if (shelf < 0 ){
                    System.out.println("Negatif değer girmeyiniz");}
            }while (shelf < 0);
            product.setShelf("SHELF" + shelf);
            System.out.println("Product placed on shelf " + product.getShelf() + " successfully.");
            List<Product> productValue = new ArrayList<>(products.values());//Envanterdeki mevcut ürünler listesini yapar /* silinebilir kontrol.*/
            System.out.println("Current products in the inventory:");
            for (Product p : productValue) {
                System.out.println("Product ID : " + p.getId() + ", Shelf : " + p.getShelf());}   //
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please try again.");
            sc.nextLine(); // Clear the buffer in case of invalid input
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    //Leven
    // Ürün çıkarma metodu
    public void productOutput() {
        System.out.println("Enter the product ID for output:");
        String productId = sc.nextLine();
        Product product = products.get(productId);

        if (product != null) {
            System.out.println("Enter the quantity to remove:");
            int quantity = sc.nextInt();
            sc.nextLine();

            if (quantity <= product.getQuantity()) {
                product.setQuantity(product.getQuantity() - quantity);
                System.out.println("Product output successful.");
            } else {
                System.out.println("Insufficient quantity in stock.");
            }
        } else {
            System.out.println("Product not found with this ID.");
        }
    }




}

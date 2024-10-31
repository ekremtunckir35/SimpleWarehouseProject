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



    //Tugce





























    //Ummuhan
    public void productShelf(Map <String, Product> products) {
        System.out.println("Urun id giriniz.");
        String id = sc.nextLine();
        if (products.containsKey(id)){
            try {
                System.out.println("Urun için raf no giriniz");
                int rafNo = sc.nextInt();
                products.get(id).setShelf("SHELF" + rafNo);
            } catch (InputMismatchException e) {
                System.out.println("Raf no için sayısal veri giriniz");
            }

        } else {
            System.out.println("Girdiğiniz id kayıtlı değil. Listeden kontrol ederek tekrar deneyiniz.");
        }
    }













    //Leven







}

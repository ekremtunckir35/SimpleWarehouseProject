import java.util.List;
import java.util.Scanner;

public class DepoApp {
static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {



        start();




    }



    public static void start () {

        ProductService ps = new ProductService();

        int select;

        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("========= Depo Management System =========");
            System.out.println("1- Define a product");
            System.out.println("2- List a product");
            System.out.println("3- Enter a product");
            System.out.println("4- Put  the product on the shelf");
            System.out.println("5- Product output");
            System.out.println("0- EXIT");
            System.out.println("Select an Option");
            select = sc.nextInt();

            if (select == 1) {

                ps.addProduct();
                mainMenu();
            } else if (select == 2) {

                ps.listProduct(ps.products);

            } else if (select == 3) {

            } else if (select == 4) {
                ps.putProductOnShelf(ps.products);
            } else if (select == 5) {

            } else if (select == 0) {

            } else  {

                System.out.println(" Invalid selection please try again");
            }


        } while (select != 0);


    }


    private static void mainMenu(){
        System.out.println("1 - MAIN MENU");
        int select = scan.nextInt();
    }
}

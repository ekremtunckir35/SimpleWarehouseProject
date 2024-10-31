public class Product {

   private String id;

    public static int counter = 1000; // id sayma


    private String productName;
    private String productorName;

    private int quantity; //miktar

    private String part; //birim

    private String shelf; //raf

    public Product(String id, String productName, String productorName, int quantity, String part, String shelf) {
        this.id = id;
        this.productName = productName;
        this.productorName = productorName;
        this.quantity = quantity;
        this.part = part;
        this.shelf = shelf;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductorName() {
        return productorName;
    }

    public void setProductorName(String productorName) {
        this.productorName = productorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }
}

import java.util.Map;

public interface DepoInterface {
    void addProduct(Map<String, Product> products);
    void listProduct(Map<String, Product> products);
    void enterProduct(Map<String, Product> products);
    void putProductOnShelf(Map<String, Product> products);
    void productOutput(Map<String, Product> products);
}

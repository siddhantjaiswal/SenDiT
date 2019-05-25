package in.example.siddhantjaiswal.sendit;

public class Product {
    private String description;
    private String id;
    private String name;
    private String price;

    public Product(String id, String name, String price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

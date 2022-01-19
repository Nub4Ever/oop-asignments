package entitites;

public final class Gift {
    private String productName;
    private Double price;
    private String category;

    public Gift(final String productName, final  Double price, final String category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public Gift() {

    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public void setCategory(final String category) {
        this.category = category;
    }
}

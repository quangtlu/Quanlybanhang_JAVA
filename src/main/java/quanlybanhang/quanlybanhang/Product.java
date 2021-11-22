package quanlybanhang.quanlybanhang;

import java.io.Serializable;

public class Product implements Serializable {
    private int productID;
    private String name;
    private double price;
    private double discount;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Product(int productID, String name, double price, double discount) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public Product() {
    }

}

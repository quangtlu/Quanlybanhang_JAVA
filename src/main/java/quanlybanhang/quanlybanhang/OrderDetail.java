package quanlybanhang.quanlybanhang;

public class OrderDetail extends Product{
    private int quantity;
    private double sum;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setSum() {
        this.sum = getQuantity()*(getPrice() - (getPrice()*(getDiscount())/100));
    }

    public double getSum() {
        return sum;
    }

    public OrderDetail(int productID, String name, double price, double discount, int quantity, double sum) {
        super(productID, name, price, discount);
        this.quantity = quantity;
        this.sum = sum;
    }

    public OrderDetail(int quantity, double sum) {
        this.quantity = quantity;
        this.sum = sum;
    }

    public OrderDetail(int productID, String name, double price, double discount) {
        super(productID, name, price, discount);
    }

    public OrderDetail() {
    }
}

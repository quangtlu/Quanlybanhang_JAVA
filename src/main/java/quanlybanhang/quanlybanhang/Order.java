package quanlybanhang.quanlybanhang;
import java.io.Serializable;
import java.util.LinkedList;


public class Order implements Serializable {
    private int orderID;
    private String orderDate;
    private double total;
    private boolean status;
    private LinkedList<OrderDetail> orderDetails;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal() {
        double total = 0;
        for(OrderDetail odt:orderDetails){
            odt.setSum();
            total += odt.getSum();
        }
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LinkedList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    public void addOderDetail(OrderDetail odt){
        this.orderDetails.add(odt);
    }
    public void updateProducts(int index,OrderDetail odt) {
        this.orderDetails.set(index,odt);
    }
    public void RemoveProduct(OrderDetail odt) {
        this.orderDetails.remove(odt);
    }

    public void setProducts(LinkedList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Order(int orderID, String orderDate, double total, boolean status) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.orderDetails = new LinkedList<OrderDetail>();
    }

    public Order() {
        this.orderDetails = new LinkedList<OrderDetail>();
    }
}

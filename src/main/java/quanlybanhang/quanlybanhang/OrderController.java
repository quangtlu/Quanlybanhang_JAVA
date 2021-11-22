package quanlybanhang.quanlybanhang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import javafx.scene.control.DatePicker;
import javafx.util.converter.BooleanStringConverter;

import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private TableColumn<Order, String> orderDate_col;

    @FXML
    private TableColumn<Order, Integer> orderID_col;

    @FXML
    private TextField orderID_input;

    @FXML
    private DatePicker orderDate_input;


    @FXML
    private TableColumn<Product, Double> productDiscount_col;

    @FXML
    private TableColumn<Product, Integer> productID_col;
    @FXML
    private TextField quantity_input;

    @FXML
    private TableColumn<Product, String> productName_col;

    @FXML
    private TableColumn<Product, Double> productPrice_col;

    @FXML
    private TableColumn<Order, Boolean> status_col;

    @FXML
    private TableView<Order> table;

    @FXML
    private TableView<Product> table_product;

    @FXML
    private TableColumn<Order, Double> totalMoney_col;


    private ObservableList<Order> orderList;
    private ObservableList<Product> productList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();

//        Đọc file productList.dat
        boolean cont = true;
        try {
            FileInputStream fis = new FileInputStream("D://Java/quanlybanhang/productList.dat");

            while (cont) {
                ObjectInputStream input = new ObjectInputStream(fis);
                Product obj = (Product) input.readObject();
                if (obj != null) {
                    productList.add(obj);
                } else {
                    cont = false;
                }
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
        productID_col.setCellValueFactory(new PropertyValueFactory<Product,Integer>("productID"));
        productName_col.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        productPrice_col.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        productDiscount_col.setCellValueFactory(new PropertyValueFactory<Product,Double>("discount"));
        table_product.setItems(productList);

        //        Đọc file orderList.dat
        boolean cont1 = true;
        try {
            FileInputStream fis1 = new FileInputStream("D://Java/quanlybanhang/orderList.dat");

            while (cont1) {
                ObjectInputStream input = new ObjectInputStream(fis1);
                Order obj1 = (Order) input.readObject();
                if (obj1 != null) {
                    orderList.add(obj1);
                } else {
                    cont1 = false;
                }
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
        orderID_col.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderID"));
        orderDate_col.setCellValueFactory(new PropertyValueFactory<Order,String>("orderDate"));
        status_col.setCellValueFactory(new PropertyValueFactory<Order,Boolean>("status"));
        totalMoney_col.setCellValueFactory(new PropertyValueFactory<Order,Double>("total"));

        //Sửa status trên table view
        status_col.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        status_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, Boolean> event) {
                Order order = event.getRowValue();
                for(int i=0; i< orderList.size(); i++){
                    if(orderList.get(i).equals(order)){
                        order.setStatus(event.getNewValue());
                        orderList.set(i,order);
                    }
                }
            }
        });
        table.setItems(orderList);

    }
    public void addOrder(ActionEvent event){

        Product selected = table_product.getSelectionModel().getSelectedItem();
        OrderDetail odt = new OrderDetail(selected.getProductID(),selected.getName(),selected.getPrice(),selected.getDiscount());
        odt.setQuantity(Integer.parseInt(quantity_input.getText()));
        boolean flag = true;
        int index = 0;
        for(Order order:orderList){

            if(order.getOrderID() == Integer.parseInt(orderID_input.getText())){
                order.addOderDetail(odt);
                order.setTotal();
                orderList.set(index,order);
                flag = false;
            }

            index ++;
        }
        if(flag){
            Order orderObj = new Order();
            orderObj.addOderDetail(odt);
            orderObj.setTotal();
            orderObj.setStatus(false);
            orderObj.setOrderID(Integer.parseInt(orderID_input.getText()));
            orderObj.setOrderDate(orderDate_input.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            orderList.add(orderObj);
        }
    }
    public void removeOrder(ActionEvent event){
        Order selected = table.getSelectionModel().getSelectedItem();
        orderList.remove(selected);
    }
    public void saveFile(ActionEvent event){
        try {
            new FileOutputStream("D://Java/quanlybanhang/orderList.dat").close(); //xóa tất cả object trong file

            //Thêm tất cả object trong productList vào file
            for (Order obj : orderList){
                FileOutputStream f = new FileOutputStream("D://Java/quanlybanhang/orderList.dat",true);
                ObjectOutputStream oStream = new ObjectOutputStream(f);
                oStream.writeObject(obj);
                oStream.close();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save file");
            alert.setHeaderText("Saved file successfully");
            alert.setContentText("Data have been saved successfully!");

            alert.showAndWait();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LoadProductView (ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("product-view.fxml"));
        Parent parentView = loader.load();
        Scene scene = new Scene(parentView);
        stage.setScene(scene);
    }

}

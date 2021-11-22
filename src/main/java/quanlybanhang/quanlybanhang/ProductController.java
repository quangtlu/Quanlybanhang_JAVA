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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private TableColumn<Product, Double> discount_col;

    @FXML
    private TextField discount_input;


    @FXML
    private TableColumn<Product, String> name_col;

    @FXML
    private TextField name_input;

    @FXML
    private TableColumn<Product, Double> price_col;

    @FXML
    private TextField price_input;

    @FXML
    private TableColumn<Product, Integer> productID_col;

    @FXML
    private TextField productID_input;

    @FXML
    private TableView<Product> table;


    private ObservableList<Product> productList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        //Sửa productID trên table view
        productID_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        productID_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Integer> event) {
                Product product = event.getRowValue();
                for(int i=0; i< productList.size(); i++){
                    if(productList.get(i).equals(product)){
                        product.setProductID(event.getNewValue());
                        productList.set(i,product);
                    }
                }
            }
        });
        name_col.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        //Sửa name trên table view
        name_col.setCellFactory(TextFieldTableCell.forTableColumn());
        name_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, String> event) {
                Product product = event.getRowValue();
                for(int i=0; i< productList.size(); i++){
                    if(productList.get(i).equals(product)){
                        product.setName(event.getNewValue());
                        productList.set(i,product);
                    }
                }
            }
        });
        price_col.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        //Sửa price trên table view
        price_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        price_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Double> event) {
                Product product = event.getRowValue();
                for(int i=0; i< productList.size(); i++){
                    if(productList.get(i).equals(product)){
                        product.setPrice(event.getNewValue());
                        productList.set(i,product);
                    }
                }
            }
        });

        discount_col.setCellValueFactory(new PropertyValueFactory<Product,Double>("discount"));
        //Sửa discount trên table view
        discount_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        discount_col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Double> event) {
                Product product = event.getRowValue();
                for(int i=0; i< productList.size(); i++){
                    if(productList.get(i).equals(product)){
                        product.setDiscount(event.getNewValue());
                        productList.set(i,product);
                    }
                }
            }
        });


        table.setItems(productList);

    }
    public void addProduct(ActionEvent event){


        boolean flag = true;
        for(Product product:productList){
            if(product.getProductID() == Integer.parseInt(productID_input.getText())){
                flag = false;
            }
        }
        if(flag){
            Product productObj = new Product();
            productObj.setProductID(Integer.parseInt(productID_input.getText()));
            productObj.setName(name_input.getText());
            productObj.setPrice(Double.parseDouble(price_input.getText()));
            productObj.setDiscount(Double.parseDouble(discount_input.getText()));
            productList.add(productObj);
            productID_input.clear();
            name_input.clear();
            price_input.clear();
            discount_input.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error alert");
            alert.setHeaderText("Can't add product");
            alert.setContentText("The Product ID has existed!");

            alert.showAndWait();
        }



    }
    public void deleteProduct(ActionEvent event){
        Product selected = table.getSelectionModel().getSelectedItem();
        productList.remove(selected);

    }
    public void saveFile(ActionEvent event){
        try {
            new FileOutputStream("D://Java/quanlybanhang/productList.dat").close(); //xóa tất cả object trong file

            //Thêm tất cả object trong productList vào file
            for (Product obj : productList){
                FileOutputStream f = new FileOutputStream("D://Java/quanlybanhang/productList.dat",true);
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
    public void LoadOrderView(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("order-view.fxml"));
        Parent parentView = loader.load();
        Scene scene = new Scene(parentView);
        stage.setScene(scene);

    }

}

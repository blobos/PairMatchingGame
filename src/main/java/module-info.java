module com.example.pairmatchinggame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pairmatchinggame to javafx.fxml;
    exports com.example.pairmatchinggame;
}
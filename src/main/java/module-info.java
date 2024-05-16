module com.empresa.actividad019_ds_analysis {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.empresa.actividad019_ds_analysis to javafx.fxml;
    exports com.empresa.actividad019_ds_analysis;
}
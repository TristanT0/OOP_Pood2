module pood.eepilinepood {
    requires javafx.controls;
    requires javafx.fxml;


    opens pood.eepilinepood to javafx.fxml;
    exports pood.eepilinepood;
}
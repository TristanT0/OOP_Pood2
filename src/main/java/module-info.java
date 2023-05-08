module pood.eepilinepood2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens pood.eepilinepood2 to javafx.fxml;
    exports pood.eepilinepood2;
}
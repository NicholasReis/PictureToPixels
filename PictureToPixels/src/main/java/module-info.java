module PictureToPixels.PictureToPixels {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.swing;

    opens PictureToPixels.PictureToPixels to javafx.fxml;
    exports PictureToPixels.PictureToPixels;
}
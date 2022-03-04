import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser.ExtensionFilter;

public class WritableCanvasCreator {
    public WritableImage imgToWritable(Image img) {
        // Creates a writable image with the same dimensions as the image sent
        WritableImage wImage = new WritableImage((int) img.getWidth(), (int) img.getHeight());
        // Creates a pixel reader to read the image sent
        PixelReader pReader = img.getPixelReader();
        // Creates a pixel writer to write to the global writable image
        PixelWriter pWriter = wImage.getPixelWriter();
        // for every pixel read from the image, it writes to the global writable image
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                // Copies each color from the original image to the writable image
                pWriter.setColor(j, i, pReader.getColor(j, i));
            }
        }

        return wImage;
    }

}

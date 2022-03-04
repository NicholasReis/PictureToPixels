
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

public class PhotoEditor {
    public WritableImage pixelize(Image img, int bitSize) {
        // Sets the global writable image to the global image dimensions
        WritableImage wImage = new WritableImage((int) img.getWidth(), (int) img.getHeight());
        // Creates a new pixel reader to read the image pixels
        PixelReader pReader = img.getPixelReader();
        // Creates a new pixel writer to write pixels to the image
        PixelWriter pWriter = wImage.getPixelWriter();

        // iterates through the image by bitSize blocks (within there are for loops to
        // deal with pixels within the block
        for (int x = 0; x < (int) img.getHeight() - bitSize; x += bitSize) {
            // increments by bitsize to create a square of space in the image
            for (int y = 0; y < (int) img.getWidth() - bitSize; y += bitSize) {
                // creates an arraylist of colours that will find average colour
                ArrayList<Color> averagePixels = new ArrayList<Color>();
                // for each vertical pixel within the bitSize chunk
                for (int i = x; i < x + bitSize; i++) {
                    // for each horizontal pixel within the bitSize chunk
                    for (int j = y; j < y + bitSize; j++) {
                        // Adds the pixel
                        averagePixels.add(pReader.getColor(y, x));
                    }
                }

                // Sets the average value of each color to 0 for each loop to start new
                double averageReds = 0;
                double averageGreens = 0;
                double averageBlues = 0;
                double averageOpacities = 0;
                // For each color in the pixel arraylist it grabs the average of each RGBA color
                // value
                for (Color pixel : averagePixels) {
                    averageReds += pixel.getRed();
                    averageGreens += pixel.getGreen();
                    averageBlues += pixel.getBlue();
                    averageOpacities += pixel.getOpacity();
                }

                // Creates a new color by averaging the values by the size of the pixel array
                Color averageOfNearbyPixels = new Color(
                        averageReds /= averagePixels.size(),
                        averageGreens /= averagePixels.size(),
                        averageBlues /= averagePixels.size(),
                        averageOpacities /= averagePixels.size());

                // For each vertical pixel in the bitSize square chunk
                for (int i = x; i < x + bitSize; i++) {
                    // For each horizontal pixel in the bitSize square chunk
                    for (int j = y; j < y + bitSize; j++) {
                        // Writes the same colour to every pixel in this bit chunk
                        pWriter.setColor(j, i, averageOfNearbyPixels);
                    }
                }

            }
        }
        return wImage;
    }

}

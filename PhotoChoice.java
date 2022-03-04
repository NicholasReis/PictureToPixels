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

public class PhotoChoice {
    public Image photoChoice() {
        // Declares an empty, but initialized WritableImage to return in case of failure
        try {
            // Initializes a new window for choosing a file
            FileChooser fc = new FileChooser();
            // Initializes a file object to a file chosen by the user
            File f = fc.showOpenDialog(new Stage());

            // If the file exists
            if (f != null) {
                // Creates a new image from the file
                Image img = new Image(f.toURI().toURL().toExternalForm());
                return img;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Image("");
    }
    /*
     * 
     * 
     * private void saveImage(ActionEvent event, ImageView imgV){
     * try{
     * //Creates another filechooser window to save the pixelized image
     * FileChooser fc = new FileChooser();
     * 
     * //sets the intial filename as "untitled"
     * fc.setInitialFileName("Untitled");
     * //Sets an applicable extension to the field (has to be manually entered)
     * ExtensionFilter png = new ExtensionFilter("PNG (*.png)", "*.png");
     * //adds the png filter to the filechooser so it can be selected
     * fc.getExtensionFilters().add(png);
     * //shows the file save dialog
     * File f = fc.showSaveDialog(null);
     * 
     * //if the user has chosen a file
     * if(f!=null){
     * //Gets the path to where they want it saved
     * String fileName = f.getCanonicalPath();
     * //Creates a new file object with the path the user provided
     * File imgSave = new File(fileName);
     * //grabs the name without the extension
     * String extension = fileName.substring(fileName.lastIndexOf(".")+1,
     * fileName.length());
     * //Honestly not 100% sure how this works, I grabbed it from stack overflow for
     * how to save
     * //images with javafx, but so far it has worked in a few projects to save
     * files
     * ImageIO.write(SwingFXUtils.fromFXImage(img, null), extension, imgSave);
     * }
     * }catch(Exception e){
     * e.printStackTrace();
     * }
     * }
     */
}

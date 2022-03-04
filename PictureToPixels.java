import javafx.event.EventHandler;
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

public class PictureToPixels extends Application{
    //sets the pixel ration to 1 by default
    int bitSize = 1;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        GUI gui = new GUI(stage);
        //sets the title for the window
        stage.setTitle("Picture to Pixel");
        //puts the scene into the stage
        gui.welcomeScreen();
        //Shows the stage
        stage.show();
    }

}
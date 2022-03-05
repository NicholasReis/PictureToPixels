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

public class GUI{
    Stage stage;
    
    public GUI(Stage tempStage){
        stage = tempStage;
    }

    public void welcomeScreen(){
        //Label to welcome
        Label welcomeLabel = new Label("Welcome. To continue please choose a photo.");
        //Button to select a photo through file explorer
        Button imageSelectButton = new Button("Select photo");
        //Calls photoChoice with the related action event when the button is pressed
        imageSelectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override  
                public void handle(ActionEvent arg0) {
                    selectionScreen();
                }  
            });

        //Adds the label and button to a vertical box
        VBox group = new VBox(10, welcomeLabel, imageSelectButton);
        //centers the vertical box
        group.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(group));
    }

    public void selectionScreen(){
        PhotoChoice PC = new PhotoChoice();
        Pixelator pixelator = new Pixelator();

        //Creates a new imageview to display the writable image
        ImageView pixelizedImage = new ImageView(PC.getWritableImage());

        //Creates a button to select a new image
        Button imageSelectButton = new Button("Select photo");
        //On button click calls photoChoice with the related action event as a parameter
        imageSelectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override  
                public void handle(ActionEvent arg0) {
                    selectionScreen();
                }  
            });

        //sets the window size to fit 1200x800
        pixelizedImage.setFitHeight(500);
        pixelizedImage.setFitWidth(600);
        //preserves ratio so it doesn't warp image
        pixelizedImage.setPreserveRatio(true);

        //Creates a slider between 1 and 64 for the pixel ratio to pixelize,
        //@2 it would take the average of every 2x2 pixels and average the colors,
        //@40 it would take the average of ever 40/40 pixels and average the colors, etc
        Slider pixelizeMeter = new Slider(1,64, 1);

        //Adds a listener to check the value of the slider
        pixelizeMeter.valueProperty().addListener(
            (observable, oldvalue, newvalue) ->
            {
                //sets the bitsize to the new value of the slider
                final int bitSize = newvalue.intValue();
                //calls pixelize
                final WritableImage pixelImage = pixelator.pixelize(PC.getWritableImage(), bitSize);
                //sets the imageview to display the now pixelized image
                pixelizedImage.setImage(pixelImage);
                System.out.println("Value Changed.");
            } );

        //Label for the slider
        Label sliderLabel = new Label("Pixelize Meter");

        //Button to save
        Button saveButton = new Button("Save");
        //When the button is pressed it calls saveImage

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override  
                public void handle(ActionEvent arg0) {
                    PC.saveImage(pixelizedImage.getImage());
                }  
            });
        //Adds the label and slider to a vertical box
        VBox meterAndLabel = new VBox(2, sliderLabel, pixelizeMeter);
        //sets the label and slider to the bottome center
        meterAndLabel.setAlignment(Pos.BOTTOM_CENTER);

        //creates a horizontal box that contains the image selection button, slider and label vbox, and save button
        HBox underImage = new HBox(10, imageSelectButton, meterAndLabel, saveButton);

        //sets horizontal box to the bottome center (underneath the imageview of the selected picture)
        underImage.setAlignment(Pos.BOTTOM_CENTER);

        //new vertical box to store both the imageview and the toolbar
        VBox group = new VBox(10, pixelizedImage, underImage);
        //sets the new vbox to the center of the window
        group.setAlignment(Pos.BASELINE_CENTER);

        stage.setScene(new Scene(group));
    }

    public void imageScreen(){
        Button imageSelectButton = new Button("Select photo");
        //On button click calls photoChoice with the related action event as a parameter
        imageSelectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override  
                public void handle(ActionEvent arg0) {
                    selectionScreen();
                }  
            });
        //Adds the label and button to a vertical box
        VBox group = new VBox(10, imageSelectButton);
        //centers the vertical box
        group.setAlignment(Pos.CENTER);
        //Adds the label and button to a vertical box
        group = new VBox(10, imageSelectButton);
        //centers the vertical box
        group.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(group));
    }
}

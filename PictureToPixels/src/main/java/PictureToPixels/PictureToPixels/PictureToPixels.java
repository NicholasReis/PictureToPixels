 

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
	//initializing global variables
    Image img;
    Scene scene;
    WritableImage wImage;
    Stage stage = new Stage();
	//sets the pixel ration to 1 by default
    int bitSize = 1;
	//initializes firstTime to true
    boolean firstTime = true;
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage theStage){
        stage = theStage;
		//Calls drawGUI so it can be redrawn instead of creating new windows by calling start
        drawGUI();
    }

    private void photoChoice(ActionEvent event){
        try{
			//Initializes a new window for choosing a file
            FileChooser fc = new FileChooser();
			//Initializes a file object to a file chosen by the user
            File f = fc.showOpenDialog(new Stage());
			
			//If the file exists
            if(f != null){
				//Creates a new image from the file
                img = new Image(f.toURI().toURL().toExternalForm());
				//callse imgToWritable which converts the image to a writable image so it can be editted
                imgToWritable(img);
            }

			//If its the first time and an image was selected
            if(firstTime && img != null){
				//sets firstTime to false so the GUI can be redrawn later
                firstTime = false;
            }
			//redraws the GUI
            drawGUI();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pixelize(){
		//Sets the global writable image to the global image dimensions
        wImage = new WritableImage((int)img.getWidth(), (int)img.getHeight());
		//Creates a new pixel reader to read the image pixels
        PixelReader pReader = img.getPixelReader();
		//Creates a new pixel writer to write pixels to the image
        PixelWriter pWriter = wImage.getPixelWriter();
		
		//iterates through the image by bitSize blocks (within there are for loops to deal with pixels within the block
        for(int x = 0; x < (int)img.getHeight()-bitSize; x+=bitSize){
			//increments by bitsize to create a square of space in the image
            for(int y = 0; y < (int)img.getWidth()-bitSize; y+=bitSize){
				//creates an arraylist of colours that will find average colour
                ArrayList<Color> averagePixels = new ArrayList<Color>();
				//for each vertical pixel within the bitSize chunk
                for(int i = x; i < x+bitSize; i++){
					//for each horizontal pixel within the bitSize chunk
                    for(int j = y; j < y+bitSize; j++){
						//Adds the pixel
                        averagePixels.add(pReader.getColor(y,x));
                    }
                }

				//Sets the average value of each color to 0 for each loop to start new
                double averageReds = 0;
                double averageGreens = 0;
                double averageBlues = 0;
                double averageOpacities = 0;
				//For each color in the pixel arraylist it grabs the average of each RGBA color value
                for(Color pixel : averagePixels){
                    averageReds += pixel.getRed();
                    averageGreens += pixel.getGreen();
                    averageBlues += pixel.getBlue();
                    averageOpacities += pixel.getOpacity();
                }
				
				//Creates a new color by averaging the values by the size of the pixel array
                Color averageOfNearbyPixels = new Color(
                        averageReds /= averagePixels.size(),
                        averageGreens /= averagePixels.size(),
                        averageBlues /= averagePixels.size(),
                        averageOpacities /= averagePixels.size());
						
				//For each vertical pixel in the bitSize square chunk
                for(int i = x; i < x+bitSize; i++){
					//For each horizontal pixel in the bitSize square chunk
                    for(int j = y; j < y+bitSize; j++){
						//Writes the same colour to every pixel in this bit chunk
                        pWriter.setColor(j,i, averageOfNearbyPixels);
                    }
                }

            }
        }

    }

    private void imgToWritable(Image img){
		//Sets the global writable image to a new writable image with the same dimensions as the image sent
        wImage = new WritableImage((int)img.getWidth(), (int)img.getHeight());
		//Creates a pixel reader to read the image sent
        PixelReader pReader = img.getPixelReader();
		//Creates a pixel writer to write to the global writable image
        PixelWriter pWriter = wImage.getPixelWriter();
		//for every pixel read from the image, it writes to the global writable image
        for(int i = 0; i < img.getHeight(); i++){
            for(int j = 0; j < img.getWidth(); j++){
				//Copies each color from the original image to the writable image
                pWriter.setColor(j,i, pReader.getColor(j,i));
            }
        }
    }

    private void drawGUI(){
		//If first time it will set up the welcome screen
        if(firstTime){
			//Label to welcome
            Label welcomeLabel = new Label("Welcome. To continue please choose a photo.");
			//Button to select a photo through file explorer
            Button imageSelectButton = new Button("Select photo");
			//Calls photoChoice with the related action event when the button is pressed
            imageSelectButton.setOnAction(this::photoChoice);

			//Adds the label and button to a vertical box
            VBox group = new VBox(10, welcomeLabel, imageSelectButton);
			//centers the vertical box
            group.setAlignment(Pos.CENTER);

			//puts the vbox into the scene
            scene = new Scene(group, 300,100);
			//sets the title for the window
            stage.setTitle("Picture to Pixel");
			//puts the scene into the stage
            stage.setScene(scene);
			//Shows the stage
            stage.show();

        }else{ //If NOT the first time
			
			//Creates a new imageview to display the writable image
            ImageView pixelizedImage = new ImageView(wImage);
			//sets the window size to fit 1200x800
            pixelizedImage.setFitHeight(500);
            pixelizedImage.setFitWidth(600);
			//preserves ratio so it doesn't warp image
            pixelizedImage.setPreserveRatio(true);
			
			//Creates a button to select a new image
            Button imageSelectButton = new Button("Select photo");
			//On button click calls photoChoice with the related action event as a parameter
            imageSelectButton.setOnAction(this::photoChoice);
			
			//Creates a slider between 1 and 64 for the pixel ratio to pixelize,
			//@2 it would take the average of every 2x2 pixels and average the colors,
			//@40 it would take the average of ever 40/40 pixrls and average the colors, etc
            Slider pixelizeMeter = new Slider(1,64, 1);

			//Adds a listener to check the value of the slider
            pixelizeMeter.valueProperty().addListener(
                (observable, oldvalue, newvalue) ->
                {
					//sets the bitsize to the new value of the slider
                    bitSize = newvalue.intValue();
					
					//if the image exists
                    if(img != null){
						//calls pixelize
                        pixelize();
						//sets the imageview to display the now pixelized image (which is why it's global)
                        pixelizedImage.setImage(wImage);
                    }
                } );
				
			//Label for the slider
            Label sliderLabel = new Label("Pixelize Meter");
			//Button to save
            Button saveButton = new Button("Save");
			//When the button is pressed it calls saveImage
            saveButton.setOnAction(this::saveImage);
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
			
			//sets the padding to be 5 on the top left and right, but 30 on the bottom for more space
            group.setPadding(new Insets(5, 5, 30, 5));
			
			//sets the new layout in a new scene and the new scene into the stage
            stage.setScene(new Scene(group));
        }
    }

    private void saveImage(ActionEvent event){
        try{
			//Creates another filechooser window to save the pixelized image
            FileChooser fc = new FileChooser();
			
			//sets the intial filename as "untitled"
            fc.setInitialFileName("Untitled");
			//Sets an applicable extension to the field (has to be manually entered)
            ExtensionFilter png = new ExtensionFilter("PNG (*.png)", "*.png");
			//adds the png filter to the filechooser so it can be selected
            fc.getExtensionFilters().add(png);
			//shows the file save dialog
            File f = fc.showSaveDialog(null);

			//if the user has chosen a file
            if(f!=null){
				//Gets the path to where they want it saved
                String fileName = f.getCanonicalPath();
				//Creates a new file object with the path the user provided
                File imgSave = new File(fileName);
				//grabs the name without the extension
                String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				//Honestly not 100% sure how this works, I grabbed it from stack overflow for how to save
				//images with javafx, but so far it has worked in a few projects to save files
                ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), extension, imgSave);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
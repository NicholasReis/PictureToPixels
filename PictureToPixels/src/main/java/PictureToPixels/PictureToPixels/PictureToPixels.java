package PictureToPixels.PictureToPixels;

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

public class PictureToPixels extends Application
{
    Image img;
    Scene scene;
    WritableImage wImage;
    Stage stage = new Stage();
    int bitSize = 1;
    boolean firstTime = true;
    boolean save = false;
    boolean chooseImage = true;
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage theStage){
        stage = theStage;
        drawGUI();
    }

    private void photoChoice(ActionEvent event){

        try{
            FileChooser fc = new FileChooser();
            File f = fc.showOpenDialog(new Stage());
            if(f != null){
                img = new Image(f.toURI().toURL().toExternalForm());
                imgToWritable(img);
            }

            if(firstTime && img != null){
                firstTime = false;
            }
            drawGUI();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pixelize(){
        wImage = new WritableImage((int)img.getWidth(), (int)img.getHeight());
        PixelReader pReader = img.getPixelReader();
        PixelWriter pWriter = wImage.getPixelWriter();
        for(int x = 0; x < (int)img.getHeight()-bitSize; x+=bitSize){

            for(int y = 0; y < (int)img.getWidth()-bitSize; y+=bitSize){

                ArrayList<Color> averagePixels = new ArrayList<Color>();
                for(int i = x; i < x+bitSize; i++){
                    for(int j = y; j < y+bitSize; j++){
                        averagePixels.add(pReader.getColor(y,x));
                    }
                }

                double averageReds = 0;
                double averageGreens = 0;
                double averageBlues = 0;
                double averageOpacities = 0;
                for(Color pixel : averagePixels){
                    averageReds += pixel.getRed();
                    averageGreens += pixel.getGreen();
                    averageBlues += pixel.getBlue();
                    averageOpacities += pixel.getOpacity();
                }
                Color averageOfNearbyPixels = new Color(
                        averageReds /= averagePixels.size(),
                        averageGreens /= averagePixels.size(),
                        averageBlues /= averagePixels.size(),
                        averageOpacities /= averagePixels.size());
                for(int i = x; i < x+bitSize; i++){
                    for(int j = y; j < y+bitSize; j++){
                        pWriter.setColor(j,i, averageOfNearbyPixels);
                    }
                }

            }
        }

    }

    private void imgToWritable(Image img){
        wImage = new WritableImage((int)img.getWidth(), (int)img.getHeight());
        PixelReader pReader = img.getPixelReader();
        PixelWriter pWriter = wImage.getPixelWriter();
        for(int i = 0; i < img.getHeight(); i++){
            for(int j = 0; j < img.getWidth(); j++){
                pWriter.setColor(j,i, pReader.getColor(j,i));
            }
        }
    }

    private void drawGUI(){
        if(firstTime){
            Label welcomeLabel = new Label("Welcome. To continue please choose a photo.");
            Button imageSelectButton = new Button("Select photo");
            imageSelectButton.setOnAction(this::photoChoice);

            VBox group = new VBox(10, welcomeLabel, imageSelectButton);
            group.setAlignment(Pos.CENTER);

            scene = new Scene(group, 300,100);
            stage.setTitle("Picture to Pixel");
            stage.setScene(scene);
            stage.show();

        }else if(chooseImage){
            ImageView pixelizedImage = new ImageView(wImage);
            pixelizedImage.setFitHeight(1200);
            pixelizedImage.setFitWidth(800);
            pixelizedImage.setPreserveRatio(true);
            Button imageSelectButton = new Button("Select photo");
            imageSelectButton.setOnAction(this::photoChoice);
            Slider pixelizeMeter = new Slider(1,64, 1);

            pixelizeMeter.valueProperty().addListener(
                (observable, oldvalue, newvalue) ->
                {
                    bitSize = newvalue.intValue();
                    if(img != null){
                        pixelize();
                        pixelizedImage.setImage(wImage);
                    }
                } );
            Label sliderLabel = new Label("Pixelize Meter");
            Button saveButton = new Button("Save");
            saveButton.setOnAction(this::saveImage);
            VBox meterAndLabel = new VBox(2, sliderLabel, pixelizeMeter);
            meterAndLabel.setAlignment(Pos.BOTTOM_CENTER);

            HBox underImage = new HBox(10, imageSelectButton, meterAndLabel, saveButton);
            underImage.setAlignment(Pos.BOTTOM_CENTER);

            VBox group = new VBox(10, pixelizedImage, underImage);
            group.setAlignment(Pos.BASELINE_CENTER);
            group.setPadding(new Insets(5, 5, 30, 5));
            stage.setScene(new Scene(group));
        }
    }

    private void saveImage(ActionEvent event){
        try{

            FileChooser fc = new FileChooser();
            fc.setInitialFileName("Untitled");
            ExtensionFilter png = new ExtensionFilter("PNG (*.png)", "*.png");
            fc.getExtensionFilters().add(png);
            File f = fc.showSaveDialog(null);

            if(f!=null){
                String fileName = f.getCanonicalPath();
                File imgSave = new File(fileName);
                String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), extension, imgSave);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
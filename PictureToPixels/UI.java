import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.net.URI;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.control.TextField;

public class UI extends Application
{

    public void start(Stage stage)
    {
        // Create a new grid pane
        GridPane pane = new GridPane();
        Label fileName = new Label("");
        TextField tf = new TextField();
        int bitSize = 8;
        Button butt = new Button("Select image");

        ImageView imgView = new ImageView();

        butt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try{
                        FileChooser fc = new FileChooser();
                        File f = fc.showOpenDialog(stage);
                        Image img1 = new Image(f.toURI().toURL().toExternalForm());

                        //imgView.setImage(img1);
                        imgView.setImage(pixelize(img1, bitSize));
                        pane.add(imgView, 0, 3);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

        //Need to set something up to make this size nicely/accurately
        imgView.setFitHeight(600);
        imgView.setFitWidth(600);

        // Add the button and label into the pane
        pane.add(butt,0,0);
        pane.add(tf,1,0);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 400,400);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    public WritableImage pixelize(Image img, int bitSize){
        WritableImage wImage = new WritableImage((int)img.getWidth(), (int)img.getHeight());
        PixelReader pReader = img.getPixelReader();
        PixelWriter pWriter = wImage.getPixelWriter();
        for(int x = 0; x < (int)img.getHeight()-bitSize; x+=bitSize){

            //System.out.println("X: "+ x);
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

        try{
            File imgSave = new File("savedImage.png");
            ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", imgSave);
        }catch(Exception e){
            e.printStackTrace();
        }
        return wImage;
    }
}

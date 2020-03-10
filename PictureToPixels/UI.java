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

public class UI extends Application
{

    public void start(Stage stage)
    {
        // Create a new grid pane
        GridPane pane = new GridPane();
        Label fileName = new Label("Text");
        Button startButt = new Button("Deep Fry");

        Button butt = new Button("Select image");

        ImageView imgView = new ImageView();

        butt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try{
                    FileChooser fc = new FileChooser();
                    File f = fc.showOpenDialog(stage);
                    Image img1 = new Image(f.toURI().toURL().toExternalForm());
                    pane.getChildren().clear();
                    //imgView.setImage(img1);
                    imgView.setImage(pixelize(img1));
                    pane.add(imgView, 0, 0);
                }catch(Exception e){
                    e.printStackTrace();
                }
                }
            });

        imgView.setFitHeight(800);
        imgView.setFitWidth(800);

        // Add the button and label into the pane
        pane.add(butt,0,0);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 400,400);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    public WritableImage pixelize(Image img){
        WritableImage wImage = new WritableImage((int)img.getWidth(), (int)img.getHeight());
        PixelReader pReader = img.getPixelReader();
        System.out.println(img.getWidth());
        System.out.println(img.getHeight());
        PixelWriter pWriter = wImage.getPixelWriter();
        for(int x = 0; x < (int)img.getHeight()-1; x++){

            //System.out.println("X: "+ x);
            for(int y = 0; y < (int)img.getWidth()-1; y++){
                double centerPixel = pReader.getArgb(x,y);
                double southPixel = pReader.getArgb(x+1,y);
                double eastPixel = pReader.getArgb(x,y+1);
                double southEastPixel = pReader.getArgb((x)+1,(y)+1);
                int averageOfNearbyPixels = (int)(centerPixel + southPixel + eastPixel + southEastPixel/4);

                /* For testing when I was squaring and rooting, slows it
                 * down a lot though so I commented it out.
                System.out.println("Center Pixel: " + centerPixel);
                System.out.println("South Pixel: " + southPixel);
                System.out.println("East Pixel: " + eastPixel);
                System.out.println("SouthEast Pixel: " + southEastPixel);
                System.out.println("Average Pixel: " + averageOfNearbyPixels);
                System.out.println();
                 */
                pWriter.setArgb(x,y, averageOfNearbyPixels);
                pWriter.setArgb(x+1,y, averageOfNearbyPixels);
                pWriter.setArgb(x,y+1, averageOfNearbyPixels);
                pWriter.setArgb(x+1,y+1, averageOfNearbyPixels);

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

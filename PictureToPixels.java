import javafx.application.Application;
import javafx.stage.Stage;

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
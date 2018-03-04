import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Starts up the GUI for the application.
 * @author Raymond Haynes.
 * @version 2018-03-04
 */
public class Driver extends Application
{
    
    /**
     * Assigns the properties of the application upon startup.
     */
    @Override
    public void start(Stage primaryStage)
    {
        Pane root = new Pane();
        
        AccountSignIn acs = new AccountSignIn();
        acs.setLayoutX(25);
        acs.setLayoutY(10);
        root.getChildren().add(acs);
        
        Scene scene = new Scene(root, 850, 450);
        primaryStage.setTitle("Double Help");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Launches the application
     * @param args The arguments at command-line
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}

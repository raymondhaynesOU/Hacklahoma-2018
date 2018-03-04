import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

/**
 * Selection screen. The user can either search for a job, create a job, or sign out.
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class Selection extends GridPane
{
    /**
     * The account that is currently logged in.
     */
    Account loggedIn;
    
    /**
     * Constructor. Stores the account information for the account currently logged in, sets the spacing, and generates
     * the select screen.
     * @param loggedIn
     */
    public Selection(Account loggedIn)
    {
        this.loggedIn = loggedIn;
        setVgap(10);
        setHgap(10);
        generate();
    }
    
    /**
     * Creates the nodes of the select screen and either searches for a job, creates a job, or signs the user out.
     */
    private void generate()
    {
        Button search = new Button("Search for Available Jobs");
        search.setMinWidth(150);
        search.setFont(Font.font("Times", 14));
        
        search.setOnAction(e -> {
            getChildren().clear();
            
            add(new SearchJob(loggedIn), 0, 0);
        });
        add(search, 0, 0);
        
        Button create = new Button("Create A Job");
        create.setMinWidth(150);
        create.setFont(Font.font("Times", 14));
        create.setOnAction(e -> {
            getChildren().clear();
            
            add(new CreateJob(loggedIn), 0, 0);
        });
        add(create, 1, 0);
        
        Button logOut = new Button("Log Out");
        logOut.setMinWidth(100);
        logOut.setFont(Font.font("Times", 14));
        logOut.setOnAction(e -> {
            getChildren().clear();
            
            AccountSignIn pane = new AccountSignIn();
            add(pane, 0, 0);
        });
        add(logOut, 2, 0);
    }
}

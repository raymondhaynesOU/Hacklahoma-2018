import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * Creates a job provided the information give is valid.
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class CreateJob extends GridPane
{
    /**
     * The account information for the user who created the job.
     */
    private Account creator;
    
    /**
     * Constructor. Reads in the account information of the creator, stores it, sets the spacing, and renders the job
     * creation screen.
     * @param creator The account information of the creator.
     */
    public CreateJob(Account creator)
    {
        this.creator = creator;
        setVgap(10);
        setHgap(10);
        
        render();
    }
    
    /**
     * Creates the job creation screen and saves the job if the information entered is valid.
     */
    public void render()
    {
        Text promptName = new Text("Name of the Job: ");
        promptName.setFont(Font.font("Times", 14));
        add(promptName, 0, 0);
        
        Text promptDescription = new Text("Description of the Job: ");
        promptDescription.setFont(Font.font("Times", 14));
        add(promptDescription, 0, 1);
        
        Text promptLocation = new Text("Location of the job: ");
        promptLocation.setFont(Font.font("Times", 14));
        add(promptLocation, 0, 2);
        
        Text promptJobType = new Text("Pick the job category: ");
        promptJobType.setFont(Font.font("Times", 14));
        add(promptJobType, 0, 3);
        
        Text promptPaymentMethod = new Text("How will you pay the person doing the job?");
        promptPaymentMethod.setFont(Font.font("Times", 14));
        add(promptPaymentMethod, 0, 4);
        
        Text promptOnline = new Text("Can this job be done online?");
        promptOnline.setFont(Font.font("Times", 14));
        add(promptOnline, 0, 5);
        
        Text invalid = new Text("");
        invalid.setFont(Font.font("Times", 14));
        add(invalid, 1, 6);
        
        TextField jobName = new TextField();
        jobName.setMinWidth(150);
        jobName.setFont(Font.font("Times"));
        add(jobName, 1, 0);
        
        TextArea description = new TextArea();
        description.setMinWidth(150);
        description.setMinHeight(75);
        description.setFont(Font.font("Times"));
        add(description, 1, 1);
        
        TextField location = new TextField();
        location.setMinWidth(150);
        location.setFont(Font.font("Times"));
        add(location, 1, 2);
        
        ComboBox<String> category = new ComboBox<String>();
        
        category.getItems().add("Mathematics");
        category.getItems().add("Science");
        category.getItems().add("English");
        category.getItems().add("Language");
        category.getItems().add("Arts");
        category.getItems().add("Other");
        
        category.setMinWidth(150);
        add(category, 1, 3);
        
        ComboBox<String> paymentMethod = new ComboBox<String>();
        
        paymentMethod.getItems().add("PayPal");
        paymentMethod.getItems().add("Cash");
        
        paymentMethod.setMinWidth(150);
        add(paymentMethod, 1, 4);
        
        CheckBox online = new CheckBox();
        add(online, 1, 5);
        
        Button create = new Button("Create Job");
        create.setMinWidth(150);
        create.setFont(Font.font("Times", 14));
        create.setOnAction(e -> {
            if (isValid(jobName.getText(), description.getText(), location.getText(), paymentMethod.getValue(),
                    category.getValue()))
            {
                try
                {
                    Job j;
                    
                    if (paymentMethod.getValue().equalsIgnoreCase("PayPal"))
                    {
                        j = new Job(jobName.getText(), description.getText(), location.getText(), category.getValue(),
                                Account.PAYPAL, online.isSelected(), 
                                (creator.getFirstName() + " " + creator.getLastName()));
                    }
                    else
                    {
                        j = new Job(jobName.getText(), description.getText(), location.getText(), category.getValue(),
                                Account.CASH, online.isSelected(), 
                                (creator.getFirstName() + " " + creator.getLastName()));
                    }
                    
                    j.saveJob();
                    
                    getChildren().clear();
                    
                    Selection pane = new Selection(creator);
                    add(pane, 0, 0);
                }
                catch (IOException f)
                {
                    System.out.println("Error creating a new job.");
                }
            }
        });
        add(create, 0, 6);
    }
    
    private boolean isValid(String job, String description, String location, String method, String value)
    {
        if (job == null)
        {
            return false;
        }
        
        if (description == null)
        {
            return false;
        }
        
        if (location == null)
        {
            return false;
        }
        
        if (method == null)
        {
            return false;
        }
        
        if (value == null)
        {
            return false;
        }
        
        return true;
    }
}

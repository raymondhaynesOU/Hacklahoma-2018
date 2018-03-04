import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * Creates an account provided that the information give is valid
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class AccountCreator extends GridPane
{
    /**
     * The account that will be made
     */
    Account accountToBeMade;
    
    /**
     * Constructor. Sets the spacing of the pane and creates the nodes of the screen.
     */
    public AccountCreator()
    {
        setVgap(10);
        setHgap(10);
        construct();
    }
    
    /**
     * Constructs all of the information of the Create Account screen and creates the account if it is valid.
     */
    private void construct()
    {
        Text promptUser = new Text("Username(must be at least 6 characters): ");
        promptUser.setFont(Font.font("Times", 14));
        add(promptUser, 0, 0);
        
        Text promptPassword = new Text("Password (must be at least 6 characters): ");
        promptPassword.setFont(Font.font("Times", 14));
        add(promptPassword, 0, 1);
        
        Text promptFullName = new Text("Full Name (first and last): ");
        promptFullName.setFont(Font.font("Times", 14));
        add(promptFullName, 0, 2);
        
        Text promptEducation = new Text("Education Level: ");
        promptEducation.setFont(Font.font("Times", 14));
        add(promptEducation, 0, 3);
        
        Text promptSkills = new Text("Skills (separate with commas): ");
        promptSkills.setFont(Font.font("Times", 14));
        add(promptSkills, 0, 4);
        
        Text promptPayment = new Text("Select a payment method: ");
        promptPayment.setFont(Font.font("Times", 14));
        add(promptPayment, 0, 5);
        
        Text promptNewThings = new Text("Want to try new things?");
        promptNewThings.setFont(Font.font("Times", 14));
        add(promptNewThings, 0, 6);
        
        Text promptOnlineOnly = new Text("Do you only want to do online jobs?");
        promptOnlineOnly.setFont(Font.font("Times", 14));
        add(promptOnlineOnly, 0, 7);
        
        Text invalid = new Text("");
        invalid.setFont(Font.font("Times", 14));
        add(invalid, 1, 8);
        
        TextField userName = new TextField();
        userName.setMinWidth(150);
        userName.setFont(Font.font("Times"));
        add(userName, 1, 0);
        
        TextField password = new TextField();
        password.setMinWidth(150);
        password.setFont(Font.font("Times"));
        add(password, 1, 1);
        
        TextField fullName = new TextField();
        fullName.setMinWidth(150);
        fullName.setFont(Font.font("Times"));
        add(fullName, 1, 2);
        
        TextField education = new TextField();
        education.setMinWidth(150);
        education.setFont(Font.font("Times"));
        add(education, 1, 3);
        
        TextField skills = new TextField();
        skills.setMinWidth(150);
        skills.setFont(Font.font("Times"));
        add(skills, 1, 4);
        
        ChoiceBox<String> methods = new ChoiceBox<String>();
        methods.getItems().add("PayPal");
        methods.getItems().add("Cash");
        methods.setMinWidth(150);
        add(methods, 1, 5);
        
        CheckBox newThings = new CheckBox();
        add(newThings, 1, 6);
        
        CheckBox onlineOnly = new CheckBox();
        add(onlineOnly, 1, 7);
        
        Button createAccount = new Button("Create Account");
        createAccount.setMinWidth(150);
        createAccount.setFont(Font.font("Times", 14));
        createAccount.setOnAction(e -> {
            if (checkInformation(userName.getText(), password.getText(), fullName.getText(), education.getText(), 
                    skills.getText(), methods.getValue()))
            {
                String[] name = fullName.getText().split(" ");
                
                ArrayList<String> skillsList = new ArrayList<String>();
                for (int i = 0; i < skills.getText().split(",").length; i++)
                {
                    skillsList.add(skills.getText().split(",")[i]);
                }
                
                if (methods.getValue().equalsIgnoreCase("PayPal"))
                {
                    accountToBeMade = new Account(userName.getText(), password.getText(), name[0], name[1], 
                            education.getText(), skillsList, Account.PAYPAL, newThings.isSelected(), 
                            onlineOnly.isSelected());
                }
                else
                {
                    accountToBeMade = new Account(userName.getText(), password.getText(), name[0], name[1], 
                            education.getText(), skillsList, Account.CASH, newThings.isSelected(), 
                            onlineOnly.isSelected());
                }
                
                getChildren().clear();
                accountToBeMade.saveAccount();
                
                Selection pane = new Selection(accountToBeMade);
                add(pane, 0, 0);
            }
            else
            {
                invalid.setText("Invalid Information");
            }
        });
        add(createAccount, 0, 8);
        
        Button backToSignIn = new Button("Back to Sign In");
        backToSignIn.setFont(Font.font("Times", 14));
        backToSignIn.setMinWidth(150);
        backToSignIn.setOnAction(e -> {
            getChildren().clear();
            
            AccountSignIn pane = new AccountSignIn();
            add(pane, 0, 0);
        });
        add(backToSignIn, 0, 9);
    }
    
    /**
     * Checks all of the information given to see if the account can be created
     * @param userName The userName entered
     * @param password The password entered
     * @param fullName The full name of the user
     * @param education The education level of the user
     * @param skills The listed skills of the user
     * @param method The preferred payment method
     * @return Whether the information is valid or not.
     */
    private boolean checkInformation(String userName, String password, String fullName, String education, String skills,
            String method)
    {
        if (userName == null || userName.length() < 6)
        {
            return false;
        }
            
        if (password == null || password.length() < 6)
        {
            return false; 
        }
        
        if (fullName == null || !fullName.contains(" "))
        {
            return false;
        }
        
        if (education == null)
        {
            return false;
        }
        
        if (skills == null)
        {
            return false;
        }
        
        if (method == null)
        {
            return false;
        }
        return true;
    }
}

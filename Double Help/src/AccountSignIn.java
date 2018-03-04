import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

/**
 * First screen seen of the application. Signs in an account, links to AccountCreator, or signs in with a guest account.
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class AccountSignIn extends GridPane
{
    /**
     * List of accounts that were read in from the Accounts folder
     */
    private ArrayList<Account> accounts;
    
    /**
     * The account that loggedIn
     */
    private Account loggedIn;
    
    /**
     * Constructor. Loads in all accounts in the Accounts folder and renders the nodes of the screen.
     */
    public AccountSignIn()
    {
        accounts = new ArrayList<Account>();
        
        setVgap(10);
        setHgap(10);
        try
        {
            loadAccounts();
        }
        catch(IOException e)
        {
            System.out.println("IO error occurred.");
        }
        
        login();
    }
    
    /**
     * Loads all of the accounts that were saved in the Accounts folder
     * @throws IOException
     */
    private void loadAccounts() throws IOException
    {
        Scanner scnr = new Scanner(new File("Accounts/userNames.txt"));
        
        while(scnr.hasNextLine())
        {
            String nameToRead = scnr.nextLine();
            
            Scanner scnr2 = new Scanner(new File("Accounts/" + nameToRead + ".txt"));
            
            String userName = scnr2.nextLine();
            String password = scnr2.nextLine();
            
            String[] fullName = scnr2.nextLine().split(" ");
            String firstName = fullName[0];
            String lastName = fullName[1];
            
            String education = scnr2.nextLine();
            
            String[] skillArr = scnr2.nextLine().split(",");
            ArrayList<String> skills = new ArrayList<String>();
            for (int i = 0; i < skillArr.length; i++)
            {
                skills.add(skillArr[i]);
            }
            
            int paymentMethod = Integer.parseInt(scnr2.nextLine());
            
            boolean newThings = (scnr2.nextLine().equalsIgnoreCase("true"));
            boolean onlineOnly = (scnr2.nextLine().equalsIgnoreCase("true"));
            
            accounts.add(new Account(userName, password, firstName, lastName, education, skills, paymentMethod,
                    newThings, onlineOnly));
            
            scnr2.close();
        }
        
        scnr.close();
    }
    
    /**
     * Creates the nodes for the login screen and logs a user in if the enter information matches an account.
     */
    private void login()
    {
        Text promptUser = new Text("Username: ");
        promptUser.setFont(Font.font("Times", 14));
        add(promptUser, 0, 0);
        
        Text promptPassword = new Text("Password: ");
        promptPassword.setFont(Font.font("Times", 14));
        add(promptPassword, 0, 1);
        
        Text invalid = new Text("");
        invalid.setFont(Font.font("Times", 14));
        add(invalid, 1, 2);
        
        TextField userName = new TextField();
        userName.setFont(Font.font("Times"));
        userName.setMinWidth(150);
        add(userName, 1, 0);
        
        TextField password = new TextField();
        password.setFont(Font.font("Times"));
        password.setMinWidth(150);
        add(password, 1, 1);
        
        Button login = new Button("Log In");
        login.setMinWidth(75);
        login.setFont(Font.font("Times", 14));
        login.setOnAction(e -> {
            if (valid(userName.getText(), password.getText()))
            {
                for (int i = 0; i < accounts.size(); i++)
                {
                    if (userName.getText().equals(accounts.get(i).getUserName()))
                    {
                        loggedIn = accounts.get(i);
                    }
                }
                getChildren().clear();
                
                Selection pane = new Selection(loggedIn);
                add(pane, 0, 0);
            }
        });
        add(login, 0, 2);
        
        Button createAccount = new Button("Create Account");
        createAccount.setMinWidth(150);
        createAccount.setFont(Font.font("Times", 14));
        createAccount.setOnAction(e -> {
            getChildren().clear();
            
            AccountCreator ac = new AccountCreator();
            add(ac, 0, 0);
        });
        add(createAccount, 0, 3);
        
        Button guestAccount = new Button("Continue As Guest");
        guestAccount.setMinWidth(150);
        guestAccount.setFont(Font.font("Times", 14));
        guestAccount.setOnAction(e -> {
            getChildren().clear();
            
            Account guest = new Account("guest123", "randomLines", "Guest", "Account", "N/A", new ArrayList<String>(),
                    2, true, true);
            
            Selection pane = new Selection(guest);
            add(pane, 0, 0);
        });
        add(guestAccount, 1, 3);
    }
    
    /**
     * Checks the userName and password entered and sees if they match with an account.
     * @param userName The userName entered.
     * @param password The password entered.
     * @return Whether the userName and password matches an account.
     */
    private boolean valid(String userName, String password)
    {
        boolean userMatch = false;
        for (int i = 0; i < accounts.size(); i++)
        {
            if (userName.equals(accounts.get(i).getUserName()))
            {
                userMatch = true;
                break;
            }
        }
        
        if (userMatch)
        {
            for  (int i = 0; i < accounts.size(); i++)
            {
                if (password.equals(accounts.get(i).getPassword()))
                {
                    return true;
                }
            }
            
            return false;
        }
        else
        {
            return userMatch;
        }
    }
}

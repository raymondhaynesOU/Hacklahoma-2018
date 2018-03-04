import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A single account. Stores all of the information of the account.
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class Account
{
    /**
     * The user's first name.
     */
    private String firstName;
    
    /**
     * The user's last name.
     */
    private String lastName;
    
    /**
     * The user's user name.
     */
    private String userName;
    
    /**
     * The user's password.
     */
    private String password;
    
    /**
     * The education level of the user.
     */
    private String education;
    
    /**
     * A list of the user's skills.
     */
    private ArrayList<String> skills;
    
    /**
     * The preferred payment method that the user will accept.
     */
    private int preferredPaymentMethod;
    
    /**
     * The int value representing PAYPAL.
     */
    public static final int PAYPAL = 1;
    
    /**
     * The int value representing CASH.
     */
    public static final int CASH = 2;
    
    /**
     * Determines if the user is willing to try new things.
     */
    private boolean newThings;
    
    /**
     * Determines if the user will only do online projects.
     */
    private boolean onlineOnly;
    
    /**
     * Constructor. Initializes all of the variables.
     * @param userName The userName of the account.
     * @param password The password of the account.
     * @param firstName The first name of the account.
     * @param lastName The last name of the account.
     * @param education The education level of the account.
     * @param skills The list of skills of the account.
     * @param preferredPaymentMethod The preferred payment method of the account.
     * @param newThings Whether the user will try new things.
     * @param onlineOnly Whether the user will only do online jobs.
     */
    public Account(String userName, String password, String firstName, String lastName, String education,
            ArrayList<String> skills, int preferredPaymentMethod, boolean newThings, boolean onlineOnly)
    {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.education = education;
        this.skills = skills;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.newThings = newThings;
        this.onlineOnly = onlineOnly;
    }

    /**
     * Saves the account on a text file and updates the account list.
     */
    public void saveAccount()
    {
        try
        {
           PrintWriter pw = new PrintWriter(new File("Accounts/" + userName + ".txt"));
            
            String skillString = "";
            for (int i = 0; i < skills.size() - 1; i++)
            {
                skillString += skills.get(i) + ",";
            }
            skillString += skills.get(skills.size() - 1);
            
            pw.println(userName);
            pw.println(password);
            pw.println(firstName + " " + lastName);
            pw.println(education);
            pw.println(skillString);
            pw.println(preferredPaymentMethod);
            pw.println(newThings);
            pw.println(onlineOnly);
            
            pw.close();
            
            Scanner scnr = new Scanner(new File("Accounts/userNames.txt"));
            
            ArrayList<String> usersString = new ArrayList<String>();
            while(scnr.hasNextLine())
            {
                usersString.add(scnr.nextLine());
            }
            usersString.add(userName);
            scnr.close();
            
            PrintWriter px = new PrintWriter(new File("Accounts/userNames.txt"));
            
            for (int i = 0; i < usersString.size(); i++)
            {
                px.println(usersString.get(i));
            }
            
            px.close();
        }
        catch (IOException e)
        {
            System.out.println("Error detected. Invalid IO process.");
        }
    }
    
    /**
     * Returns the first name of the user.
     * @return The first name of the user.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     * @return The last name of the user.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Returns the userName of the user.
     * @return The userName of the user.
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * Returns the password of the user.
     * @return The password of the user.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Returns the education level of the user.
     * @return The education level of the user.
     */
    public String getEducation()
    {
        return education;
    }

    /**
     * Returns the list of skills of the user.
     * @return The list of skills of the user.
     */
    public ArrayList<String> getSkills()
    {
        return skills;
    }

    /**
     * Returns the preferred payment method of the user.
     * @return The preferred payment method of the user.
     */
    public int getPreferredPaymentMethod()
    {
        return preferredPaymentMethod;
    }

    /**
     * Returns whether the user will try new things.
     * @return Whether the user will try new things.
     */
    public boolean isNewThings()
    {
        return newThings;
    }

    /**
     * Returns whether the user will only do online jobs.
     * @return Whether the user will only do online jobs.
     */
    public boolean isOnlineOnly()
    {
        return onlineOnly;
    }
    
    
}

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A single Job object. Stores all of the information of a job.
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class Job
{
    /**
     * The name of the job
     */
    private String name;
    
    /**
     * The description of the job
     */
    private String description;
    
    /**
     * Where the job is taking place
     */
    private String location;
    
    /**
     * The category of the job
     */
    private String category;
    
    /**
     * Whether the job can be done online or not.
     */
    private boolean isOnline;
    
    /**
     * The name of the creator of the job.
     */
    private String creator;
    
    /**
     * How the creator of the job will pay the person who accepts (1 = PayPal, 2 = Cash (Constants listed in Account))
     */
    private int paymentMethod;
    
    /**
     * Constructor. Initializes the varibles.
     * @param name The name of the job.
     * @param description The description of the job.
     * @param location The location of the job.
     * @param category The category of the job.
     * @param paymentMethod The paymentMethod of the job.
     * @param isOnline Whether the job can be done online.
     * @param creator The name of the creator of the job.
     */
    public Job (String name, String description, String location, String category, int paymentMethod,
            boolean isOnline, String creator)
    {
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.isOnline = isOnline;
        this.creator = creator;
    }
    
    /**
     * Saves the job in a text file and updates the job list.
     * @throws IOException
     */
    public void saveJob() throws IOException
    {
        PrintWriter pw = new PrintWriter(new File("Available Jobs/" + name + ".txt"));
        
        pw.println(name);
        pw.println(description);
        pw.println(location);
        pw.println(category);
        pw.println(paymentMethod);
        pw.println(isOnline);
        pw.println(creator);
        
        pw.close();
        
        Scanner scnr = new Scanner(new File("Available Jobs/jobs.txt"));
        
        String jobsString = "";
        while (scnr.hasNextLine())
        {
            jobsString += scnr.nextLine() + "\n";
        }
        jobsString += name + "\n";
        
        scnr.close();
        
        PrintWriter list = new PrintWriter(new File("Available Jobs/jobs.txt"));
        
        list.println(jobsString);
        
        list.close();
    }

    /**
     * Returns the name of the job.
     * @return The name of the job.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the description of the job.
     * @return The description of the job.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns the location of the job.
     * @return The location of the job.
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Returns the category of the job.
     * @return The category of the job.
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * Returns how the creator will pay the person who accepts.
     * @return How the creator will pay the person who accepts.
     */
    public int getPaymentMethod()
    {
        return paymentMethod;
    }
    
    /**
     * Returns whether the job can be done online.
     * @return Whether the job can be done online.
     */
    public boolean isOnline()
    {
        return isOnline;
    }
    
    /**
     * Returns the name of the creator of the job.
     * @return The name of the creator of the job.
     */
    public String getCreator()
    {
        return creator;
    }
}

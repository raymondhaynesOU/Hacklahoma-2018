import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Searches for a job and accepts as long as a job is selected.
 * @author Raymond Haynes
 * @version 2018-03-04
 */
public class SearchJob extends GridPane
{
    /**
     * The list of all of the jobs that are stored in the Available Jobs folder
     */
    private ArrayList<Job> jobs = new ArrayList<Job>();
    
    /**
     * The narrowed-down list of jobs.
     */
    private ArrayList<Job> jobsOneCat = new ArrayList<Job>();
    
    /**
     * The information of the account that is logged in.
     */
    private Account loggedIn;
    
    /**
     * Constructor. Reads in the information of the account that is logged in, reads in all of the jobs in the Available
     * Jobs folder, and creates the search job screen.
     * @param loggedIn
     */
    public SearchJob(Account loggedIn)
    {
        setHgap(10);
        setVgap(10);
        this.loggedIn = loggedIn;
        try
        {
            createJobList();
        }
        catch (IOException e)
        {
            System.out.println("Error loading the job list.");
        }
        
        createMenu();
    }
    
    /**
     * Creates a list of jobs based on what is read in from the Available Jobs folder.
     * @throws IOException
     */
    private void createJobList() throws IOException
    {
        Scanner scnr = new Scanner(new File("Available Jobs/jobs.txt"));
        
        while (scnr.hasNextLine())
        {
            String jobName = scnr.nextLine();
            
            Scanner scnr2 = new Scanner(new File("Available Jobs/" + jobName + ".txt"));
            
            String name = scnr2.nextLine();
            String description = scnr2.nextLine();
            String location = scnr2.nextLine();
            String category = scnr2.nextLine();
            int method = Integer.parseInt(scnr2.nextLine());
            boolean isOnline = (scnr2.nextLine().equalsIgnoreCase("true"));
            String fullName = scnr2.nextLine();
            
            jobs.add(new Job(name, description, location, category, method, isOnline, fullName));
            
            scnr2.close();
        }
        scnr.close();
    }
    
    /**
     * Creates the nodes for the search a job screen, and narrows done a list of jobs based on the category, the payment
     * method, and whether the job can be done online, based on the user's preferences.
     */
    private void createMenu()
    {
        TextArea description = new TextArea();
        description.setFont(Font.font("Times", 14));
        description.setMinHeight(100);
        description.setMinWidth(150);
        
        ComboBox<String> categories = new ComboBox<String>();
        
        categories.getItems().add("Mathematics");
        categories.getItems().add("Science");
        categories.getItems().add("English");
        categories.getItems().add("Language");
        categories.getItems().add("Arts");
        categories.getItems().add("Other");
        
        ComboBox<String> remainingJobs = new ComboBox<String>();
        
        categories.setOnAction(e -> {
            remainingJobs.getItems().clear();
            jobsOneCat.clear();
            description.setText("");
            
            filterJobs(categories.getValue());
            
            for (int i = 0; i < jobsOneCat.size(); i++)
            {
                remainingJobs.getItems().add(jobsOneCat.get(i).getName());
            }
        });
        add(categories, 0, 0);
        
        remainingJobs.setOnAction(e -> {
            if (!jobsOneCat.isEmpty())
            {
                for (int i = 0; i < jobsOneCat.size(); i++)
                {
                    if (remainingJobs.getValue().equals(jobsOneCat.get(i).getName()))
                    {
                        String jobInfo = "Job Name: " + jobsOneCat.get(i).getName() + "\n";
                        jobInfo += "Job Description: " + jobsOneCat.get(i).getDescription() + "\n";
                        jobInfo += "Job Location: " + jobsOneCat.get(i).getLocation() + "\n";
                        jobInfo += "You will be paid by: " + jobsOneCat.get(i).getPaymentMethod() + "\n";
                        jobInfo += "Job is online: " + jobsOneCat.get(i).isOnline() + "\n";
                        jobInfo += "Created by: " + jobsOneCat.get(i).getCreator();
                        
                        description.setText(jobInfo);
                        add(description, 0, 2);
                    }
                }
            }
        });
        add(remainingJobs, 0, 1);
        
        Button acceptJob = new Button ("Accept Job");
        acceptJob.setFont(Font.font("Times", 14));
        acceptJob.setMinWidth(100);
        acceptJob.setOnAction(e -> {
            if (remainingJobs.getValue() != null && categories.getValue() != null)
            {
                for (int i = 0; i < jobsOneCat.size(); i++)
                {
                    if (remainingJobs.getValue().equals(jobsOneCat.get(i).getName()))
                    {
                        File toRemove = new File("Available Jobs/" + jobsOneCat.get(i).getName() + ".txt");
                        
                        jobs.remove(jobsOneCat.get(i));
                        jobsOneCat.remove(jobsOneCat.get(i));
                        
                        toRemove.delete();
                        
                        try
                        {
                            updateJobList();
                        }
                        catch (IOException f)
                        {
                            System.out.println("Error occurred when accepting the job.");
                        }
                    }
                }
            }
            
            getChildren().clear();
            
            Selection pane = new Selection(loggedIn);
            add(pane, 0, 0);
        });
        add(acceptJob, 0, 3);
        
        Button backToSelectScreen = new Button("Go Back To Select Screen");
        backToSelectScreen.setFont(Font.font("Times", 14));
        backToSelectScreen.setMinWidth(150);
        backToSelectScreen.setOnAction(e -> {
           getChildren().clear();
           
           Selection pane = new Selection(loggedIn);
           add(pane, 0, 0);
        });
        add(backToSelectScreen, 0, 4);
    }
    
    /**
     * Filters the jobs based on the search criteria.
     * @param category
     */
    private void filterJobs(String category)
    {
        for (int i = 0; i < jobs.size(); i++)
        {
            if (jobs.get(i).getCategory().equalsIgnoreCase(category) && (jobs.get(i).isOnline() || 
                    !loggedIn.isOnlineOnly()) && jobs.get(i).getPaymentMethod() == loggedIn.getPreferredPaymentMethod())
            {
                jobsOneCat.add(jobs.get(i));
            }
        }
    }
    
    /**
     * Updates the job list when a job is accepted.
     * @throws IOException
     */
    private void updateJobList() throws IOException
    {
        PrintWriter pw = new PrintWriter(new File("Available Jobs/jobs.txt"));
        
        for (int i = 0; i < jobs.size(); i++)
        {
            pw.println(jobs.get(i).getName());
        }
    }
}

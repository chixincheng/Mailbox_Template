import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
/**
 * Represents a mail box, and contains all the folder object with methods to 
 * manipulate the folders
 * 
 * @author
 *      XinCheng Chi, SBU ID#：111919385,Recition R30
 * Assignment:
 *      Homework #5 for CSE 214, Summer 2020
 * Date:
 *      August 6,2020
 */
public class Mailbox implements Serializable
{
    //Invariants of the Mailbox class
    private  Folder inbox;
    private  Folder trash;
    private ArrayList<Folder> folders;
    public static Mailbox mailbox;
    
    /**
     * The main operating method for this program.
     * @throws FileNotFoundException
     *      Indicates that the file was not found with the given file name
     * @throws IOException
     *      Indicates an error reading in from the keyboard.
     * @throws ClassNotFoundException
     *      Indicates that the class is not found
     * @throws ExistedNameException 
     *      Indicates that the given name already exist
     */
    public static void main(String[]args) throws FileNotFoundException, IOException, ClassNotFoundException, ExistedNameException 
    {
        readMailBox();//try to load the pre-existing mail box
        
        BufferedReader input= new BufferedReader(new InputStreamReader(System.in));//keyboard input
        
        char userseletion = 'X';//user input
        while(userseletion != 'Q')//keep looping while user input not equal to Q
        {
            printFolder();//helper method to print all folder name
            //printing the menu option
            System.out.println("");
            System.out.println("A – Add folder");
            System.out.println("R – Remove folder");
            System.out.println("C – Compose email");
            System.out.println("F – Open folder");
            System.out.println("I – Open Inbox");
            System.out.println("T – Open Trash");
            System.out.println("E – Empty Trash");
            System.out.println("Q – Quit");
            System.out.println("");
            System.out.print("Enter a user option: ");
            userseletion =  Character.toUpperCase(input.readLine().charAt(0));//set userselection to user input;
            System.out.println("");
            
            switch(userseletion)
            {
                case'A'://Add folder
                    System.out.print("Enter the folder name: ");//ask for user input
                    String foldname = input.readLine();//set user input as folder name
                    
                    Folder toBeAdd = new Folder(foldname);//create a new folder with given name
                    addFolder(toBeAdd);//add it to the mail box
                    break;
                case'R'://Remove folder
                    printFolder();//helper method to print all folder name
                    System.out.print("Enter the folder name: ");//ask for user input
                    foldname = input.readLine();//set user input as folder name
                    
                    Folder toBeRem = getFolder(foldname);//get the folder to be removed
                    if(toBeRem != null)//if it exist, remove it
                    {
                        deleteFolder(toBeRem.getName());
                    }
                    break;
                case'C'://Compose Email
                    composeEmail();//compose the email
                    break;
                case'F'://Open a folder
                    System.out.print("Enter the folder name: ");//ask for user input
                    foldname = input.readLine();//set user input as folder name
                    System.out.println("");
                    Folder exist = getFolder(foldname);//get the folder to be opened
                    if(exist != null)//if it exist, open it
                    {
                        subMenu(exist);
                    }
                    else//if it does not exist, print the statement
                        System.out.print("Folder do not exist");
                    break;
                case'I'://Open Inbox
                    subMenu(mailbox.inbox);//open the inbox
                    break;
                case'T'://Open trash
                    subMenu(mailbox.trash);//open the trash
                    break;
                case'E'://Empty trash
                    int count = 0;//count
                    for(int i =0; i <mailbox.trash.getSize();i++)//go through all email in trash and remove it
                    {
                        mailbox.trash.getEmail().remove(i);//remove the email
                        count++;//increase the count
                    }
                    System.out.println(count + " item(s) successfully deleted.");
                    System.out.println("");
                    break;
                case'Q'://Quit the program
                    break;
            }
        }
        writeMailBox();//save the mail box
    }
    /**
     * Constructor for Mailbox class
     */
    public Mailbox()
    {
        //Initialize the member variable
        folders = new ArrayList<Folder>(100);
        inbox = new Folder("Inbox");
        trash = new Folder("Trash");
        folders.add(inbox);
        folders.add(trash);
    }
    /**
     * Helper method to print all folder name
     */
    public static void printFolder()
    {
        System.out.println("");
        System.out.println("Mailbox:");
        System.out.println("--------");
        if(mailbox != null)
        {
            for(int i =0;i<mailbox.folders.size();i++)
            {
                System.out.println(mailbox.folders.get(i).getName());
            }
        }
    }
    /**
     * Add the given folder to the mailbox
     * @param folder
     *      the name of the folder to be added
     * @throws ExistedNameException 
     *      Indicates that the given name already exist
     */
    public static void addFolder(Folder folder) throws ExistedNameException
    {
        String name = folder.getName();//get the name of the folder to be added
        
        try//if the folder name already exist, throw exception
        {
            for(int i =0;i<mailbox.folders.size();i++)//go through the folders
            {
                 if(name.compareTo(mailbox.folders.get(i).getName()) == 0)//check if name exist, if so, throw exception
                     throw new ExistedNameException();
            }
        }
        catch(ExistedNameException e)
        {
            System.out.println("Name already exist");
        }
        
        mailbox.folders.add(folder);//add the folder
    }
    /**
     * Delete the folder with the given name
     * @param name 
     *      the name of the folder to be deleted
     */
    public static void deleteFolder(String name)
    {
        int pos = -1;//default to -1
        
        for(int i =0;i<mailbox.folders.size();i++)//go through all folders
        {
             if(name.compareTo(mailbox.folders.get(i).getName()) == 0)//if name match, set pos to that folder
                 pos = i;
        }
        if(pos >=0)//if folder is found, remove it
        {
            mailbox.folders.remove(pos);//remove the folder
        }
    }
    /**
     * Compose the email with the given user input
     * @throws IOException 
     *      Indicates an error reading in from the keyboard.
     */
    public static void composeEmail() throws IOException
    {
        BufferedReader input= new BufferedReader(new InputStreamReader(System.in));//keyboard input
        System.out.print("Enter recipient (To): ");//ask for user input
        String To = input.readLine();//set user input
        System.out.println("");

        System.out.print("Enter carbon copy recipients (CC):");//ask for user input
        String CC = input.readLine();//set user input
        System.out.println("");

        System.out.print("Enter blind carbon copy recipients (BCC):");//ask for user input
        String BCC = input.readLine();//set user input
        System.out.println("");

        System.out.print("Enter subject line:");//ask for user input
        String sub = input.readLine();//set user input
        System.out.println("");

        System.out.print("Enter body:");//ask for user input
        String bod = input.readLine();//set user input
        
        GregorianCalendar time = new GregorianCalendar();//get the current time
        
        Email tex = new Email(To,CC,BCC,sub,bod,time);//create an email object with user input information and current time
        
        mailbox.inbox.addEmail(tex);//add the email to the inbox
        
        System.out.println("");
        System.out.println("Email successfully added to Inbox.");
    }
    /**
     * Move the email to the trash
     * @param email 
     *      the email to be moved to the trash
     */
    public static void deleteEmail(Email email)
    {
        mailbox.trash.addEmail(email);//move the email to the trash
    }
    /**
     * Removes all emails from the trash folder
     */
    public static void clearTrash()
    {
        if(mailbox.trash != null)//if trash contains at least 1 email
        {
            for(int i=0;i<mailbox.trash.getSize();i++)//go through all email in trash
            {
                mailbox.trash.removeEmail(i);//remove the email
            }
        }
    }
    /**
     * Take the given email and put it in the given folder
     * @param email
     *      the given email
     * @param target 
     *      the given folder
     */
    public static void moveEmail(Email email, Folder target)
    {
        Folder tar = getFolder(target.getName());//check if the given folder exist in mail box
        if(tar != null)//if it exist
        {
            tar.addEmail(email);//move the email to it
        }
    }
    /**
     * Returns a folder by folder name
     * @param name
     *      the folder name
     * @return 
     *      the folder with the given name, null is such folder does not exist
     */
    public static Folder getFolder(String name)
    {
        for(int i =0; i<mailbox.folders.size();i++)//go through all folders
        {
            if(mailbox.folders.get(i).getName().equals(name))//if name matchs
                return mailbox.folders.get(i);//return the folder
        }
        return null;
    }
    /**
     * Save everything for next time
     */
    public static void writeMailBox()
    {
        try
        {
            FileOutputStream file = new FileOutputStream("mailbox.obj");
            ObjectOutputStream fout = new ObjectOutputStream(file);
            fout.writeObject(mailbox);
            fout.close();
        }
        catch(IOException a)
        {
            System.out.println("error");
        }
    }
    /**
     * Re-load the pre-existing mail box if there exist one
     * @throws FileNotFoundException
     *      Indicates that the file was not found with the given file name
     * @throws IOException
     *      Indicates an error reading in from the keyboard.
     * @throws ClassNotFoundException
     *      Indicates that the class is not found
     */
    public static void readMailBox() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        try
        {
            FileInputStream file = new FileInputStream ("mailbox.obj");
            ObjectInputStream  fin = new ObjectInputStream (file);
            mailbox = (Mailbox)fin.readObject();
            file.close();
        }
        catch(IOException a)
        {
            System.out.println("Previous save not found, starting with an empty mailbox.");
            mailbox = new Mailbox();
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("error..");
        }
    }
    /**
     * The submenu of the folders
     * @param folder
     *      the folder to be open in the sub menu
     * @throws IOException 
     *      Indicates an error reading in from the keyboard.
     */
    public static void subMenu(Folder folder) throws IOException
    {
        //printing out the information about the folder
        System.out.println(folder.getName()+"");
        System.out.println("");
        if(folder.getSize() == 0)
            System.out.println(folder.getName()+" is empty.");
        else
        {
            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
            for(int i=0;i<folder.getSize();i++)
            {
                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
            }
        }
        
        BufferedReader input= new BufferedReader(new InputStreamReader(System.in));//keyboard input
        
        if(folder.getSize() > 0)//if folder is not empty
        {
            String userseletion = "X";//user input
            
            while(!(userseletion.equals("R")) && folder.getSize() > 0)//if user input != R and folder is not empty
            {
                //print the sub menu option
                System.out.println("");
                System.out.println("M – Move email");
                System.out.println("D – Delete email");
                System.out.println("V – View email contents");
                System.out.println("SA – Sort by subject line in ascending order");
                System.out.println("SD – Sort by subject line in descending order");
                System.out.println("DA – Sort by date in ascending order");
                System.out.println("DD – Sort by date in descending order");
                System.out.println("R – Return to mailbox");
                System.out.println("");
                System.out.print("Enter a user option:");//ask for user input

                String name="";
                userseletion = input.readLine();//set userselection to user input;
                System.out.println("");
                
                switch(userseletion)
                {
                    case"M"://Move email
                        System.out.print("Enter the index of the email to move: ");//ask for user input
                        int pos = Integer.parseInt(input.readLine());//set user input
                        
                        //printing out all folders in mail box
                        System.out.println("");
                        System.out.println("Folders:");
                        for(int i =0;i<mailbox.folders.size();i++)
                        {
                            System.out.println(mailbox.folders.get(i).getName());
                        }
                        System.out.println("");
                        
                        if(pos >= 0 && pos <= folder.getSize())//if user input pos is within the valid range
                        {
                            System.out.print("Select a folder to move ”"+folder.getEmail().get(pos-1).getSubject()+"“ to:");//ask user which folder to move to
                            name = input.readLine();//set user input
                        }
                        System.out.println("");
                        
                        Folder target = getFolder(name);//initialize a folder with user input name
                        if(target != null)//if folder exist move it according to user input
                        {
                            System.out.println(folder.getEmail().get(pos-1).getSubject()+" successfully moved to: "+name+".");
                            
                            Email mail = folder.removeEmail(pos-1);
                            moveEmail(mail,target);
                        }
                        System.out.println("");
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"D"://Delete email
                        System.out.print("Enter email index: ");//ask for user input
                        pos = Integer.parseInt(input.readLine());//set user input
                        if(pos >= 0 && pos <= folder.getSize())//check if user input is within the valid range
                        {
                            Email rev = folder.removeEmail(pos-1);//delete the email from folder and save it
                            if(rev != null)
                            {
                                deleteEmail(rev);//add the removed email to trash
                                System.out.println("");
                                System.out.println(rev.getSubject()+" has successfully been moved to the trash.");
                            }
                        }
                        System.out.println("");
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"V"://View email contents
                        System.out.print("Enter email index: ");//ask for user input
                        pos = Integer.parseInt(input.readLine());//set user input
                        if(pos >= 0 && pos <= folder.getSize())//check if user input is within the valid range
                        {
                            //Print out all contents of the email
                            System.out.println("To: "+folder.getEmail().get(pos-1).getTo());
                            System.out.println("CC: "+folder.getEmail().get(pos-1).getCC());
                            System.out.println("BCC: "+folder.getEmail().get(pos-1).getBcc());
                            System.out.println("Subject: "+folder.getEmail().get(pos-1).getSubject());
                            System.out.println(folder.getEmail().get(pos-1).getBody());
                        }
                        System.out.println("");
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"SA"://Sort by subject ascending order
                        folder.sortBySubjectAscending();
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"SD"://Sort by subject descending order
                        folder.sortBySubjectDescending();
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"DA"://Sort by date ascending order
                        folder.sortByDateAscending();
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"DD"://Sort by subject descending order
                        folder.sortbyDateDescending();
                        //printing out the information about the folder
                        System.out.println(folder.getName()+"");
                        System.out.println("");
                        if(folder.getSize() == 0)
                            System.out.println(folder.getName()+" is empty.");
                        else
                        {
                            System.out.println(String.format("%-5s%-20s%-20s%-5s%-15s","Index","|","Time","|","Subject"));
                            for(int i=0;i<folder.getSize();i++)
                            {
                                System.out.println(String.format("%-5s%-10s%-30s%-5s%-15s",i+1+"","|",folder.getEmail().get(i).getDateToPrint()+"","|",folder.getEmail().get(i).getSubject()));
                            }
                        }
                        break;
                    case"R":
                        break;
                }
            }
        }
    }
}
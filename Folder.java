import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
/**
 * An abstract data type meant to serve as a representation of a Email object,
 * containing up to 1000 Email objects and with methods to manipulate the Email
 * 
 * @author
 *      XinCheng Chi, SBU ID#：111919385,Recition R30
 * Assignment:
 *      Homework #5 for CSE 214, Summer 2020
 * Date:
 *      August 6,2020
 */
public class Folder implements Comparable, Serializable
{
    //Invariants of Folder class
    private ArrayList<Email> emails;
    private String name;
    private String currentSortingMethod;//0 for date descending, 1 for date ascending
                                        //2 for subject descending, 3 for subject ascending
    private int size;
    
    /**
     * Constructor for Folder class
     * @param folderName 
     *      The name of the folder
     */
    public Folder(String folderName)
    {
        //Initiaize the member variable
        name = folderName;
        currentSortingMethod = "0";//default to be date descending order
        size = 0;
        emails = new ArrayList<Email>(1000);
    }
    /**
     * Add an email to the folder according to the current sorting method
     * @param email 
     *      the email to be added to the folder
     */
    public void addEmail(Email email)
    {
        char sort = currentSortingMethod.charAt(0);//check for sorting method
        
        if(email != null)//if email exist
        {
            switch(sort)
            {
                case'0'://date descending order
                    int posAddDescending = dateDescending(email);
                    if(posAddDescending >= 0)
                        emails.add(posAddDescending, email);
                    size++;//increase size count
                    break;
                case'1'://date ascending order
                    int posAddAsending = dateAscending(email);
                    if(posAddAsending >= 0)
                        emails.add(posAddAsending, email);
                    size++;//increase size count
                    break;
                case'2'://subject descending order
                    int posDes = subjectDescending(email);
                    if(posDes >= 0)
                        emails.add(posDes, email);
                    size++;//increase size count
                    break;
                case'3'://subject ascending order
                    int posAsd = subjectAscending(email);
                    if(posAsd >= 0)
                        emails.add(posAsd, email);
                    size++;//increase size count
                    break;
            }
        }
    }
    /**
     * Helper method to get the correct index to add when in date descending order
     * @param email
     *      the email to be add
     * @return 
     *      the index to add the email
     */
    public int dateDescending(Email email)
    {
        int pos = -1;
        if(emails.isEmpty())//if it is the first email, pos will be at 0
            pos = 0;
        else
        {
            for(int i = 0; i <emails.size(); i++)//check to see where to be added
            {
                if(email.getTime().compareTo(emails.get(i).getTime()) > 0 )
                    return pos = i;
            }
            if(pos == -1)//add to the back of the folder
                pos = emails.size();
        }
        return pos;
    }
    /**
     * Helper method to get the correct index to add when in date ascending order
     * @param email
     *      the email to be add
     * @return 
     *      the index to add the email
     */
    public int dateAscending(Email email)
    {
        int pos = -1;
        if(emails.isEmpty())//if it is the first email, pos will be at 0
            pos = 0;
        else
        {
            for(int i = 0; i <emails.size(); i++)//check to see where to be added
            {
                if(email.getTime().compareTo(emails.get(i).getTime()) < 0 )
                    return pos = i;
            }
            if(pos == -1)//add to the back of the folder
                pos = emails.size();
        }
        return pos;
    }
    /**
     * Helper method to get the correct index to add when in subject descending order
     * @param email
     *      the email to be add
     * @return 
     *      the index to add the email
     */
    public int subjectDescending(Email email)
    {
        int pos = -1;
        if(emails.isEmpty())//if it is the first email, pos will be at 0
            pos = 0;
        else
        {
            for(int i = 0; i <emails.size(); i++)//check to see where to be added
            {
                if(email.getSubject().compareTo(emails.get(i).getSubject()) > 0)
                    return pos = i;
            }
            if(pos == -1)//add to the back of the folder
                pos = emails.size();
        }
        return pos;
    }
    /**
     * Helper method to get the correct index to add when in subject ascending order
     * @param email
     *      the email to be add
     * @return 
     *      the index to add the email
     */
    public int subjectAscending(Email email)
    {
        int pos = -1;
        if(emails.isEmpty())//if it is the first email, pos will be at 0
            pos = 0;
        else
        {
            for(int i = 0; i <emails.size(); i++)//check to see where to be added
            {
                if(email.getSubject().compareTo(emails.get(i).getSubject()) < 0)
                    return pos = i;
            }
            if(pos == -1)//add to the back of the folder
                pos = emails.size();
        }
        return pos;
    }
    /**
     * To remove a email at the given index
     * @param index
     *      the index for which email to be removed
     * @return 
     *      the email that is removed, or null if email does not exist at the
     *      given index
     */
    public Email removeEmail(int index)
    {
        Email rem = null;//default to null
        
        if(index >= 0 && index < emails.size())//if index is within the valid range
        {
            rem = emails.get(index);//get the email at that index
            if(rem != null)//if email exist in index
            {
                emails.remove(index);//remove the email
            }
        }
        size--;//decrease size count
        return rem;//return the email that is removed.
    }
    /**
     * Sort the email in the folder by subject ascending order
     */
    public void sortBySubjectAscending()
    {
        Collections.sort(emails, new SubjectAsdComparator());
        currentSortingMethod = "3";
    }
    /**
     * Sort the email in the folder by subject descending order
     */
    public void sortBySubjectDescending()
    {
        Collections.sort(emails, new SubjectDesComparator());
        currentSortingMethod = "2";
    }
    /**
     * Sort the email in the folder by date ascending order
     */
    public void sortByDateAscending()
    {
        Collections.sort(emails, new DateAsdComparator());
        currentSortingMethod = "1";
    }
    /**
     * Sort the email in the folder by date descending order
     */
    public void sortbyDateDescending()
    {
        Collections.sort(emails, new DateDesComparator());
        currentSortingMethod = "0";
    }
    /**
     * over ride the compareTo method because folder implements comparable
     * @param t
     *      the object to be compared
     * @return 
     *      Not supported yet
     */
    public int compareTo(Object t) 
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    /**
     * Accessor method for name
     * @return 
     *      the name of the folder
     */
    String getName()
    {
        return name;
    }
    /**
     * Accessor method for current sorting method
     * @return 
     *      the current sorting method
     */
    String getCurentSortingMethod()
    {
        return currentSortingMethod;
    }
    /**
     * Accessor method for email in the folder
     * @return 
     *      the emails in the folder
     */
    ArrayList<Email> getEmail()
    {
        return emails;
    }
    /**
     * Accessor method for size of the folder
     * @return 
     *      the size of the folder
     */
    int getSize()
    {
        return size;
    }
    /**
     * Mutator method for name
     * @param names 
     *      the name of the folder that will be changed to
     */
    public void setName(String names)
    {
        name = names;
    }
    /**
     * Mutator method for current sorting method
     * @param sorting 
     *      the current sorting method that will be changed to
     */
    public void setCurrentSortingMethod(String sorting)
    {
        currentSortingMethod = sorting;
    }
    /**
     * Mutator method for emails in the folder
     * @param list 
     *      the new sets of email that will be set to the folder
     */
    public void setEmailList(ArrayList<Email> list)
    {
        emails = list;
    }
    /**
     * Mutator method for size of the folder
     * @param sizes 
     *      the size of the folder that will be set to
     */
    public void setSize(int sizes)
    {
        size = sizes;
    }
}
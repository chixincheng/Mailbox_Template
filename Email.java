import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * An Email object which contains information about a particular
 * email, it have 6 member variables for to,cc,bcc,subject,body and timestamp.
 * 
 * @author
 *      XinCheng Chi, SBU ID#：111919385,Recition R30
 * Assignment:
 *      Homework #5 for CSE 214, Summer 2020
 * Date:
 *      August 6,2020
 */
public class Email implements Serializable
{
    //Invariants of Email class
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private GregorianCalendar timestamp;
    /**
     * Constructor of Email class
     * @param sendTo
     *      where to send this email
     * @param cc1
     *      who will also receive this email(recipients will see each other)
     * @param bcc1
     *      who will also receive this email(recipients will not see each other)
     * @param subject1
     *      The title of this email
     * @param body1
     *      The main body text of this email
     * @param time 
     *      When is this email being send
     */
    public Email(String sendTo, String cc1, String bcc1, String subject1, String body1, GregorianCalendar time)
    {
        //set value for the member variables to the given parameter
        to = sendTo;
        cc = cc1;
        bcc = bcc1;
        subject = subject1;
        body = body1;
        timestamp = time;
    }
    /**
     * Accessor method for to
     * @return 
     *      the to member variable
     */
    String getTo()
    {
        return to;
    }
    /**
     * Accessor method for cc
     * @return 
     *      the cc member variable
     */
    String getCC()
    {
        return cc;
    }
    /**
     * Accessor method for bcc
     * @return 
     *      the bcc member variable
     */
    String getBcc()
    {
        return bcc;
    }
    /**
     * Accessor method for subject
     * @return 
     *      the subject member variable
     */
    String getSubject()
    {
        return subject;
    }
    /**
     * Accessor method for body
     * @return 
     *      the body member variable
     */
    String getBody()
    {
        return body;
    }
    /**
     * Accessor method for time
     * @return 
     *      the timestamp member variable
     */
    GregorianCalendar getTime()
    {
        return timestamp;
    }
    /**
     * Helper method to conver timestamp to a actual date to print for clearness
     * @return 
     *      The actual date that the timestamp represents
     */
    String getDateToPrint()
    {
        Date date = timestamp.getTime();
        String pattern = "HH:mm:ss MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    /**
     * Mutator method for to
     * @param sendTo 
     *      the value that to will be set to
     */
    public void setTo(String sendTo)
    {
        to = sendTo;
    }
    /**
     * Mutator method for cc
     * @param cc1
     *      the value that cc will be set to
     */
    public void setCC(String cc1)
    {
        cc = cc1;
    }
    /**
     * Mutator method for bcc
     * @param bcc1
     *      the value that bcc will be set to
     */
    public void setBCC(String bcc1)
    {
        bcc = bcc1;
    }
    /**
     * Mutator method for subject
     * @param subject1
     *      the value that subject will be set to
     */
    public void setSubject(String subject1)
    {
        subject = subject1;
    }
    /**
     * Mutator method for body
     * @param body1
     *      the value that body will be set to
     */
    public void setBody(String body1)
    {
        body = body1;
    }
    /**
     * Mutator method for timestamp
     * @param timenow
     *      the value that timestamp will be set to
     */
    public void setTime(GregorianCalendar timenow)
    {
        timestamp = timenow;
    }
}
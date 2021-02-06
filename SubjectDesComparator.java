import java.util.Comparator;
/**
 * The comparator to help with sorting in folder class, this is subject descending sorting
 * 
 * @author
 *      XinCheng Chi, SBU ID#：111919385,Recition R30
 * Assignment:
 *      Homework #5 for CSE 214, Summer 2020
 * Date:
 *      August 6,2020
 */
public class SubjectDesComparator implements Comparator<Email>
{
    public int compare(Email t, Email t1) 
    {
        return -(t.getSubject().compareTo(t1.getSubject()));
    }
}
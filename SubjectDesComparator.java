import java.util.Comparator;
/**
 * The comparator to help with sorting in folder class, this is subject descending sorting
 * 
 * @author
 *      XinCheng Chi
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

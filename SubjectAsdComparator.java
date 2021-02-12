import java.util.Comparator;
/**
 * The comparator to help with sorting in folder class, this is subject ascending sorting
 * 
 * @author
 *      XinCheng Chi
 * Date:
 *      August 6,2020
 */
public class SubjectAsdComparator  implements Comparator<Email>
{
    public int compare(Email t, Email t1) 
    {
        return t.getSubject().compareTo(t1.getSubject());
    }
}

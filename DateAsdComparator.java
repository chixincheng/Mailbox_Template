import java.util.Comparator;
/**
 * The comparator to help with sorting in folder class, this is date ascending sorting
 * 
 * @author
 *      XinCheng Chi
 * Date:
 *      August 6,2020
 */
public class DateAsdComparator implements Comparator<Email>
{
    public int compare(Email t, Email t1) 
    {
        return t.getTime().compareTo(t1.getTime());
    }
}

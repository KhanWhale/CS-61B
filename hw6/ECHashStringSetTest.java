import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class ECHashStringSetTest  {

    @Test
    public void testNothing() {
        ECHashStringSet myHash = new ECHashStringSet();
        myHash.put("hi");
        assertTrue(myHash.contains("hi"));
    }
}

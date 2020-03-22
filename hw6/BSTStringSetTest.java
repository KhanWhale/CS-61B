import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Aniruddh Khanwale
 */
public class BSTStringSetTest  {

    @Test
    public void myTest() {
        BSTStringSet myBST = new BSTStringSet();
        myBST.put("a");
        myBST.put("b");
        myBST.put("c");
        myBST.put("d");
        assertTrue(myBST.contains("d"));
        assertFalse(myBST.contains("adkv"));
    }
}

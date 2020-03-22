import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class BSTStringSetTest  {
    @Test
    public void putTest() {
        BSTStringSet test = new BSTStringSet();
        test.put("hi");
        test.put("there");
        test.put("wow");
        assertTrue(test.contains("hi"));
        assertTrue(test.contains("there"));
        assertTrue(test.contains("wow"));
        assertFalse(test.contains("a"));
    }

    @Test
    public void asListTest() {
        BSTStringSet test = new BSTStringSet();
        test.put("hi");
        test.put("there");
        test.put("wow");
        System.out.println(test.asList());
    }

    @Test
    public void iteratorTest() {
        BSTStringSet test = new BSTStringSet();
        test.put("hi");
        test.put("there");
        test.put("wow");
        test.put("wyz");
        test.put("yid");
        test.put("zzldkf");
        System.out.println(test.asList());
    }

    @Test
    public void emptyTest() {
        BSTStringSet test = new BSTStringSet();
        test.put("a");
        test.put("b");
        test.put("c");
        test.put("d");
        System.out.println(test.asList());
    }
}

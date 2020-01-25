import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

/** Tests for hw0. 
 *  @author YOUR NAMES HERE
 */
public class Tester {

    /* Feel free to add your own tests.  For now, you can just follow
     * the pattern you see here.  We'll look into the details of JUnit
     * testing later.
     *
     * To actually run the tests, just use
     *      java Tester 
     * (after first compiling your files).
     *
     * DON'T put your HW0 solutions here!  Put them in a separate
     * class and figure out how to call them from here.  You'll have
     * to modify the calls to max, threeSum, and threeSumDistinct to
     * get them to work, but it's all good practice! */

    @Test
    public void maxTest() {
        // Change call to max to make this call yours.
        assertEquals(14, hw0.max(new int[] { 0, -5, 2, 14, 10 }));
        assertEquals(3, hw0.max(new int[]{0, 1, 2 ,3}));
        assertEquals(80, hw0.max(new int[]{-6, 80, -7, -8}));
        assertEquals(-1, hw0.max(new int[]{-2,-1,-3,-4}));
    }

    @Test
    public void threeSumTest() {
        assertTrue(hw0.threeSum(new int[] { -6, 3, 10, 200 }));
        assertTrue(hw0.threeSum(new int[]{-6, 2, 4}));
        assertFalse(hw0.threeSum(new int[]{-6, 2, 5}));
        assertTrue(hw0.threeSum(new int[]{8, 2, -1, 15}));
        assertTrue(hw0.threeSum(new int[]{8, 2, -1, -1, 15}));
        assertTrue(hw0.threeSum(new int[]{5, 1, 0, 3, 6}));
    }

    @Test
    public void threeSumDistinctTest() {
        assertFalse(hw0.threeSumDistinct(new int[] { -6, 3, 10, 200 }));
        assertTrue(hw0.threeSumDistinct(new int[]{-6, 2, 4}));
        assertFalse(hw0.threeSumDistinct(new int[]{-6, 2, 5}));
        assertFalse(hw0.threeSumDistinct(new int[]{8, 2, -1, 15}));
        assertTrue(hw0.threeSumDistinct(new int[]{8, 2, -1, -1, 15}));
        assertFalse(hw0.threeSumDistinct(new int[]{5, 1, 0, 3, 6}));
    }

    public static void main(String[] unused) {
        textui.runClasses(Tester.class);
    }
}

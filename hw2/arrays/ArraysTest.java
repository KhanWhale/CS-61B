package arrays;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import image.MatrixUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Aniruddh Khanwale
 */

public class ArraysTest {
    /** FIXME
     */
    @Test
    public void testCatenate() {
        int[] A1 = {1, 2, 3};
        int[] B1 = {5, 6, 7};
        int[] cat1 = {1,2,3,5,6,7};
        assertArrayEquals(cat1, Arrays.catenate(A1, B1));
        int[] emptyA = new int[0];
        int[] fullB = {4, 5, 54, 78};
        int[] emptyACat = {4,5,54,78};
        assertArrayEquals(emptyACat, Arrays.catenate(emptyA, fullB));
        assertArrayEquals(emptyACat, Arrays.catenate(fullB, emptyA));
        int[] emptyB = new int[0];
        int[] bothEmpty = new int[0];
        assertArrayEquals(bothEmpty, Arrays.catenate(emptyA, emptyB));
        assertArrayEquals(bothEmpty, Arrays.catenate(emptyB, emptyB));
    }

    @Test
    public void testRemove() {
        int[] A = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] remove_none = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(remove_none, Arrays.remove(A, 0, 0));
        assertArrayEquals(remove_none, Arrays.remove(A, 5, 0));
        assertArrayEquals(remove_none, Arrays.remove(A, 10, 0));
        int[] remove_first = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(remove_first, Arrays.remove(A, 0, 1));
        int[] remove_first_two = {2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(remove_first_two, Arrays.remove(A, 0, 2));
        int[] remove_random = {0,1, 2, 3, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(remove_random, Arrays.remove(A, 4, 1));
        int[] remove_random_two = {0,1, 2, 3, 4, 7, 8, 9, 10};
        assertArrayEquals(remove_random_two, Arrays.remove(A, 5, 2));
        int[] remove_last = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(remove_last, Arrays.remove(A, 10, 1));
        int[] remove_last_two = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(remove_last_two, Arrays.remove(A, 9, 2));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}

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

    @Test
    public void testNaturalRuns() {
        int[] original = {1, 3, 7, 5, 4, 6, 9, 10};
        int[] originalNR1 = {1, 3, 7};
        int[] originalNR2 = {5};
        int[] originalNR3 = {4, 6, 9, 10};
        int[][] originalNR = {originalNR1, originalNR2, originalNR3};
        assertEquals(3, Arrays.naturalRuns(original).length);
        assertArrayEquals(originalNR, Arrays.naturalRuns(original));
        int[] empty = {};
        int[][] emptyNR = {};
        assertEquals(0, Arrays.naturalRuns(empty).length);
        assertArrayEquals(emptyNR, Arrays.naturalRuns(empty));
        int [] only_ascending = {0,1,2,3,4,5};
        int [][] only_ascNR = {only_ascending};
        assertEquals(1, Arrays.naturalRuns(only_ascending).length);
        assertArrayEquals(only_ascNR, Arrays.naturalRuns(only_ascending));
        int [] only_desc = {5, 4, 3, 2, 1};
        int[] only_desc1 = {5};
        int[] only_desc2 = {4};
        int[] only_desc3 = {3};
        int[] only_desc4 = {2};
        int[] only_desc5 = {1};
        int[][] only_descNR = {only_desc1, only_desc2, only_desc3,only_desc4,only_desc5};
        assertArrayEquals(only_descNR, Arrays.naturalRuns(only_desc));
        assertEquals(5, Arrays.naturalRuns(only_desc).length);
        int [] all_equal = {1,1,1,};
        int[][] all_equal_desc = {only_desc5,only_desc5,only_desc5};
        assertArrayEquals(all_equal_desc, Arrays.naturalRuns(all_equal));
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}

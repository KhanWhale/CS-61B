package arrays;

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
        int[] cat1 = {1, 2, 3, 5, 6, 7};
        assertArrayEquals(cat1, Arrays.catenate(A1, B1));
        int[] emptyA = new int[0];
        int[] fullB = {4, 5, 54, 78};
        int[] emptyACat = {4, 5, 54, 78};
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
        int[] removeNone = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(removeNone, Arrays.remove(A, 0, 0));
        assertArrayEquals(removeNone, Arrays.remove(A, 5, 0));
        assertArrayEquals(removeNone, Arrays.remove(A, 10, 0));
        int[] removeFirst = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(removeFirst, Arrays.remove(A, 0, 1));
        int[] removeFirstTwo = {2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(removeFirstTwo, Arrays.remove(A, 0, 2));
        int[] removeRandom = {0,1, 2, 3, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(removeRandom, Arrays.remove(A, 4, 1));
        int[] removeRandomTwo = {0,1, 2, 3, 4, 7, 8, 9, 10};
        assertArrayEquals(removeRandomTwo, Arrays.remove(A, 5, 2));
        int[] removeLast = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(removeLast, Arrays.remove(A, 10, 1));
        int[] removeLastTwo = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(removeLastTwo, Arrays.remove(A, 9, 2));
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
        int [] onlyAscending = {0, 1, 2, 3, 4, 5};
        int [][] onlyAscNR = {onlyAscending};
        assertEquals(1, Arrays.naturalRuns(onlyAscending).length);
        assertArrayEquals(onlyAscNR, Arrays.naturalRuns(onlyAscending));
        int [] onlyDesc = {5, 4, 3, 2, 1};
        int[] onlyDesc1 = {5};
        int[] onlyDesc2 = {4};
        int[] onlyDesc3 = {3};
        int[] onlyDesc4 = {2};
        int[] onlyDesc5 = {1};
        int[][] onlyDescNR = {onlyDesc1, onlyDesc2, onlyDesc3,onlyDesc4,onlyDesc5};
        assertArrayEquals(onlyDescNR, Arrays.naturalRuns(onlyDesc));
        assertEquals(5, Arrays.naturalRuns(onlyDesc).length);
        int [] all_equal = {1, 1, 1};
        int[][] all_equal_desc = {onlyDesc5,onlyDesc5,onlyDesc5};
        assertArrayEquals(all_equal_desc, Arrays.naturalRuns(all_equal));
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}

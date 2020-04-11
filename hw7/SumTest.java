import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SumTest {
    /**
     * Checks the correctness of each sorting algorithm on an
     * already sorted array.
     */
    @Test
    public void alreadySortedCorrectnessTest() {
        int[] A = new int[]{0, 1, 2, 3, 4, 5};
        int[] B = new int[]{7, 8, 9, 12, 10, 11};
        assertTrue(Sum.sumsTo(A, B, 17));
    }
}

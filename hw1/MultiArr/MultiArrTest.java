import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int [][] arr = {{0, 1, 2, 3, 4}, {5, 6, 7, 8, 9},{10, 11, 12, 13, 14}};
        assertEquals(14, MultiArr.maxValue(arr));
        int [][] arr1 = {{-5, -8}, {-17, -50}};
        assertEquals(-5, MultiArr.maxValue(arr1));
        int [][] arr2 = {{0, 1, 8, 69, 73}, {5, 6, 98, 10, 18}, {4, 78, 2, 3, 9},{5, 6, 9, 100, 72}};
        assertEquals(100, MultiArr.maxValue(arr2));
    }

    @Test
    public void testAllRowSums() {
        int [][] arr = {{0, 1, 2, 3, 4}, {5, 6, 7, 8, 9},{10, 11, 12, 13, 14}};
        int[] sums = {10, 35,60};
        int[] temp = MultiArr.allRowSums(arr);
        for(int i = 0; i < temp.length; i += 1){
            assertEquals(sums[i], temp[i]);
        }

        int [][] arr1 = {{-5, -8}, {-17, -50}};
        int[] sums1 = new int[]{-13, -67};
        temp = MultiArr.allRowSums(arr1);
        for(int i = 0; i < temp.length; i += 1){
            assertEquals(sums1[i], temp[i]);
        }
        int [][] arr2 = {{0, 1, 8, 69, 73}, {5, 6, 98, 10, 18}, {4, 78, 2, 3, 9},{5, 6, 9, 100, 72}};
        int[] sums2 = new int[]{151,137,96,192};
        temp = MultiArr.allRowSums(arr2);
        for(int i = 0; i < temp.length; i += 1){
            assertEquals(sums2[i], temp[i]);
        }
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}

/** Multidimensional array
 *  @author Aniruddh Khanwale
 */
public class MultiArr {

    /**
    {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
    Rows: 2
    Columns: 3
    
    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    public static void printRowAndCol(int[][] arr) {
        System.out.println("Rows: " + arr.length);
        System.out.println("Columns: " + arr[0].length);
    } 

    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        int max = Integer.MIN_VALUE;
        for (int row = 0; row < arr.length; row += 1) {
            for (int col = 0; col < arr[row].length; col += 1) {
                int val = arr[row][col];
                if (val > max) {
                    max = val;
                }
            }
        }
        return max;
    }

    /**Return an array where each element is the sum of the 
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] sums = new int[arr.length];
        for (int row = 0; row < arr.length; row += 1) {
            int rowSum = 0;
            for (int col = 0; col < arr[row].length; col += 1) {
                rowSum += arr[row][col];
            }
            sums[row] = rowSum;
        }
        return sums;
    }
}

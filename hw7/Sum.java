import java.util.Arrays;

/** HW #7, Two-sum problem.
 * @author Aniruddh Khanwale
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        int[] combined = new int[A.length + B.length];
        System.arraycopy(A, 0, combined, 0, A.length);
        System.arraycopy(B, 0, combined, A.length, B.length);
        Arrays.sort(combined);
        int leftInd = 0;
        int rightInd = combined.length - 1;
        while (leftInd < rightInd) {
            int sum = combined[leftInd] + combined[rightInd];
            if (sum == m) {
                return true;
            } else if (sum < m) {
                leftInd += 1;
            } else {
                rightInd += 1;
            }
        }
        return false;
    }

}

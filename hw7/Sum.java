import java.lang.reflect.Array;
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
        int left = 0;
        int right = combined.length - 1;
        while (left < right) {
            if (combined[left] + combined[right] == m) {
                return true;
            } else if (combined[left] + combined[right] < m) {
                left+= 1;
            } else {
                right -= 1;
            }
        }
        return false;
    }

}

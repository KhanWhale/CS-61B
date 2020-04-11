import java.lang.reflect.Array;
import java.util.Arrays;

/** HW #7, Two-sum problem.
 * @author Aniruddh Khanwale
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        Arrays.sort(B);
        for (int i = 0; i < A.length; i += 1) {
            for (int j = 0; j < B.length; j += 1) {
                if (A[i] + B[j] == m) {
                    return true;
                } else if (A[i] + B[j] > m) {
                    break;
                }
            }
        }
        return false;
    }

}

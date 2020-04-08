package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Aniruddh Khanwale
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int [] catenated = new int[A.length+B.length];
        System.arraycopy(A, 0, catenated, 0, A.length);
        System.arraycopy(B, 0, catenated, A.length, B.length);
        return catenated;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] removed = new int[A.length-len];
        System.arraycopy(A, 0, removed, 0, start);
        System.arraycopy(A, start+len, removed, start, A.length-start-len);
        return removed;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        if (A.length == 0) {
            int[][] empty = {};
            return empty;
        } else {
            int max_so_far = Integer.MAX_VALUE;
            int num_subs = 0;
            for (int i = 0; i < A.length; i += 1) {
                if (A[i] <= max_so_far) {
                    num_subs += 1;
                }
                max_so_far = A[i];
            }
            int[][] natural = new int[num_subs][];
            int[] ascend = new int[1];
            ascend[0] = A[0];
            max_so_far = A[0];
            num_subs = 0;
            for (int i = 1; i < A.length; i += 1) {
                if (A[i] <= max_so_far) {
                    natural[num_subs] = ascend;
                    num_subs += 1;
                    ascend = new int[1];
                    ascend[0] = A[i];
                } else {
                    int[] temp = new int[1];
                    temp[0] = A[i];
                    ascend = catenate(ascend, temp);
                }
                max_so_far = A[i];
            }
            natural[num_subs] = ascend;
            return natural;
        }
    }
}

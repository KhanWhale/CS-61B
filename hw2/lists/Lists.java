package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */
import java.util.Arrays;

/** List problem.
 *  @author Aniruddh Khanwale
 */
class Lists {

    /* B. */
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sub lists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {
        if (L == null) {
            return null;
        } else {
            IntList tail = null;
            IntList cp = L;
            IntList start = L;
            for(int head = L.head; L != null && head < L.head; L = L.tail) {
                head = L.head;
                tail = L.tail;
                start = L;
            }
            start.tail = null;
            return new IntListList(cp, naturalRuns(tail));
        }
    }
}

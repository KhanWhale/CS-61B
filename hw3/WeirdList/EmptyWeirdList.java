/**
 * An empty list is the tail of a WeirdList.
 *
 * @author Aniruddh Khanwale
 */
public class EmptyWeirdList extends WeirdList {
    /** The head of the empty List, always set to 0 */
    private int head = 0;
    /** Constructs a new Empty Weird List */
    public EmptyWeirdList() {
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public WeirdList map(IntUnaryFunction func) {
        return this;
    }
}

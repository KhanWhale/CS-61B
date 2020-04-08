/** A unary function that sums the elements of a group when
 * called repeatedly.
 *
 * @author Aniruddh Khanwale
 */
public class SumUnaryFunction implements IntUnaryFunction {
    /** The running sum. */
    private int sum = 0;
    /** Constructs a new SumUnaryFunction. */
    public SumUnaryFunction() {
    }
    @Override
    public int apply(int x) {
        sum += x;
        return x;
    }
    /** Return sum. */
    public int getSum() {
        return sum;
    }
}

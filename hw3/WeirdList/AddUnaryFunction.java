/** An AddUnaryFunction adds an inputed number to the input.
 *
 * @author Aniruddh Khanwale
 */
public class AddUnaryFunction implements IntUnaryFunction {
    /** The value to be added to the input */
    private int addend = 0;
    /** Constructs a new AddUnaryFunction with addend add */
    public AddUnaryFunction(int add) {
        addend = add;
    }
    @Override
    public int apply(int x) {
        return x + addend;
    }

}

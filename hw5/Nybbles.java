import antlr.StringUtils;

/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Aniruddh Khanwale
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. 
    * DON'T CHANGE THIS.*/
    public Nybbles(int N) {
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int whichData = k / 8;
            k -= 8*whichData;
            String myDataString = String.format("%32s", Integer.toBinaryString(_data[whichData]));
            myDataString = myDataString.replaceAll(" ", "0");
            int endIndex = myDataString.length()- (4 * k);
            String myNybble = myDataString.substring(endIndex - 4, endIndex);
            int parsed = Integer.parseInt(myNybble, 2);
            if (myNybble.charAt(0) == '0') {
                return parsed;
            } else {
                return -8;
            }
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else if (val >= 0) {
            int whichData = k / 8;
            String myValString = String.format("%4s", Integer.toBinaryString(val));
            myValString = myValString.replaceAll(" ", "0");
            k -= 8*whichData;
            String myDataString = String.format("%32s", Integer.toBinaryString(_data[whichData]));
            myDataString = myDataString.replaceAll(" ", "0");
            int endIndex = myDataString.length()- (4 * k);
            myDataString = myDataString.substring(0, endIndex - 4) + myValString + myDataString.substring(endIndex);
            _data[whichData] = Integer.valueOf(myDataString, 2);
        } else {
            int whichData = k / 8;
            String myValString = Integer.toBinaryString(val);
            myValString = myValString.substring(myValString.length() - 4);
            k -= 8*whichData;
            String myDataString = String.format("%32s", Integer.toBinaryString(_data[whichData]));
            myDataString = myDataString.replaceAll(" ", "0");
            int endIndex = myDataString.length()- (4 * k);
            myDataString = myDataString.substring(0, endIndex - 4) + myValString + myDataString.substring(endIndex);
            if (myDataString.charAt(0) == '1') {
                long avoidOverflows = Long.parseLong(myDataString, 2);
                _data[whichData] = (int) avoidOverflows;
            } else {
                _data[whichData] = Integer.valueOf(myDataString, 2);
            }

        }
    }

    /** DON'T CHANGE OR ADD TO THESE.*/
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** A set of String values.
 *  @author Aniruddh Khanwale
 */
class ECHashStringSet implements StringSet {
    @Override
    public void put(String s) {
        Integer myHash = s.hashCode();
        if (myHash < 0) {
            myHash = (s.hashCode() & 0x7fffffff) % 3;
        } else {
            _myHashTable.put(myHash, s);
        }
    }

    @Override
    public boolean contains(String s) {
        Integer myHash = s.hashCode();
        if (myHash < 0) {
            myHash = (s.hashCode() & 0x7fffffff) % 3;
        }
        return _myHashTable.containsKey(myHash);
    }

    @Override
    public List<String> asList() {
        return new ArrayList<String>();
    }

    public ECHashStringSet () {

    }
    private HashMap<Integer, String> _myHashTable = new HashMap<Integer, String>();
}

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** A set of String values.
 *  @author Aniruddh Khanwale
 */
class ECHashStringSet implements StringSet {
    @Override
    public void put(String s) {
        boolean full = true;
        for(int i = 0; i < _myHashTable.length; i += 1) {
            if(_myHashTable[i] == null) {
                full = false;
                break;
            }
        }
        if (full) {
            ArrayList[] _temp = _myHashTable;
            _myHashTable = new ArrayList[_temp.length * 2];
            for (int i = 0; i < _temp.length; i += 1) {
                _myHashTable[i] = _temp[i];
            }
        }
            int myHash = s.hashCode();
            if (myHash < 0) {
                myHash = (s.hashCode() & 0x7fffffff) % _myHashTable.length;
            } else {
                myHash = s.hashCode() % _myHashTable.length;
            }
            _myHashTable[myHash].add(s);
    }

    @Override
    public boolean contains(String s) {
        int myHash = s.hashCode();
        if (myHash < 0) {
            myHash = (s.hashCode() & 0x7fffffff) % _myHashTable.length;
        } else {
            myHash = s.hashCode() % _myHashTable.length;
        }
        return _myHashTable[myHash].contains(s);
    }

    @Override
    public List<String> asList() {
        return new ArrayList<String>();
    }

    public ECHashStringSet () {

    }
    private ArrayList[] _myHashTable = new ArrayList[4];
}

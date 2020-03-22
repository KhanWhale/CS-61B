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
            ArrayList[] temp = new ArrayList[_myHashTable.length * 2];
            for (int i = 0; i < _myHashTable.length; i += 1) {
                temp[i] = _myHashTable[i];
            }
            _myHashTable=temp;
        }
            int myHash = s.hashCode();
            if (myHash < 0) {
                myHash = (s.hashCode() & 0x7fffffff) % _myHashTable.length;
            } else {
                myHash = s.hashCode() % _myHashTable.length;
            }
            if (_myHashTable[myHash] == null) {
                _myHashTable[myHash] = new ArrayList<String>();
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
        if (_myHashTable[myHash] == null) {
            return false;
        } else {
            return _myHashTable[myHash].contains(s);
        }

    }

    @Override
    public List<String> asList() {
        ArrayList<String> myList = new ArrayList<String>();
        for (int i = 0; i < _myHashTable.length; i += 1) {
            if (_myHashTable[i] != null) {
                for (int  j= 0 ; j < _myHashTable[i].size(); j += 1) {
                    ArrayList myarr = _myHashTable[i];
                    String mystr = (String) myarr.get(j);
                    myList.add(mystr);
                }
            }
        }
        return myList;
    }

    public ECHashStringSet () {

    }
    private ArrayList[] _myHashTable = new ArrayList[4];
}

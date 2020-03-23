
import java.util.ArrayList;
import java.util.List;

/** A set of String values.
 *  @author Aniruddh Khanwale
 */
class ECHashStringSet implements StringSet {
    @Override
    public void put(String s) {
        if (totalEls / _myHashTable.length > 5) {
            ECHashStringSet tempSet = new ECHashStringSet(_myHashTable.length * 2, _myHashTable);
            totalEls = tempSet.totalEls;
            _myHashTable = tempSet.getTable();
        }
        int hash = getHash(s);
        if (_myHashTable[hash] == null) {
            _myHashTable[hash] = new ArrayList<String>();
        }
        totalEls += 1;
        _myHashTable[hash].add(s);
    }
    @Override
    public boolean contains(String s) {
        int hash = getHash(s);
        if (_myHashTable[hash] == null) {
            return false;
        } else {
            return _myHashTable[hash].contains(s);
        }
    }

    @Override
    public List<String> asList() {
        ArrayList<String> myList = new ArrayList<String>();
        for (int i = 0; i < _myHashTable.length; i += 1) {
            if (_myHashTable[i] != null) {
                for (int  j= 0 ; j < _myHashTable[i].size(); j += 1) {
                    myList.add((String) _myHashTable[i].get(j));
                }
            }
        }
        return myList;
    }

    public ECHashStringSet () {

    }
    public ECHashStringSet(int numBuckets, ArrayList[] table) {
        _myHashTable = new ArrayList[numBuckets];
        for (int i = 0; i < table.length; i += 1) {
            if (table[i] != null) {
                for (int j = 0; j < table[i].size(); j += 1) {
                    put((String) table[i].get(j));
                }
            }
        }
    }
    public ArrayList[] getTable() {
        return _myHashTable;
    }
    public int getHash(String s) {
        int hash = s.hashCode();
        if(hash < 0) {
            hash = hash & 0x7fffffff;
        }
        return hash % _myHashTable.length;
    }
    private ArrayList[] _myHashTable = new ArrayList[2];
    int totalEls = 0;
}

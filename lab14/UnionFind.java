
/** Disjoint sets of contiguous integers that allows (a) finding whether
 *  two integers are in the same set and (b) unioning two sets together.  
 *  At any given time, for a structure partitioning the integers 1 to N, 
 *  into sets, each set is represented by a unique member of that
 *  set, called its representative.
 *  @author Aniruddh Khanwale
 */
public class UnionFind {

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        array_struct = new int[N + 1];
        sizes = new int[N+1];
        for (int i = 1; i <= N; i += 1) {
            array_struct[i] = i;
        }
        computeSizes();
    }

    /** Return the representative of the set currently containing V.
     *  Assumes V is contained in one of the sets.  */
    public int find(int v) {
        if (array_struct[v] != v) {
            return find(array_struct[v]);
        } else {
            return v;
        }
    }

    void computeSizes() {
        sizes = new int[array_struct.length];
        for (int i = 1; i < array_struct.length; i += 1) {
            int root = find(i);
            sizes[root] += 1;
        }
    }
    /** Return true iff U and V are in the same set. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single set, returning its representative. */
    public int union(int u, int v) {
        int uRoot = find(u);
        int vRoot = find(v);
        if (uRoot == vRoot) {
            return uRoot;
        } else {
            int uSize = sizes[uRoot];
            int vSize = sizes[vRoot];
            if (vSize > uSize) {
                array_struct[uRoot] = vRoot;
                computeSizes();
                return vRoot;
            } else {
                array_struct[vRoot] = uRoot;
                computeSizes();
                return uRoot;
            }

        }
    }

    // FIXME
    int[] array_struct;

    int[] sizes;
}

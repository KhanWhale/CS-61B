/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        _colInd = input.colNameToIndex(colName);
        _sub = subStr;
    }

    @Override
    protected boolean keep() {
        String colData = _next.getValue(_colInd);
        if (colData.contains(_sub)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Store column Index.
     */
    private int _colInd;

    /**
     * Store reference Substring.
     */
    private String _sub;

}

/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        _colIndex = input.colNameToIndex(colName);
        _match = match;

    }

    @Override
    protected boolean keep() {
        String colData = _next.getValue(_colIndex);
        if (colData.equals(_match)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Store column index.
     */
    private int _colIndex;

    /**
     * Store string to match.
     */
    private String _match;
}

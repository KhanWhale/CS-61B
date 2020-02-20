/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        _colIndex = input.colNameToIndex(colName);
        _ref = ref;
    }

    @Override
    protected boolean keep() {
        String colData = _next.getValue(_colIndex);
        if (colData.compareTo(_ref) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Store column index in input table.
     */
    private int _colIndex;

    /**
     * Store reference string.
     */
    private String _ref;
}

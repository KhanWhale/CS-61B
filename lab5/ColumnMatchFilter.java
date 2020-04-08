/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
     super(input);
    _col1Index = input.colNameToIndex(colName1);
    _col2Index = input.colNameToIndex(colName2);
    }

    @Override
    protected boolean keep() {
        String col1Data = _next.getValue(_col1Index);
        String col2Data = _next.getValue(_col2Index);
        if (col1Data.equals(col2Data)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Store the index of the first columnName.
     */
    private int _col1Index;

    /**
     * Store the index of the first columnName.
     */
    private int _col2Index;
}

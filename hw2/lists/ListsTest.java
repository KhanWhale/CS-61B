package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for the Lists class
 *
 *  @author Aniruddh Khanwale
 */

public class ListsTest {


   @Test
   public void testNaturalRuns() {
       IntList myList = IntList.list(1);
       assertEquals(IntListList.list(new int[][]{{1}}), Lists.naturalRuns(myList));
       IntList myList1 = IntList.list(0,1,2,3,4);
       assertEquals(IntListList.list(new int[][]{{0,1,2,3,4}}), Lists.naturalRuns(myList1));
       IntList myList2 = IntList.list(4,6,19,3,4,10);
       assertEquals(IntListList.list(new int[][]{{4,6,19}, {3,4,10}}), Lists.naturalRuns(myList2));
       IntList myList3 = IntList.list(7,9,12,12,12, 12, 10,10,4,5);
       assertEquals(IntListList.list(new int[][]{{7,9,12}, {12}, {12}, {12}, {10}, {10}, {4,5}}), Lists.naturalRuns(myList3));


   }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}

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
   }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}

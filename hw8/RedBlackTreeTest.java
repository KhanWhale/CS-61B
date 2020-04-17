import org.junit.Test;
import static org.junit.Assert.*;
public class RedBlackTreeTest {

    @Test
    public void addFifty() {
        RedBlackTree myTree = new RedBlackTree();
        for (int i = 1; i <= 40; i += 1) {
            myTree.insert(i);
        }
        assertEquals(40, myTree.size);
        System.out.println(myTree);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(RedBlackTreeTest.class));
    }
}

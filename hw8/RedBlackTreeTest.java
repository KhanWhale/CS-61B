import org.junit.Test;

public class RedBlackTreeTest {

    @Test
    public void addFifty() {
        RedBlackTree myTree = new RedBlackTree();
        for (int i = 1; i <= 50; i += 1) {
            myTree.insert(i);
        }
        System.out.println(myTree);
    }
}

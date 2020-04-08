package image;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class MatrixUtilsTest {
    @Test
    public void testTranspose() {
        double[] orig1 = {1, 2};
        double[][] original = {orig1};
        double[] transa = {1};
        double [] transb = {2};
        double [][] trans = {transa, transb};
        assertArrayEquals(trans, MatrixUtils.transpose(original));
        double[] orig2b = {1, 2, 3, 4};
        double[] orig2a = {5, 6, 7, 8};
        double[] orig2c = {10, 11, 12, 13};
        double[][] orig2 = {orig2b, orig2a,orig2c};
        double[] trans2b = {1, 5, 10};
        double[] trans2a = {2,6,11};
        double[] trans2c = {3,7,12};
        double[] trans3d = {4, 8, 13};
        double[][] trans2 = {trans2b, trans2a,trans2c, trans3d};
        assertArrayEquals(trans2, MatrixUtils.transpose(orig2));
        double[][] test = new double[][] {{10, 4, 5, 6}, {3, 10, 18, 6}, {8, 5, 19, 6}};
        double[][] test_transpose = new double[][] {{10, 3, 8}, {4,10,5}, {5, 18, 19}, {6, 6, 6}};
        assertArrayEquals(test_transpose, MatrixUtils.transpose(test));
    }

    @Test
    public void testAccumulate() {
        double[] pre1 = {1000000, 1000000, 1000000, 1000000};
        double[] pre2 = {1000000, 75990, 30003, 1000000};
        double[] pre3 = {1000000, 30002, 103046, 1000000};
        double[] pre4 = {1000000, 29515, 38273, 1000000};
        double[] pre5 = {1000000, 73403, 35399, 1000000};
        double[][] pre_vert = {pre1, pre2, pre3, pre4, pre5, pre1};
        double[] ac2 = {2000000, 1075990, 1030003, 2000000};
        double[] ac3 = {2075990, 1060005, 1133049, 2030003};
        double[] ac4 = {2060005,   1089520,   1098278,   2133049};
        double[] ac5 = {2089520,   1162923,   1124919,   2098278};
        double[] ac6 = {2162923,   2124919,   2124919,   2124919};
        double[][] accumulated = {pre1, ac2, ac3, ac4, ac5, ac6};
        assertArrayEquals(accumulated, MatrixUtils.accumulate(pre_vert, MatrixUtils.Orientation.VERTICAL));
        double[] horiz1 = {1000000, 1000000, 1000000, 1000000};
        double[] horiz2 = {1000000, 75990, 30003, 1000000};
        double[] horiz3 = {1000000, 30002, 103046, 1000000};
        double[] horiz4 = {1000000, 29515, 38273, 1000000};
        double[] horiz5 = {1000000, 73403, 35399, 1000000};

    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}

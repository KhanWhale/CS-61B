import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers

        assertEquals(0, 0); */
        assertEquals(10, CompoundInterest.numYears(2029));
        assertEquals(5, CompoundInterest.numYears(2024));
        assertEquals(100, CompoundInterest.numYears(2119));
        assertEquals(38, CompoundInterest.numYears(2057));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        //System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}

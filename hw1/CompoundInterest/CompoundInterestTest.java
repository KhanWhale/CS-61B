import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers

        assertEquals(0, 0); */
        assertEquals(9, CompoundInterest.numYears(2029));
        assertEquals(5, CompoundInterest.numYears(2024));
        assertEquals(100, CompoundInterest.numYears(2119));
        assertEquals(38, CompoundInterest.numYears(2057));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(11.2, CompoundInterest.futureValue(10, 12, 2021), tolerance);
        assertEquals(7.744, CompoundInterest.futureValue(10, -12, 2021), tolerance);
        assertEquals(57391.4, CompoundInterest.futureValue(53000, 1, 2027), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(10.864, CompoundInterest.futureValueReal(10, 12, 2021, 3), tolerance);
        assertEquals(6.98896, CompoundInterest.futureValueReal(10, -12, 2021, 5), tolerance);
        assertEquals(114355.97035073, CompoundInterest.futureValueReal(53000, 1, 2027, -9), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(10500, CompoundInterest.totalSavings(5000, 2021, 10), tolerance);
        assertEquals(22067.5, CompoundInterest.totalSavings(7000, 2021, 5), tolerance);
        assertEquals(19839.984343441, CompoundInterest.totalSavings(1257, 2029, 7), tolerance);

    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(10185.0, CompoundInterest.totalSavingsReal(5000, 2021, 10, 3), tolerance);
        assertEquals(13202.0, CompoundInterest.totalSavingsReal(7000, 2021, 5, 8), tolerance);
        assertEquals(26942.343877633204, CompoundInterest.totalSavingsReal(1257, 2029, 7, -5), tolerance);

    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}

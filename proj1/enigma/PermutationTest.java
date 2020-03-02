package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Aniruddh Khanwale
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private Alphabet alpha = getNewAlphabet();
    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    Permutation getNewPermutation(String cycles, Alphabet alphabet) {
        return new Permutation(cycles, alphabet);
    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    Alphabet getNewAlphabet(String chars) {
        return new Alphabet(chars);
    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    Alphabet getNewAlphabet() {
        return new Alphabet();
    }

    /** Check that PERM has an alphabet ALPHA whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
        assertEquals(perm.alphabet(), alpha);
    }


    /* ***** TESTS ***** */

    /* *** TESTS for Alphabet **** */
    @Test
    public void checkAlphabet() {
        /** Default Alphabet Tests **/
        Alphabet def = getNewAlphabet();
        assertEquals(26, def.size());
        assertTrue(def.contains('A'));
        assertTrue(def.contains('X'));
        assertTrue(def.contains('Z'));
        assertFalse(def.contains('a'));
        assertFalse(def.contains(' '));
        assertEquals('A', def.toChar(0));
        assertEquals('M', def.toChar(12));
        assertEquals('Z', def.toChar(def.size() - 1));
        assertEquals(0, def.toInt('A'));
        assertEquals(12, def.toInt('M'));
        assertEquals(def.size() - 1, def.toInt('Z'));

        /** Non Default Alphabet Tests **/
        Alphabet lower = getNewAlphabet(LOWER_STRING);
        assertEquals(26, def.size());
        assertTrue(lower.contains('a'));
        assertTrue(lower.contains('x'));
        assertTrue(lower.contains('z'));
        assertFalse(lower.contains('A'));
        assertFalse(lower.contains(' '));
        assertEquals('a', lower.toChar(0));
        assertEquals('m', lower.toChar(12));
            assertEquals('z', lower.toChar(lower.size()-1));
        assertEquals(0, lower.toInt('a'));
        assertEquals(12, lower.toInt('m'));
        assertEquals(lower.size() - 1, lower.toInt('z'));

        Alphabet nums = getNewAlphabet("1234567809");
        assertEquals(10, nums.size());
        assertTrue(nums.contains('4'));
        assertTrue(nums.contains('7'));
        assertTrue(nums.contains('8'));
        assertEquals('1', nums.toChar(0));
        assertEquals('6', nums.toChar(5));
        assertEquals('9', nums.toChar(nums.size()-1));
        assertEquals(0, nums.toInt('1'));
        assertEquals(5, nums.toInt('6'));
        assertEquals(nums.size() - 1 , nums.toInt('9'));

        Alphabet alphanum = getNewAlphabet("a45nl!&Zx4f+");
        assertEquals(12, alphanum.size());
        assertTrue(alphanum.contains('a'));
        assertTrue(alphanum.contains('&'));
        assertTrue(alphanum.contains('!'));
        assertFalse(alphanum.contains('^'));
        assertFalse(alphanum.contains(' '));
        assertEquals('a', alphanum.toChar(0));
        assertEquals('Z', alphanum.toChar(7));
            assertEquals('+',
                    alphanum.toChar(alphanum.size() - 1));
        assertEquals(0, alphanum.toInt('a'));
        assertEquals(7, alphanum.toInt('Z'));
        assertEquals(alphanum.size() - 1, alphanum.toInt('+'));


    }
    @Test
    public void checkIdTransform() {
        perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
        assertFalse(perm.derangement());
    }
    @Test
    public void testPermutations() {
        Alphabet alph = getNewAlphabet("ABCD");
        Permutation p = getNewPermutation("(BACD)", alph);
        checkPerm("p1", "ABCD", "CADB", p, alph);
        assertTrue(p.derangement());
        p = getNewPermutation("(A) (B) (CD)", alph);
        checkPerm("p2", "ABCD", "ABDC", p, alph);
        assertFalse(p.derangement());
        alph = getNewAlphabet("AXRDET");
        p = getNewPermutation("(ADT) (X) (RE)", alph);
        checkPerm("p3", "ADTXRE", "DTAXER", p, alph);
        assertFalse(p.derangement());
        alph = getNewAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Permutation Rotor1 = getNewPermutation(
                "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZS)",
                alph);
        checkPerm("r1", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "EKMFLGDQVZNTOWYHXUJPAIBRCS", Rotor1,
                alph);
        Permutation Rotor2 = getNewPermutation(
                "  (FIXVYOMW) (CDKLHUP)   (ESZ) (BJ) (GR) (NT) (A) ", alpha);
        checkPerm("r2", "FIXVYOMWCDKLHUPESZBJGRNTAQ",
                "IXVYOMWFDKLHUPCSZEJBRGTNAQ", Rotor2,
                alpha);
    }

}
    /* ***** TESTS ***** */



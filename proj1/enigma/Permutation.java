package enigma;

import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Aniruddh Khanwale
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        int i = 0;
        int charsAdded = 0;
        cycles.replaceAll("\\s", "");
        cycles.trim();
        while (i < cycles.length()) {
            if (cycles.charAt(i) == '(') {
                String cycle = "";
                i += 1;
                while (cycles.charAt(i) != ')') {
                    cycle += Character.toString(cycles.charAt(i));
                    i += 1;
                }
                addCycle(cycle);
                charsAdded += cycle.length();
            }
            i += 1;
        }
        if (charsAdded < this.alphabet().size()) {
            for (i = 0; i < this.alphabet().size(); i += 1) {
                boolean added = false;
                for (String cycle : this.cycles()) {
                    if (cycle.contains(
                            Character.toString(alphabet().toChar(i)))) {
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    _deranged = false;
                    addCycle(Character.toString(this.alphabet().toChar(i)));
                }
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        if (cycle.length() == 1) {
            this._deranged = false;
        }
        this.cycles().add(cycle);
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return this.alphabet().size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char toPermute = this.alphabet().toChar(wrap(p));
        String myCycle = charCycle(toPermute);
        int index = myCycle.indexOf(toPermute);
        char permuted;
        if (index == myCycle.length() - 1) {
            permuted = myCycle.charAt(0);
        } else {
            permuted = myCycle.charAt(index + 1);
        }
        return this.alphabet().toInt(permuted);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char toInvert = this.alphabet().toChar(wrap(c));
        String myCycle = charCycle(toInvert);
        int index = myCycle.indexOf(toInvert);
        char inverted;
        if (index == 0) {
            inverted = myCycle.charAt(myCycle.length() - 1);
        } else {
            inverted = myCycle.charAt(index - 1);
        }
        return this.alphabet().toInt(inverted);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        String myCycle = charCycle(p);
        int index = myCycle.indexOf(p);
        char permuted;
        if (index == myCycle.length() - 1) {
            permuted = myCycle.charAt(0);
        } else {
            permuted = myCycle.charAt(index + 1);
        }
        return permuted;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        String myCycle = charCycle(c);
        int index = myCycle.indexOf(c);
        char inverted;
        if (index == 0) {
            inverted = myCycle.charAt(myCycle.length() - 1);
        } else {
            inverted = myCycle.charAt(index - 1);
        }
        return inverted;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return the cycles of this permutation. */
    ArrayList<String> cycles() {
        return _cycles;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        return this._deranged;
    }

    /** Find the cycle in which this character is found.
     * @param c Character to find cycle for
     * @return myCycle the Cycle in which this char is found **/
    private String charCycle(char c) {
        String myCycle = Character.toString(c);
        for (String cycle : this.cycles()) {
            if (cycle.contains(Character.toString(c))) {
                myCycle = cycle;
            }
        }
        return myCycle;
    }
    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycles of this permutation. */
    private ArrayList<String> _cycles = new ArrayList<String>();

    /** Stores whether the permutation is a derangement. */
    private boolean _deranged = true;
}

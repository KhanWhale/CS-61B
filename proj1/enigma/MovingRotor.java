package enigma;

import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Aniruddh Khanwale
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        for (int i = 0; i < notches.length(); i += 1) {
            if (this.permutation().alphabet().contains(notches.charAt(i))) {
                this.notches().add(notches.charAt(i));
            }
        }
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        return notches().contains(this.alphabet().toChar(this.setting()));
    }

    @Override
    void advance() {
        if (this.setting() == this.alphabet().size() - 1) {
            this.set(0);
        } else {
            this.set(this.setting() + 1);
        }
    }
    @Override
    public String toString() {
        return "Moving Rotor " + name();
    }
    /** Return the notches of this Rotor. **/
    ArrayList<Character> notches() {
        return _notches;
    }
    /** Stores the notches of this rotor. **/
    private ArrayList<Character> _notches = new ArrayList<Character>();

}

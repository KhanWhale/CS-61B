package enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        for (Rotor rotor : allRotors) {
            _rotorOptions.put(rotor.name(), rotor);
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return this._numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return this._pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        for (int i = 0; i < rotors.length; i += 1) {
            _myRotors.add(_rotorOptions.get(rotors[i]));
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        for (int i = 1; i < _myRotors.size(); i += 1) {
            _myRotors.get(i).set(setting.charAt(i-1));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        this._plugboard = plugboard;
    }

    void advance() {
        boolean[] advanced = new boolean[_myRotors.size()];
        this._myRotors.get(_myRotors.size() - 1).advance();
        for (int i = _myRotors.size() - 2;
             i >= _myRotors.size() - numPawls(); i -= 1) {
            if (_myRotors.get(i + 1).atNotch()) {
                if (!advanced[i]) {
                    _myRotors.get(i).advance();
                }
                if (!advanced[i + 1]) {
                    _myRotors.get(i + 1).advance();
                }
            }
        }
    }
    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        this.advance();
        int reflectedIndex = this._plugboard.permute(c);
        char currChar= 'y';
        for (int i = _myRotors.size() - 1; i >= 0; i -= 1) {
            reflectedIndex = _myRotors.get(i).convertForward(reflectedIndex);
            currChar = _alphabet.toChar(reflectedIndex);
        }
        for (int i = 1; i < _myRotors.size(); i += 1) {
            reflectedIndex = _myRotors.get(i).convertBackward(reflectedIndex);
            currChar = _alphabet.toChar(reflectedIndex);
        }
        return this._plugboard.permute(reflectedIndex);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String converted = "";
        for(int i = 0; i < msg.length(); i += 1) {
            int index = this._alphabet.toInt(msg.charAt(i));
            int convertedIndex = convert(index);
            converted += Character.toString(
                    this._alphabet.toChar(convertedIndex));
        }
        return converted;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotors. */
    private int _numRotors;

    /** Number of pawls or moving rotors. */
    private int _pawls;

    /** The collection of my rotors. */
    public ArrayList<Rotor> _myRotors = new ArrayList<Rotor>();

    /** The collection of all rotors this machine could be configured with. */
    private HashMap<String, Rotor> _rotorOptions = new HashMap<String, Rotor>();

    /** The plugboard permutation. */
    private Permutation _plugboard;
}

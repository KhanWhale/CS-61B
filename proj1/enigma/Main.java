package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Aniruddh Khanwale
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);
        _storeArgs = args;
        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);

        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        if(_input.hasNextLine()) {
            currLine = _input.nextLine();
            if (currLine.charAt(0) == '*') {
                myLineScanner = new Scanner(currLine);
                myLineScanner.next();
                _myMachine = readConfig();
                setUp(_myMachine, myLineScanner.next());
            } else {
                throw new EnigmaException("No Configuration Specified");
            }
        }
        while (_input.hasNextLine()) {
           currLine = _input.nextLine();
           if (currLine.isEmpty()) {
               _output.println();
           } else if (currLine.charAt(0) == '*') {
                myLineScanner = new Scanner(currLine);
                myLineScanner.next();
                _myMachine = readConfig();
                setUp(_myMachine, myLineScanner.next());
            } else {
                printMessageLine(currLine);
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() throws EnigmaException {
        try {
            _config = getInput(_storeArgs[0]);
            _alphabet = new Alphabet(_config.nextLine());
            int numRotors = _config.nextInt();
            int pawls = _config.nextInt();
            List<String> rotorNames = new ArrayList<String>();
            while (_config.hasNextLine()) {
                wasNew = true;
                Rotor myRotor = readRotor();
                if (wasNew) {
                    allRotors.add(myRotor);
                    rotorNames.add(myRotor.name());
                }
            }
            Set<String> set = new HashSet<String>(rotorNames);
            if (set.size() < rotorNames.size()) {
                throw new EnigmaException("Duplicate Rotor");
            }
            for (int i  = 0; i < allRotors.size(); i += 1) {
                allRotors.get(i).setPermutation(
                        new Permutation(allCycles.get(i), _alphabet));
            }
            Machine myMachine = new Machine(_alphabet, numRotors, pawls,
                    allRotors);
            String[] rotors = new String[numRotors];
            for (int i = 0; i < numRotors; i += 1) {
                rotors[i] = myLineScanner.next();
            }
            for (int i = 0; i < rotors.length; i += 1) {
                if (!rotorNames.contains(rotors[i])) {
                    throw new EnigmaException("Bad Rotor Name");
                }
            }
            myMachine.insertRotors(rotors);
            return myMachine;
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name = _config.next();
            String typeNotch = _config.next();
            String type = Character.toString(typeNotch.charAt(0));
            String cycles = _config.nextLine();
            if (name.contains("(")) {
                wasNew = false;
                allCycles.set(
                        allCycles.size() - 1,
                        allCycles.get(allCycles.size() - 1) + name);
                allCycles.set(
                        allCycles.size() - 1,
                        allCycles.get(allCycles.size() - 1) + typeNotch);
                allCycles.set(
                        allCycles.size() - 1,
                        allCycles.get(allCycles.size() - 1) + cycles);
            } else {
                allCycles.add(cycles);
            }
            Permutation perm = new Permutation(cycles, _alphabet);
            if (type.equals("M")) {
                return new MovingRotor(name, perm, typeNotch.substring(1));
            } else if (type.equals("N")) {
                return new FixedRotor(name, perm);
            } else {
                return new Reflector(name, perm);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) throws EnigmaException {
        if (settings.length() < _myMachine.numRotors() - 1) {
            throw new EnigmaException("Wheel settings too short");
        } else if (settings.length() > _myMachine.numRotors() - 1) {
            throw new EnigmaException("Wheel settings too long");
        }
        for (int i = 0; i < settings.length(); i += 1) {
            if (!_alphabet.contains(settings.charAt(i))) {
                throw new EnigmaException("Bad character in wheel settings");
            }
        }
        M.setRotors(settings);
        String cycles = "";
        if (myLineScanner.hasNextLine()) {
            cycles = myLineScanner.nextLine();
        }
        Permutation perm = new Permutation(cycles, _alphabet);
        M.setPlugboard(perm);
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        String message = "";
        msg = msg.replaceAll("\\s+", "");
        for (int i = 0; i < msg.length(); i += 1) {
            int charIndex = _alphabet.toInt(msg.charAt(i));
            int convertedInt = _myMachine.convert(charIndex);
            char convertedChar = _alphabet.toChar(convertedInt);
            message += Character.toString(convertedChar);
            if (i > 3 && (i + 1) % 5 == 0) {
                message += " ";
            }
        }
        _output.println(message);
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /**Settings line from input message. */
    private Machine _myMachine;

    /** Stores the original configuration input. */
    private String[] _storeArgs;

    /** Store all the rotors. */
    private ArrayList<Rotor> allRotors = new ArrayList<Rotor>();

    /** Store all the cyles. */
    private ArrayList<String> allCycles = new ArrayList<String>();

    /** Determine whether or not a rotor is new. */
    private boolean wasNew = true;

    private String currLine;

    private Scanner myLineScanner;
}

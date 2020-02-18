import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Aniruddh Khanwale
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    private Reader src;
    private String input;
    private String output;
    public TrReader(Reader str, String from, String to) {
        src = str;
        input = from;
        output = to;
    }

    /* TODO: IMPLEMENT ANY MISSING ABSTRACT METHODS HERE
     * NOTE: Until you fill in the necessary methods, the compiler will
     *       reject this file, saying that you must declare TrReader
     *       abstract. Don't do that; define the right methods instead!
     */
    public int read (char[] cbuf, int off, int len) throws IOException {
        int read = src.read(cbuf, off, len);
        for (int i = off; i <= off + len && i < cbuf.length; i += 1) {
            for (int j = 0; j < input.length(); j += 1) {
                if (cbuf[i] == input.charAt(j)) {
                    cbuf[i] = output.charAt(j);
                    break;
                }
            }
        }
        return read;
    }

    public void close() throws IOException {
        src.close();
    }
}

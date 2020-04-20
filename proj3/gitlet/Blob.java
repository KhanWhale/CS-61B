package gitlet;

import java.io.File;
import java.io.Serializable;

/** The class designed to track blobs, or files.
 * @author Aniruddh Khanwale */
public class Blob implements Serializable{
    Blob (File toBlobify) {
        if (!toBlobify.isFile()) {
            throw new GitletException("File does not exist.");
        } else {
            name = toBlobify.getName();
            hash = Utils.sha1(Utils.readContentsAsString(toBlobify));
        }
    }

    String getHash() {
        return hash;
    }

    String getName() {
        return name;
    }
    /** SHA-1 hashcode of the blob object. */
    String hash;

    /** The filename of this blob object. */
    String name;
}

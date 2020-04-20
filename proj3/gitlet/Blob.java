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
            blobFile = toBlobify;
            hash = Utils.sha1(Utils.readContentsAsString(toBlobify));
        }
    }


    String getHash() { return hash;
    }

    String getName() {
        return name;
    }
    /** SHA-1 hashcode of the blob object. */
    private String hash;

    /** The filename of this blob object. */
    private String name;

    /** The file this blob stores data for. */
    private File blobFile;
}

package gitlet;

import java.io.File;
import java.io.Serializable;

/** The class designed to track blobs, or files.
 * @author Aniruddh Khanwale */
public class Blob implements Serializable{
    Blob (File toBlobify, File blobDir) {
        _blobDir = blobDir;
        if (!toBlobify.isFile()) {
            throw new GitletException("File does not exist.");
        } else {
            name = toBlobify.getName();
            File blobFile = toBlobify;
            blobString = Utils.readContentsAsString(toBlobify);
            hash = Utils.sha1(Utils.serialize(this));
        }
        if (!Utils.join(_blobDir, hash).exists()) {
            persist();
        }
    }

    void persist() {
        Utils.writeObject(Utils.join(_blobDir, hash), this);
    }
    String getHash() { return hash;
    }

    String getName() {
        return name;
    }
    /** SHA-1 hashcode of the blob object. */

    String getBlobString() {
        return blobString;
    }
    private String hash;

    /** The filename of this blob object. */
    private String name;


    /** The string within the blobFile */
    private String blobString;

    /** The filepath this blob will be stored in. */
    private File _blobDir;
}

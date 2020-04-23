package gitlet;

import java.io.File;
import java.io.Serializable;

/** The class designed to track blobs, or files.
 * @author Aniruddh Khanwale */
public class Blob implements Serializable {
    /** Constructs a new blob object, given the file to convert to a blob,
     * and the directory in which to store it.
     * @param toBlobify the file to write to a blob.
     * @param blobDir the directory in which to persist the blob.
     */
    Blob(File toBlobify, File blobDir) {
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

    /** Implements persistence for the blob, writing it to a file
     * named by the blob hash.
     */
    void persist() {
        Utils.writeObject(Utils.join(_blobDir, hash), this);
    }
    /** Returns the blobs hash code.
     * @return the hash of the blob*/
    String getHash() {
        return hash;
    }

    /** Returns the file name to which this blob refers.
     * @return  the name of the file*/
    String getName() {
        return name;
    }

    /** SHA-1 hashcode of the blob object.
     * @return the String contents of the file*/
    String getBlobString() {
        return blobString;
    }

    /** The hash of this blob. */
    private String hash;

    /** The filename of this blob object. */
    private String name;


    /** The string within the blobFile. */
    private String blobString;

    /** The filepath this blob will be stored in. */
    private File _blobDir;
}

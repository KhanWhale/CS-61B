package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Commit class stores instances of commits.
 * @author Aniruddh Khanwale
 */
public class Commit implements Serializable {
    /** The epoch time in Milliseconds at which this commit was made. **/
    private long commitTime;

    /** The commit message. **/
    private String commitMessage;

    /** The SHA-1 hash of this commit used for identification purposes.*/
    private String hash;

    /** Default commit constructor. Takes a commit message and a commit time.
     * @param msg The commit message.
     * @param unixTime The commit time, in Unix epoch seconds. */
    public Commit(String msg, long unixTime) {
        commitMessage = msg;
        commitTime = unixTime;
        hash = Utils.sha1(commitMessage, Long.toString(commitTime));
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf =
                new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z ");
        return sdf.format(new Date(commitTime));
    }

    /** Return the commit time of this commit. */
    public long getCommitTime() {
        return commitTime;
    }

    /** Return the commit message. */
    public String getCommitMessage() {
        return commitMessage;
    }

    /** Return the hash identifier of the commit. */
    public String getHash() {
        return hash;
    }

}

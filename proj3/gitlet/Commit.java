package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Commit class stores instances of commits.
 * @author Aniruddh Khanwale
 */
public abstract class Commit implements Serializable {
    /** The epoch time in Milliseconds at which this commit was made. **/
    private long commitTime;

    /** The commit message. **/
    private String commitMessage;

    /** The SHA-1 hash of this commit used for identification purposes.*/
    private String hash;

    public Commit(){

    }


    /** Return the commit time of this commit. */
    long getCommitTime() {
        return commitTime;
    }

    /** Return the commit message. */
    String getCommitMessage() {
        return commitMessage;
    }

    /** Set the commit time of this commit. */
    void setCommitTime(long time) {
        commitTime = time;
    }

    /** Set the commit message. */
    void setCommitMessage(String msg) {
        commitMessage = msg;
    }

    /** Return the hash identifier of the commit. */
    String getHash() {
        return hash;
    }

    /** Set the default hash value of this commit. */
    void setHash() {
        hash = Utils.sha1(commitMessage, Long.toString(commitTime));
    }

}

package gitlet;

import jdk.jshell.execution.Util;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Commit class stores instances of commits.
 * @author Aniruddh Khanwale
 */
public class Commit implements Serializable, Dumpable {

    /** The epoch time in Milliseconds at which this commit was made. **/
    private long commitTime;

    /** The commit message. **/
    private String commitMessage;

    /** The SHA-1 hash of this commit used for identification purposes.*/
    private String hash;

    /** Stores the staging area associated with this commit. */
    private StagingArea myStage;

    /** The parent commit. */
    private String parentUID = null;

    /** The branch this commit was made on. */
    private String branch = "master";

    /** The directory in which to persist the commit object. */
    private File commitDir;

    /** Default constructor, allowing for extension. */
    public Commit() {

    }

    /** Constructs a new Commit with a given message and time.
     * @param msg The commit message
     * @param unixTime The unix time at which this commit was made*/
    public Commit(String msg, long unixTime) {
        setCommitMessage(msg);
        setCommitTime(unixTime);
    }

    /** Performs the commit operation.
     *
     * @param stage The current staging area
     */
    void commit(StagingArea stage, String _branch) {
        branch = _branch;
        myStage = stage;
        commitDir = Utils.join(myStage.getGitletDir(), "commits");
        myStage.getStagePath().delete();
        parentUID = Utils.readContentsAsString(myStage.getHeadPath());
        setHash();
        Utils.writeContents(myStage.getHeadPath(), hash);
    }

    /** Return the commit time of this commit. */
    long getCommitTime() {
        return commitTime;
    }

    /** Return the commit message. */
    String getCommitMessage() {
        return commitMessage;
    }

    /** Set the commit time of this commit.
     * @param time the time to set this commit to */
    void setCommitTime(long time) {
        commitTime = time;
    }

    /** Set the commit message.
     * @param msg the message for this commit */
    void setCommitMessage(String msg) {
        commitMessage = msg;
    }

    /** Return the hash identifier of the commit. */
    String getHash() {
        return hash;
    }

    /** Set the default hash value of this commit. */
    void setHash() {
        hash = Utils.sha1(Utils.serialize(this));
    }
    /** Return the stage of the commit. */
    StagingArea getStage() {
        return myStage;
    }

    String getBranch() {
        return branch;
    }
    /** Formats the string into the proper format for the commit.
     * @return The formatted time string */
    public String timeToString() {
        SimpleDateFormat sdf =
                new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        return sdf.format(new Date(getCommitTime()));
    }

    @Override
    public void dump() {
        System.out.println(commitMessage + "at " + timeToString());
        System.out.println("HEAD was at" + parentUID);
        System.out.println("NEW HEAD is" + hash + ", "
                + Utils.readContentsAsString(myStage.getHeadPath()));
        System.out.println("+++++");
    }

    /** Writes the commit to a file in the commit directory.
     * @param _commitDir The directory to which to write the commit. */
    void persist(File _commitDir) {
        Utils.writeObject(Utils.join(_commitDir, hash), this);
    }

    /** Prints out the log of this commit.
     * @return The parent ID of this commit, to be used for looping. */
    String log() {
        System.out.println("===");
        System.out.println("commit " + hash);
        System.out.println("Date: " + timeToString());
        System.out.println(commitMessage);
        System.out.println();
        Commit parentCommit = Utils.readObject(Utils.join(commitDir, parentUID), Commit.class);
        if (!parentCommit.getBranch().equals(branch)) {
            return null;
        } else {
            return parentUID;
        }
    }
}

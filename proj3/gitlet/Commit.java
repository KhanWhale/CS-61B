package gitlet;

import java.io.File;
import java.io.Serializable;
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

    /** The parent commit */
    private String parentUID = null;

    public Commit() {

    }

    public Commit(String msg, long unixTime) {
        setCommitMessage(msg);
        setCommitTime(unixTime);
    }

    void commit(StagingArea stage) {
            myStage = stage;
            myStage.stagePath.delete();
            parentUID = Utils.readContentsAsString(myStage.headPath);
            setHash();
            Utils.writeContents(myStage.headPath, hash);
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
        if (myStage == null) {
            hash = Utils.sha1(Utils.serialize(this));
        } else {
            List<Object> metadata = new ArrayList<Object>();
            for (String key: myStage.blobTreeMap.keySet()) {
                metadata.add(key);
            }
            metadata.add(commitMessage);
            metadata.add(Long.toString(commitTime));
            metadata.add(parentUID);
            hash = Utils.sha1(metadata);
        }
    }
    /** Return the stage of the commit. */
    StagingArea getStage() {
        return myStage;
    }


    void setStage(StagingArea stage) {
        myStage = stage;
    }

    public String timeToString() {
        SimpleDateFormat sdf =
                new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        return sdf.format(new Date(getCommitTime()));
    }

    @Override
    public void dump() {
        System.out.println(commitMessage + "at " + timeToString());
        System.out.println("HEAD was at" + parentUID);
        System.out.println("NEW HEAD is" + hash + ", " + Utils.readContentsAsString(myStage.headPath));
        System.out.println("+++++");
    }

    void persist(File commitDir) {
        Utils.writeObject(Utils.join(commitDir, hash), this);
    }

    String log() {
        System.out.println("===");
        System.out.println("commit " + hash);
        System.out.println("Date: " + timeToString());
        System.out.println(commitMessage);
        System.out.println();
        return parentUID;
    }
}

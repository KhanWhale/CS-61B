package gitlet;

import java.io.IOException;
import java.io.Serializable;
import java.io.File;

/** This class represents the Initial repository commit.
 * @author Aniruddh Khanwale */
public class InitialCommit extends Commit implements Serializable {

    /** Initial commit constructor. Takes a commit message and a commit time.
     * @param msg The commit message.
     * @param unixTime The commit time, in Unix epoch seconds. */
    public InitialCommit(String msg, long unixTime) {
        setCommitMessage(msg);
        setCommitTime(unixTime);
    }

    /** Performs a commit.
     *
     * @param head The path to head.
     */
    public void commit(File head) {
        try {
            head.createNewFile();
        } catch (IOException e) {
            return;
        }
        setHash();
        Utils.writeContents(head, getHash());
    }
    @Override
    String log() {
        System.out.println("===");
        System.out.println("commit " + getHash());
        System.out.println("Date: " + timeToString());
        System.out.println(getCommitMessage());
        return null;
    }
}

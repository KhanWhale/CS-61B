package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitialCommit extends Commit implements Serializable {
    /** Initial commit constructor. Takes a commit message and a commit time.
     * @param msg The commit message.
     * @param unixTime The commit time, in Unix epoch seconds. */
    public InitialCommit(String msg, long unixTime) {
        setCommitMessage(msg);
        setCommitTime(unixTime);
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

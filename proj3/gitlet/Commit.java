package gitlet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Commit {
    public Commit(String msg, long unixTime) {
        commitMessage = msg;
        commitTime = unixTime;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z ");
        return sdf.format(new Date(commitTime));
    }
    /** The epoch time in Milliseconds at which this commit was made. **/
    long commitTime;

    /** The commit message. **/
    String commitMessage;
}

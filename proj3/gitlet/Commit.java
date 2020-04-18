package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Commit implements Serializable{
    public Commit(String msg, long unixTime) {
        commitMessage = msg;
        commitTime = unixTime;
        hash = Utils.sha1(commitMessage, Long.toString(commitTime));
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z ");
        return sdf.format(new Date(commitTime));
    }
    /** The epoch time in Milliseconds at which this commit was made. **/
    long commitTime;

    /** The commit message. **/
    String commitMessage;

    String hash;
}

package gitlet;

public class MergeCommit extends Commit {


    /** Constructs a new Commit with a given message and time.
     * @param msg The commit message
     * @param unixTime The unix time at which this commit was made*/
    public MergeCommit(String msg, long unixTime, String mergedBranchHead) {
        super(msg, unixTime);
        mergeParentUID = mergedBranchHead;
    }

    @Override
    /** Prints out the log of this commit.
     * @return The parent ID of this commit, to be used for looping. */
    String log() {
        System.out.println("===");
        System.out.println("commit " + getHash());
        System.out.println("Merge: " + getParentUID().substring(0, 7) + " " + mergeParentUID.substring(0, 7));
        System.out.println("Date: " + timeToString());
        System.out.println(getCommitMessage());
        System.out.println();
        return getParentUID();
    }
    /** The merged-in parent */
    private String mergeParentUID;
}

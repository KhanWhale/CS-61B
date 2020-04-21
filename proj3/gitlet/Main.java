package gitlet;
import jdk.jshell.execution.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.io.File;
import java.util.TimeZone;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Aniruddh Khanwale
 */
public class Main {

    /** Current Working Directory. */
    static final File CWD = new File(".");

    /** File object of gitlet subdirectory. */
    static File gitletDir = Utils.join(CWD, ".gitlet");

    /** File object containing commits */
    static File commits = Utils.join(gitletDir, "commits");

    /** File object containing blobs */
    static File blobs = Utils.join(gitletDir, "blobs");

    /** File object containing logs */
    static File logs = Utils.join(gitletDir, "logs");

    /** File containing log for master branch. */
    static File masterLog = Utils.join(logs, "master");

    /** Time zone offset */
    static long tzOffset = Calendar.getInstance().getTimeZone().getOffset(0);
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        try {
            if (args.length == 0) {
                throw new GitletException("Please enter a command.");
            } else {
                switch (args[0]) {
                    case "init":
                        init(args);
                        break;
                    case "add":
                        add(args);
                        break;
                    case "commit":
                        commit(args);
                        break;
                    default:
                       throw new GitletException("No command with that name exists.");
                }
            }
        } catch (GitletException gitletErr) {
            System.err.print(gitletErr.getMessage());
            System.exit(0);
        }
    }
    /** Initializes a new gitlet repository. */
    public static void init(String[] args) throws GitletException{
        if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        }
        if (gitletDir.exists()) {
            throw new GitletException(
                    "A Gitlet version-control system already " +
                            "exists in the current directory.");
        } else {
            gitletDir.mkdir();
            commits.mkdir();
            logs.mkdir();
            blobs.mkdir();
            InitialCommit initialCommit =
                    new InitialCommit("initial commit", -tzOffset);
            initialCommit.setHash();
            File serializedCommit =
                    Utils.join(commits, initialCommit.getHash());
            File head = Utils.join(gitletDir, "HEAD");
            try {
                masterLog.createNewFile();
                serializedCommit.createNewFile();
                head.createNewFile();
                FileWriter myWriter = new FileWriter(head);
                myWriter.write(initialCommit.getHash());
                myWriter.close();
            } catch (IOException e) {
                return;
            }
            Utils.writeObject(serializedCommit, initialCommit);
            Utils.writeObject(masterLog, initialCommit);
            }
    }
    public static void add(String[] args) throws GitletException{
        if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else {
            File toAdd = Utils.join(CWD, args[1]);
            if (!toAdd.isFile()) {
                throw new GitletException("File does not exist.");
            } else {
                StagingArea myStage = new StagingArea(gitletDir);
                Blob toStage = new Blob(toAdd, blobs);
                myStage.stageFile(toStage);
                if (myStage.stagePath.isFile()) {
                    Utils.writeObject(myStage.stagePath, myStage);
                }
            }
        }
    }
    public static void commit(String[] args) throws GitletException {
        StagingArea currentStage = new StagingArea(gitletDir);
        if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (currentStage.size() == 0) {
            throw new GitletException("No changes added to the commit.");
        } else if (args[1].length() == 0) {
            throw new GitletException("Please enter a commit message.");
        } else {
            Commit myCommit = new Commit(args[1], System.currentTimeMillis() - tzOffset);
            myCommit.commit(currentStage);
            myCommit.persist(commits);
        }
    }
}

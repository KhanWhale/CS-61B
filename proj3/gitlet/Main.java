package gitlet;
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

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            return;
        }
        switch (args[0]) {
        case "init" :
            init();
            break;
        case "add":
            break;
        default:
            System.exit(0);
            break;
        }
    }

    /** Initializes a new gitlet repository. */
    public static void init() {
        File gitletDir = Utils.join(CWD, ".gitlet");
        try {
            if (gitletDir.exists()) {
                throw new GitletException(
                        "A Gitlet version-control system already " +
                                "exists in the current directory.");
            } else {
                gitletDir.mkdir();
                File objects = Utils.join(gitletDir, "objects");
                objects.mkdir();
                File logs = Utils.join(gitletDir, "logs");
                logs.mkdir();
                File masterLog = Utils.join(logs, "master");
                TimeZone tz = Calendar.getInstance().getTimeZone();
                long tzOffset = tz.getOffset(0);
                Commit initialCommit =
                        new Commit("initial commit", -tzOffset);
                File serializedCommit =
                        Utils.join(objects, initialCommit.getHash());
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
        } catch (GitletException gitErr) {
            System.err.print(gitErr.getMessage());
            System.exit(0);
        }
    }
}

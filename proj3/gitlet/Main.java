package gitlet;

import jdk.jshell.execution.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.TreeMap;

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

    /** File object containing head reference */
    static File head = Utils.join(gitletDir, "HEAD");


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
                    case "log":
                        log(args);
                        break;
                    case "status":
                        status(args);
                        break;
                    case "rm":
                        rm(args);
                        break;
                    case "checkout":
                        checkout(args);
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
        if (gitletDir.exists()) {
            throw new GitletException(
                    "A Gitlet version-control system already " +
                            "exists in the current directory.");
        } else if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        } else {
            gitletDir.mkdir();
            commits.mkdir();
            blobs.mkdir();
            InitialCommit initialCommit =
                    new InitialCommit("initial commit", 0);
            initialCommit.setHash();
            File serializedCommit =
                    Utils.join(commits, initialCommit.getHash());
            try {
                serializedCommit.createNewFile();
                head.createNewFile();
                FileWriter myWriter = new FileWriter(head);
                myWriter.write(initialCommit.getHash());
                myWriter.close();
            } catch (IOException e) {
                return;
            }
            Utils.writeObject(serializedCommit, initialCommit);
        }
    }
    public static void add(String[] args) throws GitletException{
        if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
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
        if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else if (currentStage.size() == 0 && currentStage.removedFiles.size() == 0) {
            throw new GitletException("No changes added to the commit.");
        } else if (args[1].length() == 0) {
            throw new GitletException("Please enter a commit message.");
        } else {
            Commit myCommit = new Commit(args[1], System.currentTimeMillis());
            myCommit.commit(currentStage);
            myCommit.persist(commits);
            currentStage.stagePath.delete();
        }
    }
    public static void rm(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else {
            StagingArea currStage = new StagingArea(gitletDir);
            File toRemove = Utils.join(CWD, args[1]);
            currStage.rmFile(toRemove);
            if (currStage.stagePath.isFile()) {
                Utils.writeObject(currStage.stagePath, currStage);
            }
        }
    }
    public static void log(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        } else {
            String currCommitID = Utils.readContentsAsString(head);
            while (currCommitID != null) {
                Commit nextCommit = Utils.readObject(Utils.join(commits, currCommitID), Commit.class);
                currCommitID = nextCommit.log();
            }
        }
    }
    public static void status(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        } else {
            System.out.println("=== Branches ===");
            System.out.println("*master");
            System.out.println();
            System.out.println("=== Staged Files ===");
            StagingArea currStage = new StagingArea(gitletDir);
            for (String name : currStage.blobNames.keySet()) {
                System.out.println(name);
            }
            System.out.println();
            System.out.println("=== Removed Files ===");
            for (String name: currStage.removedFiles) {
                System.out.println(name);
            }
            System.out.println();
            System.out.println("=== Modifications Not Staged For Commit ===");
//            TreeMap<String, String> modded = currStage.checkModifications(CWD);
//            for (String file : modded.keySet()) {
//                System.out.println(file + modded.get(file));
//            }
            System.out.println();
            System.out.println("=== Untracked Files ===");
            System.out.println();
        }
    }
    public static void checkout(String[] args){
        if (!gitletDir.exists()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        } else if (args[1].equals("--")) {
            String headCommitID = Utils.readContentsAsString(head);
            Commit headCommit = Utils.readObject(Utils.join(commits, headCommitID), Commit.class);
            File checkoutFile = Utils.join(CWD, args[2]);
            if (headCommit.getStage().blobNames.containsKey(checkoutFile.getName())) {
                Blob checkoutBlob = headCommit.getStage().blobNames.get(checkoutFile.getName());
                String newData = checkoutBlob.getBlobString();
                try {
                    if (!checkoutFile.exists()) {
                        checkoutFile.createNewFile();
                    }
                    FileWriter myWriter = new FileWriter(checkoutFile);
                    myWriter.write(newData);
                    myWriter.close();
                } catch (IOException e) {
                    return;
                }
            } else {
                throw new GitletException("File does not exist in that commit.");
            }
        } else if (args[2].equals("--")) {
            File readCommit = Utils.join(commits, args[1]);
            if (!readCommit.exists()) {
                throw new GitletException("No commit with that id exists.");
            }
            Commit chCommit = Utils.readObject(readCommit, Commit.class);
            File checkoutFile = Utils.join(CWD, args[3]);
            if (chCommit.getStage().blobNames.containsKey(checkoutFile.getName())) {
                Blob checkoutBlob = chCommit.getStage().blobNames.get(checkoutFile.getName());
                String newData = checkoutBlob.getBlobString();
                try {
                    if (!checkoutFile.exists()) {
                        checkoutFile.createNewFile();
                    }
                    FileWriter myWriter = new FileWriter(checkoutFile);
                    myWriter.write(newData);
                    myWriter.close();
                } catch (IOException e) {
                    return;
                }
            }
        } else if (args.length == 2) {
            return;
        } else {
            throw new GitletException("Incorrect operands.");
        }
    }
}

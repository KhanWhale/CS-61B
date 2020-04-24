package gitlet;

import jdk.jshell.execution.Util;
import net.sf.saxon.lib.SchemaURIResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Aniruddh Khanwale
 */
public class Main {

    /** Current Working Directory. */
    static final File CWD = new File(".");

    /** File object of gitlet subdirectory. */
    private static File gitletDir = Utils.join(CWD, ".gitlet");

    /** File object containing commits. */
    private static File commits = Utils.join(gitletDir, "commits");

    /** File object containing blobs. */
    private static File blobs = Utils.join(gitletDir, "blobs");

    /** File object containing head reference. */
    private static File head = Utils.join(gitletDir, "HEAD");

    /** File object containing ref to current branch. */
    private static File workingBranch = Utils.join(gitletDir,
            "current-branch");
    /** File object containing branch data. */
    private static File branches = Utils.join(gitletDir, "branches");

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
                case "branch":
                    branch(args);
                    break;
                default:
                    throw new GitletException(
                            "No command with that name exists.");
                }
            }
        } catch (GitletException gitletErr) {
            System.err.print(gitletErr.getMessage());
            System.exit(0);
        }
    }

    /** Initializes a new gitlet repository.
     * @param args Not used. */
    public static void init(String[] args) throws GitletException {
        if (gitletDir.exists()) {
            throw new GitletException(
                    "A Gitlet version-control system already "
                            + "exists in the current directory.");
        } else if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        } else {
            gitletDir.mkdir();
            commits.mkdir();
            blobs.mkdir();
            branches.mkdir();
            InitialCommit initialCommit =
                    new InitialCommit("initial commit", 0);
            initialCommit.commit(head);
            initialCommit.persist(commits);
            File masterHead = Utils.join(branches, "master");
            try {
                workingBranch.createNewFile();
                masterHead.createNewFile();
                FileWriter myWriter = new FileWriter(workingBranch);
                myWriter.write("master");
                myWriter.close();
                myWriter = new FileWriter(masterHead);
                myWriter.write(initialCommit.getHash());
                myWriter.close();
            } catch (IOException e) {
                return;
            }
        }
    }

    /** Adds the specified file to the repository.
     *
     * @param args The file to add.
     * @throws GitletException
     */
    public static void add(String[] args) throws GitletException {
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
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
                if (myStage.getStagePath().isFile()) {
                    Utils.writeObject(myStage.getStagePath(), myStage);
                }
            }
        }
    }

    /** Commits the current state of the repository.
     *
     * @param args The commit message
     * @throws GitletException
     */
    public static void commit(String[] args) throws GitletException {
        StagingArea currentStage = new StagingArea(gitletDir);
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
        } else if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else if (currentStage.size() == 0
                && currentStage.getRemovedFiles().size() == 0) {
            throw new GitletException("No changes added to the commit.");
        } else if (args[1].length() == 0) {
            throw new GitletException("Please enter a commit message.");
        } else {
            String currentBranch = Utils.readContentsAsString(workingBranch);
            Commit myCommit = new Commit(args[1], System.currentTimeMillis());
            myCommit.commit(currentStage, currentBranch);
            myCommit.persist(commits);
            File currBranch = Utils.join(branches, currentBranch);
            Utils.writeContents(currBranch, myCommit.getHash());
            currentStage.getStagePath().delete();
        }
    }

    /** Removes the specified file from the gitlet repo.
     *
     * @param args The file to remove.
     */
    public static void rm(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
        } else if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else {
            StagingArea currStage = new StagingArea(gitletDir);
            File toRemove = Utils.join(CWD, args[1]);
            currStage.rmFile(toRemove);
            if (currStage.getStagePath().isFile()) {
                Utils.writeObject(currStage.getStagePath(), currStage);
            }
        }
    }

    /** Prints out a log of this branches commits. *
     *
     * @param args Not used
     */
    public static void log(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
        } else if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        } else {
            String currCommitID = Utils.readContentsAsString(head);
            while (currCommitID != null) {
                Commit prevCommit = Utils.readObject(
                        Utils.join(commits, currCommitID), Commit.class);
                currCommitID = prevCommit.log();
            }
        }
    }

    /** Prints the current status of the gitlet repo.
     *
     * @param args Not used
     */
    public static void status(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
        } else if (args.length != 1) {
            throw new GitletException("Incorrect operands.");
        } else {
            String currBranch = Utils.readContentsAsString(workingBranch);
            System.out.println("=== Branches ===");
            System.out.println("*" + currBranch);
            for (File branch : branches.listFiles()) {
                if (!branch.getName().equals(currBranch)) {
                    System.out.println(branch.getName());
                }
            }
            System.out.println();
            System.out.println("=== Staged Files ===");
            StagingArea currStage = new StagingArea(gitletDir);
            for (String name : currStage.getBlobNames().keySet()) {
                System.out.println(name);
            }
            System.out.println();
            System.out.println("=== Removed Files ===");
            for (String name: currStage.getRemovedFiles()) {
                System.out.println(name);
            }
            System.out.println();
            System.out.println("=== Modifications Not Staged For Commit ===");
            System.out.println();
            System.out.println("=== Untracked Files ===");
            System.out.println();
        }
    }

    /** Resets either a file or the repo to a specified commit/branch.
     *
     * @param args Specifies what to checkout
     */
    public static void checkout(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
        } else if (args[1].equals("--")) {
            String headCommitID = Utils.readContentsAsString(head);
            Commit headCommit = Utils.readObject(
                    Utils.join(commits, headCommitID), Commit.class);
            File checkoutFile = Utils.join(CWD, args[2]);
            if (headCommit.getStage().getBlobNames().containsKey(
                    checkoutFile.getName())) {
                Blob checkoutBlob = headCommit.getStage().getBlobNames().get(
                        checkoutFile.getName());
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
                throw new GitletException(
                        "File does not exist in that commit.");
            }
        }  else if (args.length == 2) {
            if (!Utils.join(branches, args[1]).exists()) {
                throw new GitletException("No such branch exists.");
            } else if (args[1].equals(Utils.readContentsAsString(workingBranch))) {
                throw new GitletException("No need to checkout the current branch.");
            }
            Commit repoHead = Utils.readObject(Utils.join(commits, Utils.readContentsAsString(head)), Commit.class);
            Commit branchHead = Utils.readObject(Utils.join(branches, args[1]), Commit.class);
            for (String fName : branchHead.getStage().getBlobNames().keySet()) {
                File toWrite = Utils.join(CWD, fName);
                if (toWrite.exists()) {
                    if (!repoHead.getStage().getBlobNames().keySet().contains(fName)) {
                        throw new GitletException("There is an untracked file in the way; delete it, or add and commit it first.");
                    }
                    Utils.writeContents(toWrite, branchHead.getStage().getBlobNames().get(fName).getBlobString());
                }
            }
            for (String fName : repoHead.getStage().getBlobNames().keySet()) {
                File toDelete = Utils.join(CWD, fName);
                if (!branchHead.getStage().getBlobNames().keySet().contains(fName)) {
                    Utils.restrictedDelete(toDelete);
                }
            }
            new StagingArea(gitletDir).getStagePath().delete();
            Utils.writeContents(workingBranch, args[1]);
            Utils.writeContents(head, Utils.readContentsAsString(Utils.join(branches, args[1])));
        } else if (args[2].equals("--")) {
            File readCommit = Utils.join(commits, args[1]);
            if (!readCommit.exists()) {
                throw new GitletException("No commit with that id exists.");
            }
            Commit chCommit = Utils.readObject(readCommit, Commit.class);
            File checkoutFile = Utils.join(CWD, args[3]);
            if (chCommit.getStage().getBlobNames().containsKey(
                    checkoutFile.getName())) {
                Blob checkoutBlob = chCommit.getStage().getBlobNames().get(
                        checkoutFile.getName());
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
        } else {
            throw new GitletException("Incorrect operands.");
        }
    }

    /** Creates a new branch in the gitlet repo.
     *
     * @param args The branch name.
     */
    public static void branch(String[] args) {
        if (!gitletDir.exists()) {
            throw new GitletException(
                    "Not in an initialized Gitlet directory.");
        } else if (args.length != 2) {
            throw new GitletException("Incorrect operands.");
        } else if (Utils.join(branches, args[1]).exists()) {
            throw new GitletException(
                    "A branch with that name already exists.");
        } else {
            File branchHead = Utils.join(branches, args[1]);
            try {
                branchHead.createNewFile();
                FileWriter setHead = new FileWriter(branchHead);
                setHead.write(Utils.readContentsAsString(head));
            } catch (IOException io) {
                return;
            }
        }
    }
}

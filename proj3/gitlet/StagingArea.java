package gitlet;

import com.sun.java.accessibility.util.GUIInitializedListener;
import jdk.jshell.execution.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/** Represents the Staging Area of the gitlet repository */
public class StagingArea implements Serializable, Dumpable {

    /** Path to HEAD file, in case a previous stage exist */
    File headPath;
    StagingArea(File gitDir) {
        gitletDir = gitDir;
        stagePath = Utils.join(gitDir, "stage");
        headPath = Utils.join(gitDir, "HEAD");
        try {
            if (headPath.isFile() && headPath.length() != 0) {
                copyHead();
            }
            if (stagePath.isFile() && stagePath.length() != 0) {
                copyStage();
            } else {
                stagePath.createNewFile();
            }
        } catch (IOException e) {
            return;
        }
    }

    public void rmFile(File rm) {
        if (blobNames.containsKey(rm.getName())) {
            removedFiles.add(rm.getName());
            String originalHash = blobNames.get(rm.getName()).getHash();
            blobNames.remove(rm.getName());
            blobTreeMap.remove(originalHash);
            if (headStage != null && headStage.blobNames.containsKey(rm.getName())) {
                Utils.restrictedDelete(rm);
            }


        } else {
            throw new GitletException("No reason to remove the file.");
        }
        return;
    }
    public void stageFile(Blob toStage) {
        if (headStage != null && headStage.blobTreeMap.containsKey(toStage.getHash())) {
            if (blobTreeMap.containsKey(toStage.getHash())) {
                blobTreeMap.remove(toStage.getHash());
                blobNames.remove(toStage.getName());
            }
        } else if (blobNames != null && blobNames.containsKey(toStage.getName())) {
            String originalHash = blobNames.get(toStage.getName()).getHash();
            blobNames.remove(toStage.getName());
            blobTreeMap.remove(originalHash);
            blobTreeMap.put(toStage.getHash(), toStage);
            blobNames.put(toStage.getName(), toStage);
        } else {
            blobTreeMap.put(toStage.getHash(), toStage);
            blobNames.put(toStage.getName(), toStage);
        }
    }

    private void copyHead() {
        String parentCommitID = Utils.readContentsAsString(headPath);
        Commit parentCommit = Utils.readObject(Utils.join(gitletDir, "commits", parentCommitID), Commit.class);
        headStage = parentCommit.getStage();
    }
    private void copyStage() {
        StagingArea parent = Utils.readObject(stagePath, StagingArea.class);
        blobTreeMap.putAll(parent.blobTreeMap);
        blobNames.putAll(parent.blobNames);
    }

    int size() {
        if (blobTreeMap.size() != blobNames.size()) {
            throw new GitletException("Incorrect blobTree/Name implementation");
        } else {
            return blobNames.size();
        }
    }
    /** File object containing staging area reference */
    File stagePath;

    /** File object containing gitlet directory reference. */
    File gitletDir;

    /** Treemap containing all blobs in the staging area,
     * used to ensure lgN search time.  */
    TreeMap<String, Blob> blobTreeMap = new TreeMap<String, Blob>();

    /** Treemap containing all the filenames in the staging area,
     * used to overwrite files */
     TreeMap<String, Blob> blobNames = new TreeMap<String, Blob>();

     ArrayList<String> removedFiles = new ArrayList<String>();

     /** Staging Area of previous Commit */
     StagingArea headStage;

    @Override
    public void dump() {
       if (blobNames == null && blobTreeMap == null) {
           System.out.println("No blobs added");
       } else {
           System.out.println("Blob Names :");
           System.out.println(blobNames.toString());
           System.out.println("Blob Tree Map: ");
           System.out.println(blobTreeMap.toString());
           System.out.println();
       }
    }
}

package gitlet;

import com.sun.java.accessibility.util.GUIInitializedListener;
import jdk.jshell.execution.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        boolean reasonToRemove = false;
        if (blobNames.containsKey(rm.getName())) {
            String originalHash = blobNames.get(rm.getName()).getHash();
            blobNames.remove(rm.getName());
            blobTreeMap.remove(originalHash);
            reasonToRemove = true;
        }
        if (headStage != null && headStage.blobNames.containsKey(rm.getName())) {
            removedFiles.add(rm.getName());
            Utils.restrictedDelete(rm);
            reasonToRemove = true;
        }
        if (!reasonToRemove) {
            throw new GitletException("No reason to remove the file.");
        }
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
        if (removedFiles.contains(toStage.getName())) {
            removedFiles.remove(toStage.getName());
        }
    }

    private void copyHead() {
        String parentCommitID = Utils.readContentsAsString(headPath);
        Commit parentCommit = Utils.readObject(Utils.join(gitletDir, "commits", parentCommitID), Commit.class);
        headStage = parentCommit.getStage();
        if (headStage != null && headStage.removedFiles.size() > 0) {
            for (String name : headStage.removedFiles) {
                if (size() > 0) {
                    Blob oFile = blobNames.get(name);
                    blobNames.remove(oFile.getName());
                    blobTreeMap.remove(oFile.getHash());
                }
            }
        }
    }
    private void copyStage() {
        StagingArea parent = Utils.readObject(stagePath, StagingArea.class);
        blobTreeMap.putAll(parent.blobTreeMap);
        blobNames.putAll(parent.blobNames);
        removedFiles.addAll(parent.removedFiles);
    }

    TreeMap<String, String> checkModifications(File parentDir) {
        TreeMap<String, String> modified = new TreeMap<>();
        File[] allFile = parentDir.listFiles();
        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(allFile));
        //FILE STILL EXISTS
        for (File toCheck : allFiles) {
            Blob toCompare = new Blob(toCheck, Utils.join(gitletDir, "blobs"));
            //      Tracked in current commit, modified in working dir, not staged
            if (headStage != null && headStage.blobNames.containsKey(toCheck.getName())) {
                String blobHash = toCompare.getHash();
                if (!headStage.blobTreeMap.containsKey(blobHash) && !blobTreeMap.containsKey(blobHash)) {
                    modified.put(toCheck.getName(), " (modified)");
                }
            }
//            Staged for addition but with different contents than in wd.
            if (blobNames.containsKey(toCheck.getName()) && !blobTreeMap.containsKey(toCompare.getHash())) {
                modified.put(toCheck.getName(), " (modified)");
            }
        }
//        Staged for addition, removed from pwd
        for (String fileName : blobNames.keySet()) {
            File toCheck = Utils.join(parentDir, fileName);
            if (!allFiles.contains(toCheck)) {
                modified.put(fileName, " (deleted)");
            }
        }
//        Tracked in curr commit, deleted from pwd, not staged for removal
        if (headStage != null) {
            for (String blobName : headStage.blobNames.keySet()) {
                File toCheck = Utils.join(parentDir, blobName);
                if (!allFiles.contains(toCheck) && !removedFiles.contains(blobName)) {
                    modified.put(blobName, " (deleted)");
                }
            }
        }
        return modified;
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

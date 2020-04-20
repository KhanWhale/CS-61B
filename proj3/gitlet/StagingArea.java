package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;

/** Represents the Staging Area of the gitlet repository */
public class StagingArea implements Serializable, Dumpable {
    StagingArea(File gitDir) {
        stagePath = Utils.join(gitDir, "stage");
        try {
            if (stagePath.isFile()) {
                copyStaging();
            } else {
                stagePath.createNewFile();
            }
        } catch (IOException e) {
            return;
        }
    }

    public void stageFile(Blob toStage) {
        if (blobTreeMap != null && blobTreeMap.containsKey(toStage.getHash())) {
            blobTreeMap.remove(toStage.getHash());
            blobNames.remove(toStage.getName());
            return;
        } else if (blobNames != null && blobNames.containsKey(toStage.getName())) {
            blobNames.remove(toStage.getName());
            blobTreeMap.remove(toStage.getHash());
            blobTreeMap.put(toStage.getHash(), toStage);
            blobNames.put(toStage.getName(), toStage);
        } else {
            blobTreeMap = new TreeMap<String, Blob>();
            blobNames = new TreeMap<String, Blob>();
            blobTreeMap.put(toStage.getHash(), toStage);
            blobNames.put(toStage.getName(), toStage);
        }
    }
    private void copyStaging() {
        StagingArea parent = Utils.readObject(stagePath, StagingArea.class);
        blobTreeMap = parent.blobTreeMap;
        blobNames = parent.blobNames;
    }


    /** File object containing staging area reference */
    File stagePath;

    /** Treemap containing all blobs in the staging area,
     * used to ensure lgN search time.  */
    TreeMap<String, Blob> blobTreeMap;

    /** Treemap containing all the filenames in the staging area,
     * used to overwrite files */
     TreeMap<String, Blob> blobNames;

    @Override
    public void dump() {
       if (blobNames == null && blobTreeMap == null) {
           System.out.println("No blobs added");
       } else {
           System.out.println("Blob Names :");
           System.out.println(blobNames.toString());
           System.out.println("Blob Tree Map: ");
           System.out.println(blobTreeMap.toString());
       }
    }
}

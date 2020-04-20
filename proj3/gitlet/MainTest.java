package gitlet;

import jdk.jshell.execution.Util;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    /** Reference to wugFile, used for testing */
    final File wugFile = createWug();

    @Test
    public void initMain() {
        File gitletDir = Utils.join(".", ".gitlet");
        assertFalse(gitletDir.exists());
        Main myMain = new Main();
        myMain.main("init");
        assertTrue(gitletDir.isDirectory());
    }

    @Test
    public void testAdd() {
        Main myMain = new Main();
        String[] args = new String[]{"add", wugFile.getName()};
//       Initial addition of wug file
        myMain.main(args);

        //Add wug file but identical to make sure it is removed
        myMain = new Main();
        myMain.main(args);

//        Add wug file again to make sure it gets added when not staged
        myMain = new Main();
        myMain.main(args);

//        Stage updated spelling of wug file
        updateWugSpelling();
        myMain = new Main();
        myMain.main(args);

//        Stage period form of wug file.
        updateWugPeriod();
        myMain = new Main();
        myMain.main(args);

//        Restage updated spelling form of wug file
        updateWugSpelling();
        myMain = new Main();
        myMain.main(args);

//        Restage period form of wug file.
        updateWugPeriod();
        myMain = new Main();
        myMain.main(args);

//        Restage original wug file. At this point, staging area should only be original file.

        myMain = new Main();
        myMain.main(args);
    }

    public File createWug(){
        File wug = Utils.join(".", "wug.txt");
        if (wug.isFile()) {
            Utils.writeObject(wug,"This is a wg");
        } else {
            try {
                wug.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return wug;
    }

    public void updateWugSpelling() {
        Utils.writeObject(wugFile, "This is a wug");
    }
    public void updateWugPeriod() {
        Utils.writeObject(wugFile, "This is a wug.");
    }
}

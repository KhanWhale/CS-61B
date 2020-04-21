package gitlet;

import jdk.jshell.execution.Util;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    /** Reference to wugFile, used for testing */
    final File wugFile = createWug();
    final File wegFile = createWeg();

    @Test
    public void initMain() {
        File gitletDir = Utils.join(".", ".gitlet");
        assertFalse(gitletDir.exists());
        Main myMain = new Main();
        myMain.main("init");
        assertTrue(gitletDir.isDirectory());
    }

    @Test
    public void testWugAdd() {
        Main myMain = new Main();
        String[] args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug file.");
        myMain.main(args);

        System.out.println("Add wug file but identical to make sure nothing happens.");
        myMain = new Main();
        myMain.main(args);

        System.out.println("Add wug file again to make sure it gets added when not staged");
        myMain = new Main();
        myMain.main(args);

        System.out.println("Stage updated spelling of wug file");
        updateWugSpelling();
        myMain = new Main();
        myMain.main(args);

        System.out.println("Stage period form of wug file.");
        updateWugPeriod();
        myMain = new Main();
        myMain.main(args);

        System.out.println("Restage updated spelling form of wug file");
        updateWugSpelling();
        myMain = new Main();
        myMain.main(args);

        System.out.println("Restage period form of wug file.");
        updateWugPeriod();
        myMain = new Main();
        myMain.main(args);

        System.out.println("Restage period wug file. At this point, it should only contain the period wug. ");
        myMain = new Main();
        myMain.main(args);
    }

    @Test
    public void testAddMultiple() {
        String[] args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug file.");
        createWug();
        Main myMain = new Main();
        myMain.main(args);

        System.out.println("Initial addition of weg file.");
        args[1] = wegFile.getName();
        myMain = new Main();
        myMain.main(args);

        System.out.println("Stage period form of wug file.");
        updateWugPeriod();
        myMain = new Main();
        args[1] = wugFile.getName();
        myMain.main(args);


    }
    @Test
    public void trivialCommitTest() {
        createWug();
        //Commit wg
        Main myMain = new Main();
        String[] args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wg file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"commit", "Commiting a wg!"};
        System.out.println("Commiting wg file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"add", wugFile.getName()};
        System.out.println("Ensure re-adding same file removes it from stage");
        myMain.main(args);

//        myMain = new Main();
//        args = new String[]{"commit", "This should fail"};
//        System.out.println("Ensure that commit aborts when there are no files.");
//        myMain.main(args);

        //Commit wug
        updateWugSpelling();
        args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"commit", "Commiting a wug!"};
        System.out.println("Commiting wg file");
        myMain.main(args);

        //Commit wug.
        updateWugPeriod();
        args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug. file");
        myMain.main(args);


        myMain = new Main();
        args = new String[]{"commit", "Commiting a wug.!"};
        System.out.println("Commiting wg file");
        myMain.main(args);
    }

    public File createWeg() {
        File weg = Utils.join(".", "weg.txt");
        if (weg.isFile()) {
            Utils.writeObject(weg,"This is a weg");
        } else {
            try {
                weg.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return weg;
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

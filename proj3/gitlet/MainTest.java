package gitlet;


import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MainTest {

    /** Reference to wugFile, used for testing */
    final File wugFile = createWug();
    final File wegFile = createWeg();

    @Test
    public void initMain() {
        File gitletDir = Utils.join(".", ".gitlet");
        Main myMain = new Main();
        myMain.main("init");

        myMain = new Main();
        System.out.println("Initial log");
        myMain.main("log");
    }

    @Test
    public void testWugAdd() {
        Main myMain = new Main();
        String[] args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug file.");
        myMain.main(args);

        System.out.println("Add wug file but identical "
                + "to make sure nothing happens.");
        myMain = new Main();
        myMain.main(args);

        System.out.println("Add wug file again to make sure"
                + " it gets added when not staged");
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

        System.out.println("Restage period wug file."
                 + " At this point, it should only contain the period wug. ");
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
        Main myMain = new Main();
        String[] args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wg file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"commit", "Commiting a wg!"};
        System.out.println("Commiting wg file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"log"};
        System.out.println("wg log");
        myMain.main(args);


        myMain = new Main();
        args = new String[]{"add", wugFile.getName()};
        System.out.println("Ensure re-adding same file removes it from stage");
        myMain.main(args);


        updateWugSpelling();
        args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"commit", "Commiting a wug!"};
        System.out.println("Commiting wg file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"log"};
        System.out.println("wug log");
        myMain.main(args);

        updateWugPeriod();
        args = new String[]{"add", wugFile.getName()};
        System.out.println("Initial addition of wug. file");
        myMain.main(args);


        myMain = new Main();
        args = new String[]{"commit", "Commiting a wug.!"};
        System.out.println("Commiting wg file");
        myMain.main(args);

        myMain = new Main();
        args = new String[]{"log"};
        System.out.println("wug. log");
        myMain.main(args);
    }

    public File createWeg() {
        File weg = Utils.join(".", "weg.txt");
        if (weg.isFile()) {
            Utils.writeObject(weg, "This is a weg");
        } else {
            try {
                weg.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return weg;
    }
    public File createWug() {
        File wug = Utils.join(".", "wug.txt");
        if (wug.isFile()) {
            Utils.writeObject(wug, "This is a wg");
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

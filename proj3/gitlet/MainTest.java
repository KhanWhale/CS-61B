package gitlet;

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
//        assertFalse(gitletDir.exists());
        Main myMain = new Main();
        myMain.main("init");
        assertTrue(gitletDir.isDirectory());
    }

    @Test
    public void testAdd() {
        Main myMain = new Main();
        String[] args = new String[]{"add", wugFile.getName()};
        myMain.main(args);
        myMain = new Main();
        myMain.main(args);
        myMain = new Main();
        myMain.main(args);
    }

    public File createWug(){
        File wug = Utils.join(".", "wug.txt");
        try {
            wug.createNewFile();
        } catch (IOException e) {
            return null;
        }
        Utils.writeObject(wug,"This is a wug");
        return wug;
    }
}

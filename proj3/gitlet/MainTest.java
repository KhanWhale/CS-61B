package gitlet;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void initMain() {
        File gitletDir = Utils.join(".", ".gitlet");
        assertFalse(gitletDir.exists());
        Main myMain = new Main();
        myMain.main("init");
        assertTrue(gitletDir.isDirectory());
    }


}

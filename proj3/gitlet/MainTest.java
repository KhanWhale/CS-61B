package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void initMain() throws IOException {
        File gitletDir = new File(".gitlet");
        assertFalse(gitletDir.exists());
        Main process = new Main();
        process.main("init");
        assertTrue(gitletDir.exists());
    }


}

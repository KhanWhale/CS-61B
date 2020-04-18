package gitlet;

import com.sun.tools.corba.se.idl.Util;
import ucb.junit.textui;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.Assert.*;

/** The suite of all JUnit tests for the gitlet package.
 *  @author Aniruddh Khanwale
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) throws IOException {
        System.exit(textui.runClasses(UnitTest.class, MainTest.class));
    }


    /** A dummy test to avoid complaint. */
    @Test
    public void placeholderTest() {
    }

}



/**
 * 
 */
package XMars.Test;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class XUniTest extends TestCase{
	
	public static void main(String[] args) throws IOException {
		junit.textui.TestRunner.run(suite());
	}
	
    public static Test suite() {
        TestSuite suite = new TestSuite("All JUnit Tests");
        suite.addTest(ReaderTest.suite());
        return suite;
    }
}

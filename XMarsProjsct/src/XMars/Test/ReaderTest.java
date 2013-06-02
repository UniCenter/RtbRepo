package XMars.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import XMars.Applications.RTB.RTBDataReader;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ReaderTest extends TestCase{
	private RTBDataReader _reader = null; 
	
	@Override 
	protected void setUp() {
		try {
			this._reader = new RTBDataReader("data/clk.20130311.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	protected void tearDown() {
		try {
			if (this._reader != null){
				this._reader.Close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
    public static Test suite() {
    	TestSuite suite= new TestSuite(ReaderTest.class);
        return suite;
    }
    
    public void testReaderNotNull() {
    	assertTrue(this._reader != null);
    }
    
    public void testAdSloatFormat() throws IOException {
    	assertTrue(this._reader.ReadRecord().GetSlotType()>0);
    }
}

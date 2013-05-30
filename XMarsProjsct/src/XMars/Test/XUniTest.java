/**
 * 
 */
package XMars.Test;

import java.io.IOException;

import XMars.Applications.RTB.RTBDataReader;
import XMars.Applications.RTB.RTBInstance;
import XMars.Learning.Common.DataItem;

public class XUniTest {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Application RTB Test");
		RTBDataReader reader = new RTBDataReader("data/clk.20130311.txt");
		while(!reader.EndOfFile())
		{
			RTBInstance rtbInst = reader.ReadRecord();
//			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			System.out.println("key:" + rtbInst.GetRegionCode());
		}
		reader.Close();
	}

}

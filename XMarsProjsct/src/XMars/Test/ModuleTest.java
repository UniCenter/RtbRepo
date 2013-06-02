package XMars.Test;

import java.io.IOException;

import XMars.Applications.RTB.Application;
import XMars.Applications.RTB.RTBDataReader;
import XMars.Applications.RTB.RTBInstance;

public class ModuleTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		testGenerateTrainingData();
		System.out.println("done");
	}
	
	public static void testAnalysisAndExtractFeature() throws IOException {
		Application app = new Application();
		app.AnalysisAndExtractFeature("data/", "data/", "data/");
	}

	public static void testGenerateTrainingData() throws IOException {
		Application app = new Application();
		app.GenerateTrainingData("data/", "data/", "data/");
	}
}

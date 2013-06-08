package XMars.Test;

import java.io.IOException;

import XMars.Applications.RTB.AppConf;
import XMars.Applications.RTB.Application;
import XMars.Conf.Conf;
import XMars.Logger.Logger;

public class ModuleTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		testGenerateTrainingData();
//		testAnalysisAndExtractFeature();
		System.out.println("done");
	}
	
	public static void testAnalysisAndExtractFeature() throws IOException {
		Application app = new Application();
		app.AnalysisAndExtractFeature(AppConf.getInputDir(), AppConf.getDataDir(), AppConf.getResultDir());
	}

	public static void testGenerateTrainingData() throws IOException {
		Application app = new Application();
		app.GenerateTrainingData(AppConf.getInputDir(), AppConf.getDataDir(), AppConf.getResultDir());
	}
	
	public static void testLogger() throws IOException {
		Conf _conf = new Conf();
		AppConf.readConf(_conf);
		System.out.println(AppConf.getLogPath() + "\t" + AppConf.getLogFile() + "\t" + 
				AppConf.getLogSize() + "\t" +  AppConf.getLogLevel());
		Logger.init(AppConf.getLogPath(), AppConf.getLogFile(), 
					AppConf.getLogSize(), AppConf.getLogLevel());
		Logger.debug("start process now");
		System.out.println("logger done");
		Logger.close();
	}
}

package XMars.Applications.RTB;

import XMars.Conf.Conf;

public class AppConf {
	private static String logPath;
	private static String logFile;
	private static int logLevel;
	private static int logSize;
	
	private static String inputDir;
	private static String dataDir;
	private static String resultDir;
	
	private static String pvclicklog;
	private static String domain;
	private static String extractionFile;
	private static String featureViewFile;
	private static String trainSampleFile;
	private static String testSampleFile;
	private static String crossValidationFile;
	private static String modelFile;
	private static String predictionFile;
	
	public static void readConf(Conf conf) {
		logPath = conf.getConfString("log_path");
		logFile = conf.getConfString("log_file");
		logLevel = conf.getConfInt("log_level");
		logSize = conf.getConfInt("log_size");
	
		inputDir = conf.getConfString("input_dir");
		dataDir = conf.getConfString("data_dir");
		resultDir = conf.getConfString("resultDir");
		
		pvclicklog = conf.getConfString("pvclicklog");
		domain = conf.getConfString("domain");
		extractionFile = conf.getConfString("extraction");
		featureViewFile = conf.getConfString("feature_view");
		trainSampleFile = conf.getConfString("train_sample");
		testSampleFile = conf.getConfString("test_sample");
		crossValidationFile = conf.getConfString("cross_validation");
		modelFile = conf.getConfString("model");
		predictionFile = conf.getConfString("predict");
	}

	

	public static String getPredictionFile() {
		return predictionFile;
	}



	public static String getFeatureViewFile() {
		return featureViewFile;
	}



	public static String getTrainSampleFile() {
		return trainSampleFile;
	}



	public static String getTestSampleFile() {
		return testSampleFile;
	}



	public static String getCrossValidationFile() {
		return crossValidationFile;
	}



	public static String getModelFile() {
		return modelFile;
	}



	public static String getLogPath() {
		return logPath;
	}



	public static String getLogFile() {
		return logFile;
	}



	public static int getLogLevel() {
		return logLevel;
	}



	public static int getLogSize() {
		return logSize;
	}



	public static String getInputDir() {
		return inputDir;
	}



	public static String getDataDir() {
		return dataDir;
	}



	public static String getResultDir() {
		return resultDir;
	}



	public static String getPvclicklog() {
		return pvclicklog;
	}



	public static String getDomain() {
		return domain;
	}

	
	public static String getExtractionFile() {
		return extractionFile;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

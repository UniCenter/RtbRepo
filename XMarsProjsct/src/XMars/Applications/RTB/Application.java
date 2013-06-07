package XMars.Applications.RTB;
import XMars.Conf.Conf;
import XMars.FileUtil.*;
import java.io.IOException;
import XMars.Learning.Common.*;
import XMars.Learning.LibLinearUtil.*;
import XMars.Logger.Logger;

public class Application 
{
	private Conf _conf = new Conf();
	
	public Application() {
		AppConf.readConf(_conf);
		Logger.init(AppConf.getLogPath(), AppConf.getLogFile(), 
				AppConf.getLogSize(), AppConf.getLogLevel());
	}
	
	public void AnalysisAndExtractFeature(String inputDir,String dataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(dataDir);
		FeatureAnalyzer featureAnalyzer = new FeatureAnalyzer();
		RTBDataReader reader = new RTBDataReader(inputDir + AppConf.getPvclicklog());
		ExtractionWriter writer = new ExtractionWriter(resultDir + AppConf.getExtractionFile());
		
		RTBInstance rtbInst = reader.ReadRecord();
		while(rtbInst != null)
		{
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			featureAnalyzer.OnItemExtracted(dataItem);
			writer.WriteExtraction(dataItem);
			rtbInst = reader.ReadRecord();
		}
		FeatureViewUtil.DumpOverviewToFile(featureAnalyzer.GetOverview(), resultDir + AppConf.getFeatureViewFile());
		reader.Close();
		writer.Close();
	}
	
	public void GenerateTrainingData(String inputDir,String dataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(dataDir);
		FeatureOverview overview = FeatureViewUtil.LoadOverviewFromFIle(dataDir + AppConf.getFeatureViewFile());
		CompactFeatureEncoder featureEncoder = new CompactFeatureEncoder(overview);
		ItemEncoder itemEncoder=new ItemEncoder(featureEncoder);
		LibLinearWriter writer = new LibLinearWriter(resultDir + AppConf.getTrainSampleFile());
		RTBDataReader reader = new RTBDataReader(inputDir + AppConf.getPvclicklog());
		
		RTBInstance rtbInst = reader.ReadRecord();
		while(rtbInst != null)
		{
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			EncodedItem encodedItem = itemEncoder.EncodeItem(dataItem);
			writer.WriteInstance(encodedItem);
			rtbInst = reader.ReadRecord();
		}
		reader.Close();
		writer.Close();
	}
	

	public void Validate(String inputDir,String metaDataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(metaDataDir);
		LibLinearLRModel model = new LibLinearLRModel();
		model.LoadFromFile(resultDir + AppConf.getModelFile());
		
		FeatureOverview overview = FeatureViewUtil.LoadOverviewFromFIle(metaDataDir + AppConf.getFeatureViewFile());
		CompactFeatureEncoder featureEncoder = new CompactFeatureEncoder(overview);
		SimplePredictor predictor = new SimplePredictor(featureEncoder,new LibLinearPredictor(),model);
		RTBDataReader reader = new RTBDataReader(inputDir + AppConf.getPvclicklog());
		EvaluationWriter writer = new EvaluationWriter(resultDir + AppConf.getPredictionFile());
		RTBInstance rtbInst = reader.ReadRecord();
		while(rtbInst != null)
		{
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			double predValue = predictor.Predict(dataItem);
			writer.WriteRecord(dataItem.ItemId, predValue, dataItem.TargetValue);
			rtbInst = reader.ReadRecord();
		}
		writer.Close();
		reader.Close();
	}
	
	
}

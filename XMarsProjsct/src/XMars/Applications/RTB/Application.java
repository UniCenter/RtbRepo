package XMars.Applications.RTB;
import XMars.FileUtil.*;
import java.io.IOException;
import XMars.Learning.Common.*;
import XMars.Learning.LibLinearUtil.*;

public class Application 
{
	public void AnalysisAndExtractFeature(String inputDir,String dataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(dataDir);
		FeatureAnalyzer featureAnalyzer = new FeatureAnalyzer();
		RTBDataReader reader = new RTBDataReader(inputDir+"/pvclicklog.txt");
		ExtractionWriter writer = new ExtractionWriter(resultDir+"/extraction.txt");
		
		RTBInstance rtbInst = reader.ReadRecord();
		while(rtbInst != null)
		{
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			featureAnalyzer.OnItemExtracted(dataItem);
			writer.WriteExtraction(dataItem);
			rtbInst = reader.ReadRecord();
		}
		FeatureViewUtil.DumpOverviewToFile(featureAnalyzer.GetOverview(), resultDir+"/FeatureView.txt");
		reader.Close();
		writer.Close();
	}
	
	public void GenerateTrainingData(String inputDir,String dataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(dataDir);
		FeatureOverview overview = FeatureViewUtil.LoadOverviewFromFIle(dataDir+"/FeatureView.txt");
		CompactFeatureEncoder featureEncoder = new CompactFeatureEncoder(overview);
		ItemEncoder itemEncoder=new ItemEncoder(featureEncoder);
		LibLinearWriter writer = new LibLinearWriter(resultDir+"/train.txt");
		RTBDataReader reader = new RTBDataReader(inputDir+"/pvclicklog.txt");
		
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
		model.LoadFromFile(resultDir+"/model.txt");
		
		FeatureOverview overview = FeatureViewUtil.LoadOverviewFromFIle(metaDataDir+"/FeatureView.txt");
		CompactFeatureEncoder featureEncoder = new CompactFeatureEncoder(overview);
		SimplePredictor predictor = new SimplePredictor(featureEncoder,new LibLinearPredictor(),model);
		RTBDataReader reader = new RTBDataReader(inputDir+"/pvclicklog.txt");
		EvaluationWriter writer = new EvaluationWriter(resultDir+"/pred.txt");
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

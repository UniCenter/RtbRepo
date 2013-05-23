package XMars.Applications.RTB;

import java.io.IOException;
import XMars.FileUtil.*;
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
		
		while(!reader.EndOfFile())
		{
			RTBInstance rtbInst = reader.ReadRecord();
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			featureAnalyzer.OnItemExtracted(dataItem);
			writer.WriteExtraction(dataItem);
			
		}
		featureAnalyzer.GetOverview().DumpToFile(resultDir+"/FeatureView.txt");
		reader.Close();
		writer.Close();
	}
	
	public void GenerateTrainingData(String inputDir,String dataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(dataDir);
		FeatureOverview dataSetView = FeatureOverview.CreateFromFile(dataDir+"/FeatureView.txt");
		CompactFeatureEncoder featureEncoder = new CompactFeatureEncoder(dataSetView);
		ItemEncoder itemEncoder=new ItemEncoder(featureEncoder);
		LibLinearWriter writer = new LibLinearWriter(resultDir+"/train.txt");
		RTBDataReader reader = new RTBDataReader(inputDir+"/pvclicklog.txt");
		
		while(!reader.EndOfFile())
		{
			RTBInstance rtbInst = reader.ReadRecord();
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			EncodedItem encodedItem = itemEncoder.EncodeItem(dataItem);
			writer.WriteInstance(encodedItem);
			
		}
		reader.Close();
		writer.Close();
	}
	

	public void Validate(String inputDir,String metaDataDir,String resultDir) throws IOException
	{
		RTBFeatureExtractor extractor = new RTBFeatureExtractor(metaDataDir);
		LibLinearLRModel model = new LibLinearLRModel();
		model.LoadFromFile(resultDir+"/model.txt");
		CompactFeatureEncoder featureEncoder = new CompactFeatureEncoder(FeatureOverview.CreateFromFile(metaDataDir+"/FeatureView.txt"));

		SimplePredictor predictor = new SimplePredictor(featureEncoder,new LibLinearPredictor(),model);
		RTBDataReader reader = new RTBDataReader(inputDir+"/pvclicklog.txt");
		EvaluationWriter writer = new EvaluationWriter(resultDir+"/pred.txt");
		while(!reader.EndOfFile())
		{
			RTBInstance rtbInst = reader.ReadRecord();
			DataItem dataItem = extractor.ExtractFeature(rtbInst);
			double predValue = predictor.Predict(dataItem);
			writer.WriteRecord(dataItem.ItemId, predValue, dataItem.TargetValue);
			
		}
		writer.Close();
		reader.Close();
	}
	
	
}
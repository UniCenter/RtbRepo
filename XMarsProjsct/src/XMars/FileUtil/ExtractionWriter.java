package XMars.FileUtil;
import XMars.Learning.Common.*;
import java.io.*;

public class ExtractionWriter {
	BufferedWriter _writer;
	static String _featureSpliter = "\t";
	static String _fieldSpliter = ":";
	public ExtractionWriter(String filePath) throws IOException
	{
		_writer = new BufferedWriter(new FileWriter(filePath));
	
	}
	public void WriteExtraction(DataItem dataItem) throws IOException
	{
		if(dataItem.Features.size() ==0)
		{
			return;
		}
		_writer.write(dataItem.TargetValue);
		_writer.write(_featureSpliter);
		_writer.write(dataItem.PredictValue);
		_writer.write(_featureSpliter);
		
		Feature feature = dataItem.Features.get(0);
		_writer.write("" + feature.getFeatureSet().GetFeatureSetId());
		_writer.write(_fieldSpliter);
		_writer.write("" + feature.getLocalFeatureId());
		_writer.write(_fieldSpliter);
		_writer.write("" +feature.getFeatureValue());
		
		for(int i = 1; i< dataItem.Features.size();i++)
		{
			_writer.write(_featureSpliter);
			feature = dataItem.Features.get(i);
			_writer.write("" + feature.getFeatureSet().GetFeatureSetId());
			_writer.write(_fieldSpliter);
			_writer.write("" + feature.getLocalFeatureId());
			_writer.write(_fieldSpliter);
			_writer.write("" + feature.getFeatureValue());
		}
		_writer.write("\n");
	}
	public void Close() throws IOException
	{
		_writer.close();
	}
}

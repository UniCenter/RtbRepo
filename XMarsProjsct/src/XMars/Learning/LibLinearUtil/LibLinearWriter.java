package XMars.Learning.LibLinearUtil;
import XMars.Learning.Common.*;
import java.io.*;

public class LibLinearWriter 
{
	private BufferedWriter _writer;
	private String _spliter = "\t";
	public LibLinearWriter(String filePath) throws IOException
	{
		_writer = new BufferedWriter(new FileWriter(filePath));
	}
	public void WriteInstance(EncodedItem item) throws IOException
	{
		_writer.write("" + item.TargetValue);
		for(int i = 0; i < item.Features.size(); i ++)
		{
			_writer.write(_spliter);
			_writer.write("" + item.Features.get(i).FeatureId);
			_writer.write(":");
			_writer.write("" + item.Features.get(i).FeatureValue);
		}
		_writer.write('\n');
	}
	
	public void Close() throws IOException
	{
		if(_writer != null)
		{
			_writer.close();
		}
	}

}

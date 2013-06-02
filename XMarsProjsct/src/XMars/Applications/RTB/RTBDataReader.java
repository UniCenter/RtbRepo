package XMars.Applications.RTB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import XMars.FileUtil.*;
import XMars.Util.*;

public class RTBDataReader 
{
	TSVFileParser _parser;
	BufferedReader _reader;
	
	public RTBDataReader(String filePath) throws FileNotFoundException
	{
		this._reader = new BufferedReader(new FileReader(filePath));
		try
		{
			_parser= new TSVFileParser(this._reader ,"\t");
		}
		catch(IOException e)
		{
			System.out.printf("[Error] Init Reader Wrong in RTBDataReader.java, with File = %s, at %s\n", filePath, TimeUtil.getTimeStr());
			System.exit(-1);
		}
	}

	public RTBInstance ReadRecord() throws IOException
	{
		TSVLine tsvLine = _parser.ReadLine();
		if (tsvLine == null) {
			return null;
		}
		return RTBInstance.NewInstance(tsvLine);
	}
	
	public void Close() throws IOException
	{
		this._reader.close();
	}

}

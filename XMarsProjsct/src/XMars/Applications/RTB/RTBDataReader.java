package XMars.Applications.RTB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import XMars.CodingUtil.*;
import XMars.FileUtil.*;

public class RTBDataReader 
{
	//added by zhangcen
	private BufferedReader bReader = null;
	private String[] columnNames = null;
	
	public RTBDataReader(String filePath)
	{
		try {
			this.bReader = new BufferedReader(new FileReader(filePath));
		} catch (IOException e) 
		{
			System.out.printf("[Error] Init Reader Wrong in RTBDataReader.java, with File = %s, at %s\n", filePath, TimeUtil.getTimeStr());
			e.printStackTrace();
			System.exit(-1);
		}//TODO maybe need a new way to construct the BufferedReader
		
		//set column names
		this.setColumnNames();
	}
	/*
	 * 该方法存疑，因为按行读入的话，无法直接判断是否直接是行尾
	 */
	public boolean EndOfFile()
	{
		if(this.bReader == null)
		{
			return true;
		}
		return false;
	}
	public RTBInstance ReadRecord()
	{
		HashMap<String, Integer> columnMap = this.getColumnMap();
		String spliter = "\t";
		//supposed to get a TSVLine?
		if(this.bReader != null)
		{
			String line = null;
			int lineCnt = 0;
			try
			{
				while((line = this.bReader.readLine()) != null)
				{
					lineCnt++;
					//TODO
					/*
					 * HashTable is from an older version of Java, 
					 * and not working well with NULL pointer
					 * my suggestion is to change it into HashMap
					 */
					TSVLine tsvLine = new TSVLine(line, spliter, columnMap);
					/*
					 * we also have some different definition of the column names here
					 * when getting the RTBInstance, I use the names as exactly the same 
					 * as they use in test environment, we can work it straight
					 */
					return RTBInstance.NewInstance(tsvLine);
				}
			}catch(Exception e)
			{
				System.out.printf("[Error] Exception Happend While Reading Data in RTBDataReader.java at Line %d at %s\n", lineCnt, TimeUtil.getTimeStr());
				e.printStackTrace();
			}
		}
		return null;
	}
	public void Close()
	{
	
	}
	
	private HashMap<String,Integer> getColumnMap()
	{
		if(this.columnNames == null || this.columnNames.length < 1)
		{
			return null;
		}
		HashMap<String, Integer> columnMap = new HashMap<String, Integer>(this.columnNames.length * 2);
		for(int i = 0, len = this.columnNames.length; i < len; i++)
		{
			columnMap.put(columnNames[i], i);//TODO here we start from 0, should we start from 1?
		}
		return columnMap;
	}
	
	/**
	 * setup column names in a String Array
	 */
	private void setColumnNames()
	{
		/*
		 * these column names are based on impression and click log, 
		 * we might want to change it in the future because the last 
		 * three columns are useless
		 */
		this.columnNames = new String[]{
				"bidId", "timestamp", "logType", "ipinyouId", "userAgent",
				"ipAddress","region","city","adExchange","domain",
				"url","anonymousURLID","adSlotID","adSlotWidth","adSlotHeight",
				"adSlotVisibility","adSlotFormat","adSlotFloorPrice","creativeID","biddingPrice",
				"payingPrice","homePage"};
		
	}

}

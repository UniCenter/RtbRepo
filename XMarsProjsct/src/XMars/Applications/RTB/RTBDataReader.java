package XMars.Applications.RTB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import XMars.FileUtil.*;
import XMars.Util.*;

public class RTBDataReader 
{
	private Scanner _scanner;
	private String[] columnNames = null;
	
	public RTBDataReader(String filePath)
	{
		try {
			this._scanner = new Scanner(new FileReader(filePath));
		} catch (IOException e) 
		{
			System.out.printf("[Error] Init Reader Wrong in RTBDataReader.java, with File = %s, at %s\n", filePath, TimeUtil.getTimeStr());
			e.printStackTrace();
			System.exit(-1);
		}
		
		//set column names
		this.setColumnNames();
	}
	
	public boolean EndOfFile()
	{
		if (this._scanner.hasNext())
		{
			return false;
		}
		return true;
	}
	public RTBInstance ReadRecord()
	{
		HashMap<String, Integer> columnMap = this.getColumnMap();
		String spliter = "\t";
		if (!this.EndOfFile())
		{
			String line = null;
			int lineCnt = 0;
			try
			{
				while((line = this._scanner.nextLine()) != null)
				{
					lineCnt++;
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
	
	public void Close() throws IOException
	{	if (this._scanner != null) {
			this._scanner.close();
		}
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

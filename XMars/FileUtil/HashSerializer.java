package XMars.FileUtil;
import java.util.*;
import java.io.*;
public class HashSerializer 
{
	public static Hashtable<Integer,Integer> LoadHashtable(BufferedReader streamReader,String spliter,int keyIndex,int valueIndex) throws IOException
	{
		Hashtable<Integer,Integer> hashTable = new Hashtable<Integer,Integer>();
		String textLine = null;
		while((textLine = streamReader.readLine()) != null)
		{
			String[] fields =  textLine.split(spliter);
			hashTable.put(Integer.parseInt(fields[keyIndex]), Integer.parseInt(fields[valueIndex]) );
			textLine = streamReader.readLine();
		}
		return hashTable;
	}

	public static Hashtable<String,Integer> LoadStringHashtable(BufferedReader streamReader,String spliter,int keyIndex,int valueIndex) throws IOException
	{
		Hashtable<String,Integer> hashTable = new Hashtable<String,Integer>();
		String textLine = null;
		while((textLine = streamReader.readLine()) != null)
		{
			String[] fields =  textLine.split(spliter);
			hashTable.put(fields[keyIndex], Integer.parseInt(fields[valueIndex]) );
			textLine = streamReader.readLine();
		}
		return hashTable;
	}
	public static Hashtable<Integer,Integer> LoadSeqHashtable(BufferedReader streamReader,String spliter,int keyIndex) throws IOException
	{
		Hashtable<Integer,Integer> hashTable = new Hashtable<Integer,Integer>();
		int index = 1;
		String textLine = null;
		while((textLine = streamReader.readLine()) != null)
		{
			String[] fields =  textLine.split(spliter);
			hashTable.put(Integer.parseInt(fields[keyIndex]),index );
			textLine = streamReader.readLine();
			index++;
		}
		return hashTable;
	}

	public static Hashtable<String,Integer> LoadStrSeqHashtable(BufferedReader streamReader,String spliter,int keyIndex) throws IOException
	{
		Hashtable<String,Integer> hashTable = new Hashtable<String,Integer>();
		int index = 1;
		String textLine = null;
		while((textLine = streamReader.readLine()) != null)
		{
			String[] fields =  textLine.split(spliter);
			hashTable.put(fields[keyIndex], index );
			textLine = streamReader.readLine();
			index++;
		}
		return hashTable;
	}
	
}

package XMars.FileUtil;
import java.io.*;
import java.util.*;
import XMars.Learning.Common.*;;

public class FeatureViewUtil 
{
	private static String _featureSetSplitLine = "*****************";
	
	private static int GetPropertyValue(String str)//,String spliter)
	{
		int spliterIndex = str.indexOf("=");
		return Integer.parseInt(str.substring(spliterIndex + 1));
	}
	private static String featureSetToString(FeatureSetInfo featureSet)
	{
		return "FeatureSet"+String.valueOf(featureSet.GetFeatureSetId()) + "=" + featureSet.GetDescription();
	}
	public static FeatureSetInfo createFeatureSetFromString(String str)
	{
		int spliterIndex = str.indexOf("=");
		int featureSetId = Integer.parseInt(str.substring(10, spliterIndex));
		String description = str.substring(spliterIndex + 1);
		return new FeatureSetInfo(featureSetId,description);
	}
	
	public static void DumpFeatureSetViewToFile(FeatureSetView featureSetView, BufferedWriter writer) throws IOException
	{
		//BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		String strFeatureSetInfo = featureSetToString(featureSetView.GetFeatureSet());
		writer.write(strFeatureSetInfo + "\n");
		writer.write("ItemCount"+"="+String.valueOf(featureSetView.ItemCount)+"\n");
		writer.write("MaxFeatureId"+"="+String.valueOf(featureSetView.MaxFeatureId)+"\n");
		writer.write("MaxFeatureValue"+"="+String.valueOf(featureSetView.MaxFeatureValue)+"\n");
		writer.write("MinFeatureId"+"="+String.valueOf(featureSetView.MinFeatureId)+"\n");
		writer.write("MinFeatureValue"+"="+String.valueOf(featureSetView.MinFeatureValue)+"\n");
		writer.write("TotalFeatureCount"+"="+String.valueOf(featureSetView.TotalFeatureCount)+"\n");
		writer.write("Feature Imps:\n");
		Vector<FeatureImpInfo> featureImpList = featureSetView.GetSortedByPv();
		for(int i=0;i<featureImpList.size();i++)
		{
			FeatureImpInfo imp = featureImpList.get(i);
			writer.write("" + imp.FeatureId);
			writer.write(":");
			writer.write("" + imp.ImpCount);
			writer.write("\n");
		}
		writer.write(_featureSetSplitLine);
		writer.write("\n");
	}
	
	public static FeatureSetView LoadFeatureSetViewFromFile(BufferedReader reader) throws IOException
	{
		//BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String strFeatureSetInfo = reader.readLine();
		FeatureSetInfo featureSet = createFeatureSetFromString(strFeatureSetInfo);
		FeatureSetView featureSetView = new FeatureSetView(featureSet);
		//必须严格按照写入顺序读取
		featureSetView.ItemCount = GetPropertyValue(reader.readLine());
		featureSetView.MaxFeatureId = GetPropertyValue(reader.readLine());
		featureSetView.MaxFeatureValue = GetPropertyValue(reader.readLine());
		featureSetView.MinFeatureId = GetPropertyValue(reader.readLine());
		featureSetView.MinFeatureValue = GetPropertyValue(reader.readLine());
		featureSetView.TotalFeatureCount = GetPropertyValue(reader.readLine());
		reader.readLine();
		while(true)
		{
			String line = reader.readLine();
			if(line == null || line.trim() == _featureSetSplitLine)
			{
				break;
			}
			String[] fields = line.split(":");
			if (fields.length < 2) {
				break;
			}
			long featureId = Long.parseLong(fields[0]);
			int featureImp = Integer.parseInt(fields[1]);
			featureSetView.FeatureCount.put(featureId, new FeatureImpInfo(featureId,featureImp));
		}
		return featureSetView;
		
	}

	public static void DumpOverviewToFile(FeatureOverview overview, String filePath) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		for(int i =0; i<overview.FeatureViews.size();i++)
		{
			DumpFeatureSetViewToFile(overview.FeatureViews.get(i),writer);
		}
		writer.close();
	}
	
	public static FeatureOverview LoadOverviewFromFIle(String filePath) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		FeatureOverview overview = new FeatureOverview();
		while(reader.ready())
		{
			FeatureSetView featureSetFiew = LoadFeatureSetViewFromFile(reader);
			overview.FeatureViews.add(featureSetFiew);
		}
		reader.close();
		return overview;
		
	}
	
}

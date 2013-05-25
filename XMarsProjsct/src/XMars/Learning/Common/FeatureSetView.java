package XMars.Learning.Common;

import java.util.*;

public class FeatureSetView 
{
	private FeatureSetInfo _featureSet;
	public int TotalFeatureCount;
	public int ItemCount;
	public int MaxFeatureId;
	public int MinFeatureId;
	public int MaxFeatureValue;
	public int MinFeatureValue;
	public TreeMap<Integer,FeatureImpInfo> FeatureCount = new TreeMap<Integer,FeatureImpInfo>();
	
	public FeatureSetView(FeatureSetInfo featureSet)
	{
		_featureSet = featureSet;

		MinFeatureId = Integer.MAX_VALUE;
		MinFeatureValue = Integer.MAX_VALUE;
	}
	public FeatureSetInfo GetFeatureSet()
	{
		return _featureSet;
	}
	public Integer[] GetFeatureIdList()
	{
		Integer[] keyList = (Integer[]) FeatureCount.keySet().toArray();
		return keyList;
	}
	
	public Vector<FeatureImpInfo> GetSortedByPv()
	{
		Object[] values = FeatureCount.values().toArray();
		Arrays.sort(values);
		Vector<FeatureImpInfo> featureImps = new Vector<FeatureImpInfo>(values.length);
		for(int i = 0;i < values.length; i ++)
		{
			featureImps.add((FeatureImpInfo) values[i]);
		}
		return featureImps;
	}
}

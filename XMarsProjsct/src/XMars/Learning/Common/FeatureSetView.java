package XMars.Learning.Common;

import java.util.*;

public class FeatureSetView 
{
	private FeatureSetInfo _featureSet;
	public int TotalFeatureCount;
	public int ItemCount;
	public long MaxFeatureId;
	public long MinFeatureId;
	public int MaxFeatureValue;
	public int MinFeatureValue;
	public TreeMap<Long,FeatureImpInfo> FeatureCount = new TreeMap<Long,FeatureImpInfo>();
	
	public FeatureSetView(FeatureSetInfo featureSet)
	{
		_featureSet = featureSet;

		MinFeatureId = Long.MAX_VALUE;
		MinFeatureValue = Integer.MAX_VALUE;
	}
	public FeatureSetInfo GetFeatureSet()
	{
		return _featureSet;
	}
	public Long[] GetFeatureIdList()
	{
		Long[] keyList = FeatureCount.keySet().toArray(new Long[1]);
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

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
	TreeMap<Integer,Integer> FeatureCount = new TreeMap<Integer,Integer>();
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
}

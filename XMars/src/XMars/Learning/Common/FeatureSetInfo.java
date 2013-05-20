package XMars.Learning.Common;

public final class FeatureSetInfo 
{
	private int _featureSetId;
	//private int _offset;
	//private int _maxFeatureCount;
	private String _description;
	public int GetFeatureSetId()
	{
		return _featureSetId;
	}

	/*public int GetOffset()
	{
		return _offset;
	}*/
	/*public int MaxFeatureCount()
	{
		return _maxFeatureCount;
	}*/
	public String GetDescription()
	{
		return _description;
	}
	public FeatureSetInfo(int featureSetId,
						 	//int offset,
							//int maxFeatureCount,
							String description)
	{
		_featureSetId = featureSetId;
		//_offset = offset;
		//_maxFeatureCount = maxFeatureCount;
		_description = description;
		
		
	}

}
